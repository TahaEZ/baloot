package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentDAO {
    @Autowired
    CommentRepository repo;

    public void save(Comment comment) {
        repo.save(comment);
    }
}
