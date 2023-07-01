package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;


@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {
List<Comment> findByCommodityId(int commodityId);   
Comment findByUsernameAndId(String username, String id);

}
