package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sls.awesomeim.domain.Quotes;

/**
 * Spring Data  repository for the Quotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotesRepository extends JpaRepository<Quotes, Long> {
}
