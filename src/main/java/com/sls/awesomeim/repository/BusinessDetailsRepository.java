package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sls.awesomeim.domain.BusinessDetails;

/**
 * Spring Data  repository for the BusinessDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessDetailsRepository extends JpaRepository<BusinessDetails, Long> {
}
