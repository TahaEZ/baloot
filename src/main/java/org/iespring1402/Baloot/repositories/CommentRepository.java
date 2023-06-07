package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {

}
