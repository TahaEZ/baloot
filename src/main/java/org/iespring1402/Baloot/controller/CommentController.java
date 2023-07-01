package org.iespring1402.Baloot.controller;

import org.iespring1402.Baloot.models.Baloot;
import org.iespring1402.Baloot.entities.Comment;
import org.iespring1402.Baloot.repositories.CommentDAO;
import org.iespring1402.Baloot.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("api/v1/comments")
@CrossOrigin
public class CommentController {
    @Autowired
    CommentDAO commentDAO;


    @GetMapping(value = "")
    @ResponseBody
    public Object getComments(@RequestParam(value = "commodityId") int commodityId,
            @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        ArrayList<Comment> comments = (ArrayList<Comment>) commentDAO.findByCommodityId(commodityId);
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();

        for (Comment comment : comments) {
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
    public Object submitComment(@RequestBody Comment comment, @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        commentDAO.save(comment);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(value = "/{commentId}")
    @ResponseBody
    public Object voteComment(@PathVariable("commentId") String commentId, @RequestParam(value = "vote") int vote,
            @RequestParam(value = "username") String username, @RequestAttribute boolean unauthorized) {
        if (unauthorized) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing authorization");
        }

        Comment comment = commentDAO.findByIdAndUsername(commentId,username);
        if (comment != null &&  2 > vote && vote > -2) {
            comment.voteComment(username, vote);
            commentDAO.save(comment);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment Not Found!");
        }
    }
}
