package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.Comment;
import org.iespring1402.Commodity;
import org.iespring1402.User;

import java.io.IOException;
import java.util.ArrayList;

public class RemoveFromBuyList  extends  Page{
    public static String result(String username, int commoditytId) throws IOException {
        Baloot.getInstance().removeFromBuyList(username,commoditytId);
        return OKPage.result();
        }
}
