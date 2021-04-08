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

import com.sls.awesomeim.domain.DocDetails;
import com.sls.awesomeim.repository.DocDetailsRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.DocDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DocDetailsResource.class);

    private static final String ENTITY_NAME = "docDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocDetailsRepository docDetailsRepository;

    public DocDetailsResource(DocDetailsRepository docDetailsRepository) {
        this.docDetailsRepository = docDetailsRepository;
    }

    /**
     * {@code POST  /doc-details} : Create a new docDetails.
     *
     * @param docDetails the docDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docDetails, or with status {@code 400 (Bad Request)} if the docDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doc-details")
    public ResponseEntity<DocDetails> createDocDetails(@Valid @RequestBody DocDetails docDetails) throws URISyntaxException {
        log.debug("REST request to save DocDetails : {}", docDetails);
        if (docDetails.getId() != null) {
            throw new BadRequestAlertException("A new docDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocDetails result = docDetailsRepository.save(docDetails);
        return ResponseEntity.created(new URI("/api/doc-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doc-details} : Updates an existing docDetails.
     *
     * @param docDetails the docDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docDetails,
     * or with status {@code 400 (Bad Request)} if the docDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doc-details")
    public ResponseEntity<DocDetails> updateDocDetails(@Valid @RequestBody DocDetails docDetails) throws URISyntaxException {
        log.debug("REST request to update DocDetails : {}", docDetails);
        if (docDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocDetails result = docDetailsRepository.save(docDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, docDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doc-details} : get all the docDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docDetails in body.
     */
    @GetMapping("/doc-details")
    public List<DocDetails> getAllDocDetails() {
        log.debug("REST request to get all DocDetails");
        return docDetailsRepository.findAll();
    }

    /**
     * {@code GET  /doc-details/:id} : get the "id" docDetails.
     *
     * @param id the id of the docDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doc-details/{id}")
    public ResponseEntity<DocDetails> getDocDetails(@PathVariable Long id) {
        log.debug("REST request to get DocDetails : {}", id);
        Optional<DocDetails> docDetails = docDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(docDetails);
    }

    /**
     * {@code DELETE  /doc-details/:id} : delete the "id" docDetails.
     *
     * @param id the id of the docDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doc-details/{id}")
    public ResponseEntity<Void> deleteDocDetails(@PathVariable Long id) {
        log.debug("REST request to delete DocDetails : {}", id);
        docDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
