package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sls.awesomeim.domain.Coupons;

/**
 * Spring Data  repository for the Coupons entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Long> {
}
