package org.iespring1402.webserver.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class OKPage extends Page{
    public static String result() throws IOException {
        String dir = System.getProperty("user.dir");
        File OKFile = new File(dir + "/src/main/java/org/iespring1402/webserver/pages/templates/200.html");
        Document OKDocument = Jsoup.parse(OKFile, "UTF-8");
        return OKDocument.outerHtml();
    }
}
