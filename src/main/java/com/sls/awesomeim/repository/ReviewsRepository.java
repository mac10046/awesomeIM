package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sls.awesomeim.domain.Reviews;

/**
 * Spring Data  repository for the Reviews entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
}
