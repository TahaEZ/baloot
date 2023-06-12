package org.iespring1402.Baloot.repositories;

import java.util.List;

import org.hibernate.sql.Update;
import org.iespring1402.Baloot.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CommentDAO {
    @Autowired
    CommentRepository repo;

    @Transactional
    public void save(Comment comment) {
        repo.save(comment);
    }

    public Comment findById(String commentId) {
        return repo.findById(commentId).get();
    }
    public List<Comment> findByCommodityId(int commodityId)
    {
        return repo.findByCommodityId(commodityId);
    }

    public Comment findByIdAndUsername(String id , String username){
        return repo.findByUsernameAndId(username, id);
    }
    
}
