package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {

}
