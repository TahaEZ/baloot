package org.iespring1402.webserver;

import io.javalin.Javalin;
import org.iespring1402.webserver.pages.Commodities;
import org.iespring1402.webserver.pages.Forbidden;
import org.iespring1402.webserver.pages.Hello;
import org.iespring1402.webserver.pages.NotFound;

public class WebServer {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        app.get("/", context -> context.html(Hello.result()));

        app.get("/commodities", context -> context.html(Commodities.result()));

        app.get("/403", context -> context.html(Forbidden.result()));

        app.get("/404", context -> context.html(NotFound.result()));

        app.error(404, context -> context.html(NotFound.result()));
    }
}
