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

import com.sls.awesomeim.domain.BusinessOffer;
import com.sls.awesomeim.repository.BusinessOfferRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.BusinessOffer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BusinessOfferResource {

    private final Logger log = LoggerFactory.getLogger(BusinessOfferResource.class);

    private static final String ENTITY_NAME = "businessOffer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessOfferRepository businessOfferRepository;

    public BusinessOfferResource(BusinessOfferRepository businessOfferRepository) {
        this.businessOfferRepository = businessOfferRepository;
    }

    /**
     * {@code POST  /business-offers} : Create a new businessOffer.
     *
     * @param businessOffer the businessOffer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessOffer, or with status {@code 400 (Bad Request)} if the businessOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-offers")
    public ResponseEntity<BusinessOffer> createBusinessOffer(@Valid @RequestBody BusinessOffer businessOffer) throws URISyntaxException {
        log.debug("REST request to save BusinessOffer : {}", businessOffer);
        if (businessOffer.getId() != null) {
            throw new BadRequestAlertException("A new businessOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessOffer result = businessOfferRepository.save(businessOffer);
        return ResponseEntity.created(new URI("/api/business-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-offers} : Updates an existing businessOffer.
     *
     * @param businessOffer the businessOffer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessOffer,
     * or with status {@code 400 (Bad Request)} if the businessOffer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessOffer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-offers")
    public ResponseEntity<BusinessOffer> updateBusinessOffer(@Valid @RequestBody BusinessOffer businessOffer) throws URISyntaxException {
        log.debug("REST request to update BusinessOffer : {}", businessOffer);
        if (businessOffer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessOffer result = businessOfferRepository.save(businessOffer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessOffer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /business-offers} : get all the businessOffers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessOffers in body.
     */
    @GetMapping("/business-offers")
    public List<BusinessOffer> getAllBusinessOffers() {
        log.debug("REST request to get all BusinessOffers");
        return businessOfferRepository.findAll();
    }

    /**
     * {@code GET  /business-offers/:id} : get the "id" businessOffer.
     *
     * @param id the id of the businessOffer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessOffer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-offers/{id}")
    public ResponseEntity<BusinessOffer> getBusinessOffer(@PathVariable Long id) {
        log.debug("REST request to get BusinessOffer : {}", id);
        Optional<BusinessOffer> businessOffer = businessOfferRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(businessOffer);
    }

    /**
     * {@code DELETE  /business-offers/:id} : delete the "id" businessOffer.
     *
     * @param id the id of the businessOffer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-offers/{id}")
    public ResponseEntity<Void> deleteBusinessOffer(@PathVariable Long id) {
        log.debug("REST request to delete BusinessOffer : {}", id);
        businessOfferRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
