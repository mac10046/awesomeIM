package com.sls.awesomeim.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sls.awesomeim.domain.Payments;
import com.sls.awesomeim.repository.PaymentsRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.Payments}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PaymentsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentsResource.class);

    private static final String ENTITY_NAME = "payments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentsRepository paymentsRepository;

    public PaymentsResource(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    /**
     * {@code POST  /payments} : Create a new payments.
     *
     * @param payments the payments to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payments, or with status {@code 400 (Bad Request)} if the payments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payments")
    public ResponseEntity<Payments> createPayments(@Valid @RequestBody Payments payments) throws URISyntaxException {
        log.debug("REST request to save Payments : {}", payments);
        if (payments.getId() != null) {
            throw new BadRequestAlertException("A new payments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Payments result = paymentsRepository.save(payments);
        return ResponseEntity.created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payments} : Updates an existing payments.
     *
     * @param payments the payments to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payments,
     * or with status {@code 400 (Bad Request)} if the payments is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payments couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments")
    public ResponseEntity<Payments> updatePayments(@Valid @RequestBody Payments payments) throws URISyntaxException {
        log.debug("REST request to update Payments : {}", payments);
        if (payments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Payments result = paymentsRepository.save(payments);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, payments.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payments} : get all the payments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payments in body.
     */
    @GetMapping("/payments")
    public ResponseEntity<List<Payments>> getAllPayments(Pageable pageable) {
        log.debug("REST request to get a page of Payments");
        Page<Payments> page = paymentsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payments/:id} : get the "id" payments.
     *
     * @param id the id of the payments to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payments, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments/{id}")
    public ResponseEntity<Payments> getPayments(@PathVariable Long id) {
        log.debug("REST request to get Payments : {}", id);
        Optional<Payments> payments = paymentsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payments);
    }

    /**
     * {@code DELETE  /payments/:id} : delete the "id" payments.
     *
     * @param id the id of the payments to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayments(@PathVariable Long id) {
        log.debug("REST request to delete Payments : {}", id);
        paymentsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
