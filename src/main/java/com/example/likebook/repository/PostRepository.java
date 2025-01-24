package com.example.likebook.repository;

import com.example.likebook.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByUser_Username(String username);
    @Query("SELECT p FROM Post p Where p.user.username <> :username")
    List<Post> findAllByUser_UsernameNot(@Param("username") String username);

    @Query("SELECT count(p) FROM Post p Where p.user.username <> :username")
    Long findOtherPostsCount(@Param("username") String username);

    Optional<Post> findById(Long id);

}
