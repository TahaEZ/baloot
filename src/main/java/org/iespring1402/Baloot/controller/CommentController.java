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
        HashMap<String, Object> response = new HashMap<>();
        response.put("comments", comments);
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
}
