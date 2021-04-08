package com.sls.awesomeim.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sls.awesomeim.domain.BusinessOpportunity;
import com.sls.awesomeim.repository.BusinessOpportunityRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.BusinessOpportunity}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BusinessOpportunityResource {

    private final Logger log = LoggerFactory.getLogger(BusinessOpportunityResource.class);

    private static final String ENTITY_NAME = "businessOpportunity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessOpportunityRepository businessOpportunityRepository;

    public BusinessOpportunityResource(BusinessOpportunityRepository businessOpportunityRepository) {
        this.businessOpportunityRepository = businessOpportunityRepository;
    }

    /**
     * {@code POST  /business-opportunities} : Create a new businessOpportunity.
     *
     * @param businessOpportunity the businessOpportunity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessOpportunity, or with status {@code 400 (Bad Request)} if the businessOpportunity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-opportunities")
    public ResponseEntity<BusinessOpportunity> createBusinessOpportunity(@Valid @RequestBody BusinessOpportunity businessOpportunity) throws URISyntaxException {
        log.debug("REST request to save BusinessOpportunity : {}", businessOpportunity);
        if (businessOpportunity.getId() != null) {
            throw new BadRequestAlertException("A new businessOpportunity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessOpportunity result = businessOpportunityRepository.save(businessOpportunity);
        return ResponseEntity.created(new URI("/api/business-opportunities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-opportunities} : Updates an existing businessOpportunity.
     *
     * @param businessOpportunity the businessOpportunity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessOpportunity,
     * or with status {@code 400 (Bad Request)} if the businessOpportunity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessOpportunity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-opportunities")
    public ResponseEntity<BusinessOpportunity> updateBusinessOpportunity(@Valid @RequestBody BusinessOpportunity businessOpportunity) throws URISyntaxException {
        log.debug("REST request to update BusinessOpportunity : {}", businessOpportunity);
        if (businessOpportunity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessOpportunity result = businessOpportunityRepository.save(businessOpportunity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessOpportunity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /business-opportunities} : get all the businessOpportunities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessOpportunities in body.
     */
    @GetMapping("/business-opportunities")
    public List<BusinessOpportunity> getAllBusinessOpportunities() {
        log.debug("REST request to get all BusinessOpportunities");
        return businessOpportunityRepository.findAll();
    }

    /**
     * {@code GET  /business-opportunities/:id} : get the "id" businessOpportunity.
     *
     * @param id the id of the businessOpportunity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessOpportunity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-opportunities/{id}")
    public ResponseEntity<BusinessOpportunity> getBusinessOpportunity(@PathVariable Long id) {
        log.debug("REST request to get BusinessOpportunity : {}", id);
        Optional<BusinessOpportunity> businessOpportunity = businessOpportunityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(businessOpportunity);
    }

    /**
     * {@code DELETE  /business-opportunities/:id} : delete the "id" businessOpportunity.
     *
     * @param id the id of the businessOpportunity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-opportunities/{id}")
    public ResponseEntity<Void> deleteBusinessOpportunity(@PathVariable Long id) {
        log.debug("REST request to delete BusinessOpportunity : {}", id);
        businessOpportunityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
