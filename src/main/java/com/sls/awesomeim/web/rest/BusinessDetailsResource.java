package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.domain.BusinessDetails;
import com.sls.awesomeim.repository.BusinessDetailsRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.BusinessDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BusinessDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BusinessDetailsResource.class);

    private static final String ENTITY_NAME = "businessDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessDetailsRepository businessDetailsRepository;

    public BusinessDetailsResource(BusinessDetailsRepository businessDetailsRepository) {
        this.businessDetailsRepository = businessDetailsRepository;
    }

    /**
     * {@code POST  /business-details} : Create a new businessDetails.
     *
     * @param businessDetails the businessDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessDetails, or with status {@code 400 (Bad Request)} if the businessDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-details")
    public ResponseEntity<BusinessDetails> createBusinessDetails(@Valid @RequestBody BusinessDetails businessDetails) throws URISyntaxException {
        log.debug("REST request to save BusinessDetails : {}", businessDetails);
        if (businessDetails.getId() != null) {
            throw new BadRequestAlertException("A new businessDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessDetails result = businessDetailsRepository.save(businessDetails);
        return ResponseEntity.created(new URI("/api/business-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-details} : Updates an existing businessDetails.
     *
     * @param businessDetails the businessDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessDetails,
     * or with status {@code 400 (Bad Request)} if the businessDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-details")
    public ResponseEntity<BusinessDetails> updateBusinessDetails(@Valid @RequestBody BusinessDetails businessDetails) throws URISyntaxException {
        log.debug("REST request to update BusinessDetails : {}", businessDetails);
        if (businessDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessDetails result = businessDetailsRepository.save(businessDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /business-details} : get all the businessDetails.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessDetails in body.
     */
    @GetMapping("/business-details")
    public List<BusinessDetails> getAllBusinessDetails(@RequestParam(required = false) String filter) {
        if ("quotes-is-null".equals(filter)) {
            log.debug("REST request to get all BusinessDetailss where quotes is null");
            return StreamSupport
                .stream(businessDetailsRepository.findAll().spliterator(), false)
                .filter(businessDetails -> businessDetails.getQuotes() == null)
                .collect(Collectors.toList());
        }
        if ("invoices-is-null".equals(filter)) {
            log.debug("REST request to get all BusinessDetailss where invoices is null");
            return StreamSupport
                .stream(businessDetailsRepository.findAll().spliterator(), false)
                .filter(businessDetails -> businessDetails.getInvoices() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all BusinessDetails");
        return businessDetailsRepository.findAll();
    }

    /**
     * {@code GET  /business-details/:id} : get the "id" businessDetails.
     *
     * @param id the id of the businessDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-details/{id}")
    public ResponseEntity<BusinessDetails> getBusinessDetails(@PathVariable Long id) {
        log.debug("REST request to get BusinessDetails : {}", id);
        Optional<BusinessDetails> businessDetails = businessDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(businessDetails);
    }

    /**
     * {@code DELETE  /business-details/:id} : delete the "id" businessDetails.
     *
     * @param id the id of the businessDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-details/{id}")
    public ResponseEntity<Void> deleteBusinessDetails(@PathVariable Long id) {
        log.debug("REST request to delete BusinessDetails : {}", id);
        businessDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
