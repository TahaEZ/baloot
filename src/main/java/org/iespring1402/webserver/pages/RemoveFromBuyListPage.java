package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.response.Response;

import java.io.IOException;

public class RemoveFromBuyListPage  extends  Page{
    public static String result(String username, int commoditytId) throws IOException {
        Response response = Baloot.getInstance().removeFromBuyList(username,commoditytId);
        if ( true == response.success )
            return OKPage.result();
        else
            return ForbiddenPage.result();
        }
}
