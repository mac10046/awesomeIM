package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sls.awesomeim.domain.DocDetails;

/**
 * Spring Data  repository for the DocDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocDetailsRepository extends JpaRepository<DocDetails, Long> {
}
