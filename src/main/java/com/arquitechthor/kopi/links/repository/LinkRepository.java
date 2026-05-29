package com.arquitechthor.kopi.links.repository;

import com.arquitechthor.kopi.links.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findAllByUserIdOrderByCategoryAsc(UUID userId);

    Optional<Link> findByIdAndUserId(Long id, UUID userId);

    void deleteByIdAndUserId(Long id, UUID userId);
}
