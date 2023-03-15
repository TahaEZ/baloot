package org.iespring1402.webserver.pages;
import org.iespring1402.Baloot;
import org.iespring1402.User;
import java.io.IOException;


public class AddToCreditPage extends Page {
    public static String result(String username , long creditToAdd) throws IOException {
        User user = Baloot.getInstance().findUserByUsername(username);
        if(user == null)
            return ForbiddenPage.result();
        user.addCredit(creditToAdd);
        return OKPage.result();
    }
}
