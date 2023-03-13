package org.iespring1402.webserver.pages;

import org.iespring1402.Baloot;
import org.iespring1402.Comment;
import org.iespring1402.User;

import java.io.IOException;

public class VoteCommentPage extends Page {
    public static String result(String username, String commentId, int vote) throws IOException {
        if (vote == 1 || vote == -1 || vote == 0) {
            User user = Baloot.getInstance().findUserByUsername(username);
            Comment comment = Baloot.getInstance().findCommentById(commentId);
            if (user == null || comment == null) {
                return NotFoundPage.result();
            } else {
                comment.voteComment(username, vote);
                return OKPage.result();
            }
        } else {
            return ForbiddenPage.result();
        }

    }
}
