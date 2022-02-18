package com.khamutov.services;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ContentLoader {

    public static void writeScript(String uri, HttpServletResponse response) {
         System.out.println( ContentLoader.class.getResource("/static/script" + uri));
        try (BufferedReader reader = new BufferedReader(
                new FileReader(ContentLoader.class.getResource("/static/script") + uri))) {
            //"src/main/resources/static/script" + uri
            //String.valueOf(ContentLoader.class.getResource("target/classes/static/script" + uri)
            String line;
            response.setStatus(HttpServletResponse.SC_OK);
            Writer writer = response.getWriter();
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
        } catch (FileNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}