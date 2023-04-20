package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Ads;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepos extends JpaRepository<Ads, Integer> {
    List<Ads> findAllByAuthorId(Integer authorId);
    Ads findByAuthorIdAndPk(Integer authorId, Integer pk);
}
