package ru.nikkitavr.geotagger.users_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikkitavr.geotagger.users_service.model.Relationship;
import ru.nikkitavr.geotagger.users_service.model.User;

@Repository
public interface RelationshipsRepository extends JpaRepository<Relationship, Long> {
}
