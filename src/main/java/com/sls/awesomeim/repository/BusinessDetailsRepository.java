package com.sls.awesomeim.repository;

import com.sls.awesomeim.domain.BusinessDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the BusinessDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessDetailsRepository extends JpaRepository<BusinessDetails, Long> {

    @Query("select businessDetails from BusinessDetails businessDetails where businessDetails.user.login = ?#{principal.username}")
    List<BusinessDetails> findByUserIsCurrentUser();
}
