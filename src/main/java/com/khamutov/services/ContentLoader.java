package com.khamutov.services;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ContentLoader {
    final private static String PATH =
            String.valueOf(ContentLoader.class.getResource("/static/script")).replace("file:/", "");

    public static void writeScript(String uri, HttpServletResponse response) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(PATH + uri))) {
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