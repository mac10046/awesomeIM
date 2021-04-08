package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sls.awesomeim.domain.BusinessOffer;

/**
 * Spring Data  repository for the BusinessOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessOfferRepository extends JpaRepository<BusinessOffer, Long> {
}
