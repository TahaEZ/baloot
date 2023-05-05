package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.models.Comment;
import org.iespring1402.Baloot.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {
    private Baloot balootInstance = Baloot.getInstance();

    @GetMapping(value = "")
    @ResponseBody
    public Object getComments(@RequestParam(value = "commodityId") int commodityId) {
        ArrayList<Comment> comments = balootInstance.getFilteredCommentsByCommodityId(commodityId);
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();

        for (Comment comment: comments) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("id", comment.getId());
            item.put("username", comment.getUsername());
            item.put("commodityId", comment.getCommodityId());
            item.put("text", comment.getText());
            item.put("date", comment.getDate());
            item.put("likes", comment.likesCount());
            item.put("dislikes", comment.dislikesCount());

            result.add(item);
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("comments", result);
        return response;
    }

    @PostMapping(value = "")
    @ResponseBody
    public Object submitComment(@RequestBody Comment comment) {
        Response addCommentRes = balootInstance.addComment(comment.getUsername(), comment.getCommodityId(), comment.getText());
        if (addCommentRes.success) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addCommentRes.data);
        }
    }

    @PostMapping(value = "/{commentId}")
    @ResponseBody
    public Object voteComment(@PathVariable("commentId") String commentId, @RequestParam(value = "vote") int vote, @RequestParam(value = "username") String username) {
        Response voteCommentRes = balootInstance.voteComment(username, commentId, vote);
        if (voteCommentRes.success) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(voteCommentRes.data);
        }
    }
}