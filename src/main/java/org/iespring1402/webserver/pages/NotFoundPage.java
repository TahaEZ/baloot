package org.iespring1402.webserver.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class NotFoundPage extends Page {
    public static String result() throws IOException {
        String dir = System.getProperty("user.dir");
        File notFoundFile = new File(dir + "/src/main/java/org/iespring1402/webserver/pages/templates/404.html");
        Document notFoundDocument = Jsoup.parse(notFoundFile, "UTF-8");
        return notFoundDocument.outerHtml();
    }
}