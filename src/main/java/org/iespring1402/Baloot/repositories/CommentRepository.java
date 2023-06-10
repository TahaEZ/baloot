package org.iespring1402.Baloot.repositories;

import org.iespring1402.Baloot.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {
List<Comment> findByCommodityId(int commodityId);
}
