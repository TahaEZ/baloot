package org.iespring1402.webserver.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HelloPage extends Page {
    public static String result() {
        Document document = Jsoup.parse("<html><head><title>Hello Page</title></head><body></body></html>");
        Element heading = document.createElement("h1");
        heading.text("Hello. This is just a test!");
        document.body().appendChild(heading);
        return document.outerHtml();
    }
}
