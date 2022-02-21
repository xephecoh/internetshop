package com.khamutov.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static final String RESOURCES_PATH = "src/main/resources/webapp/WEB-INF/views";
    private static final Configuration CONFIG = new Configuration(Configuration.VERSION_2_3_19);
    public static void getPage(String filename, Map<String, ?> data,Writer writer) {
        try {
            Template template = CONFIG.getTemplate(RESOURCES_PATH + "/" + filename);
            template.process(data, writer);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException("Failed to generate page: " + filename, e);
        }
    }
}