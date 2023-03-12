package org.iespring1402.webserver.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class ForbiddenPage extends Page {
    public static String result() throws IOException {
        String dir = System.getProperty("user.dir");
        File forbiddenFile = new File(dir + "/src/main/java/org/iespring1402/webserver/pages/templates/403.html");
        Document forbiddenDocument = Jsoup.parse(forbiddenFile, "UTF-8");
        return forbiddenDocument.outerHtml();
    }
}
