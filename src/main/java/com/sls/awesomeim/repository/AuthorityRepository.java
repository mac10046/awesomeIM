package com.sls.awesomeim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sls.awesomeim.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
