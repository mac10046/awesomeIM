package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sls.awesomeim.domain.Taxes;

/**
 * Spring Data  repository for the Taxes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxesRepository extends JpaRepository<Taxes, Long> {
}
