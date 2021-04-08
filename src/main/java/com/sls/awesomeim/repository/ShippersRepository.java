package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sls.awesomeim.domain.Shippers;

/**
 * Spring Data  repository for the Shippers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippersRepository extends JpaRepository<Shippers, Long> {
}
