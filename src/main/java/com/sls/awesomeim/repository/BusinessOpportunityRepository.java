package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sls.awesomeim.domain.BusinessOpportunity;

/**
 * Spring Data  repository for the BusinessOpportunity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessOpportunityRepository extends JpaRepository<BusinessOpportunity, Long> {
}
