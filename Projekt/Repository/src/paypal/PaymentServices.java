/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paypal;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.util.ArrayList;
import java.util.List;
import models.OrderDetail;
import models.UserAccount;

/**
 *
 * @author Nina
 */
public class PaymentServices {

    private static final String CLIENT_ID = "AVGfnZO4Iaoao0qqk9tMjKhUfDzbLrpTAQNyCSVK6FXSoB-FHgHq57S16cgNIbp9am--BtjxKaaTec7R";
    private static final String CLIENT_SECRET = "EOH-2PtSzBq2LlpxKInL8uOmDoBsZsN_tV6Luf-y3mXkouhI6mVJFX2L2Umvor5vxdVGwHDBFxkBCK_g";
    private static final String MODE = "sandbox";

    public String authorizePayment(List<OrderDetail> orderDetail, UserAccount userAccount)
            throws PayPalRESTException {

        Payer payer = getPayerInformation(userAccount);
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(orderDetail);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);

    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    public Payment executePayment(String paymentId, String payerId)
            throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        return payment.execute(apiContext, paymentExecution);
    }

    private Payer getPayerInformation(UserAccount userAccount) {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(userAccount.getFirstName())
                .setLastName(userAccount.getLastName())
                .setEmail(userAccount.getEmail());

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/WebShop/cart");
        redirectUrls.setReturnUrl("http://localhost:8080/WebShop/reviewPayment");

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(List<OrderDetail> orderDetail) {
        Details details = new Details();

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        float total = 0;
        for (OrderDetail product : orderDetail) {
            total += product.getSubtotalFloat() * Float.parseFloat(String.valueOf(product.getQuantity()));
        }
        amount.setTotal(String.format("%.3f", total));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        for (OrderDetail product : orderDetail) {
            Item item = new Item();
            item.setCurrency("EUR");
            item.setName(product.getProductName());
            item.setPrice(product.getSubtotal());
            item.setQuantity(product.getQuantity());

            items.add(item);
        }
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }
}
