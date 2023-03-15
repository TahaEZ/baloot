package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;

import java.io.IOException;

public class RemoveFromBuyListPage  extends  Page{
    public static String result(String username, int commoditytId) throws IOException {
        if ( true == Baloot.getInstance().removeFromBuyList(username,commoditytId).success)
            return OKPage.result();
        else
            return ForbiddenPage.result();
        }
}
