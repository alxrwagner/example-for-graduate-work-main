package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Repository
public interface CommentRepos extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAdsPk(Long pk);

    Comment findByAdsPkAndPk(Long pk, Long adsPk);
}
