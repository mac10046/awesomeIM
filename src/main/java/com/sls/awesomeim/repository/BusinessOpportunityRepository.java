package com.sls.awesomeim.repository;

import com.sls.awesomeim.domain.BusinessOpportunity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BusinessOpportunity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessOpportunityRepository extends JpaRepository<BusinessOpportunity, Long> {
}
