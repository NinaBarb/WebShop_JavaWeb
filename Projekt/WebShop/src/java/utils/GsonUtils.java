/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nina
 */
public class GsonUtils {
    
    public static void convertToGson(Object message, HttpServletResponse response) throws IOException{
        
        String messageJson = new Gson().toJson(message);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(messageJson);
        out.flush();
    }
}
