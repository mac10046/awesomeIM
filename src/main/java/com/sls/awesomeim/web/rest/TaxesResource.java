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

import com.sls.awesomeim.domain.Taxes;
import com.sls.awesomeim.repository.TaxesRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.Taxes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaxesResource {

    private final Logger log = LoggerFactory.getLogger(TaxesResource.class);

    private static final String ENTITY_NAME = "taxes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaxesRepository taxesRepository;

    public TaxesResource(TaxesRepository taxesRepository) {
        this.taxesRepository = taxesRepository;
    }

    /**
     * {@code POST  /taxes} : Create a new taxes.
     *
     * @param taxes the taxes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taxes, or with status {@code 400 (Bad Request)} if the taxes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taxes")
    public ResponseEntity<Taxes> createTaxes(@Valid @RequestBody Taxes taxes) throws URISyntaxException {
        log.debug("REST request to save Taxes : {}", taxes);
        if (taxes.getId() != null) {
            throw new BadRequestAlertException("A new taxes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Taxes result = taxesRepository.save(taxes);
        return ResponseEntity.created(new URI("/api/taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taxes} : Updates an existing taxes.
     *
     * @param taxes the taxes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taxes,
     * or with status {@code 400 (Bad Request)} if the taxes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taxes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taxes")
    public ResponseEntity<Taxes> updateTaxes(@Valid @RequestBody Taxes taxes) throws URISyntaxException {
        log.debug("REST request to update Taxes : {}", taxes);
        if (taxes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Taxes result = taxesRepository.save(taxes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taxes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /taxes} : get all the taxes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taxes in body.
     */
    @GetMapping("/taxes")
    public List<Taxes> getAllTaxes() {
        log.debug("REST request to get all Taxes");
        return taxesRepository.findAll();
    }

    /**
     * {@code GET  /taxes/:id} : get the "id" taxes.
     *
     * @param id the id of the taxes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taxes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taxes/{id}")
    public ResponseEntity<Taxes> getTaxes(@PathVariable Long id) {
        log.debug("REST request to get Taxes : {}", id);
        Optional<Taxes> taxes = taxesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taxes);
    }

    /**
     * {@code DELETE  /taxes/:id} : delete the "id" taxes.
     *
     * @param id the id of the taxes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taxes/{id}")
    public ResponseEntity<Void> deleteTaxes(@PathVariable Long id) {
        log.debug("REST request to delete Taxes : {}", id);
        taxesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
