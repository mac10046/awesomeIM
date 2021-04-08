package com.sls.awesomeim.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sls.awesomeim.domain.Coupons;
import com.sls.awesomeim.repository.CouponsRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.Coupons}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CouponsResource {

    private final Logger log = LoggerFactory.getLogger(CouponsResource.class);

    private static final String ENTITY_NAME = "coupons";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CouponsRepository couponsRepository;

    public CouponsResource(CouponsRepository couponsRepository) {
        this.couponsRepository = couponsRepository;
    }

    /**
     * {@code POST  /coupons} : Create a new coupons.
     *
     * @param coupons the coupons to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coupons, or with status {@code 400 (Bad Request)} if the coupons has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coupons")
    public ResponseEntity<Coupons> createCoupons(@Valid @RequestBody Coupons coupons) throws URISyntaxException {
        log.debug("REST request to save Coupons : {}", coupons);
        if (coupons.getId() != null) {
            throw new BadRequestAlertException("A new coupons cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coupons result = couponsRepository.save(coupons);
        return ResponseEntity.created(new URI("/api/coupons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coupons} : Updates an existing coupons.
     *
     * @param coupons the coupons to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coupons,
     * or with status {@code 400 (Bad Request)} if the coupons is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coupons couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coupons")
    public ResponseEntity<Coupons> updateCoupons(@Valid @RequestBody Coupons coupons) throws URISyntaxException {
        log.debug("REST request to update Coupons : {}", coupons);
        if (coupons.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Coupons result = couponsRepository.save(coupons);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, coupons.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coupons} : get all the coupons.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coupons in body.
     */
    @GetMapping("/coupons")
    public List<Coupons> getAllCoupons(@RequestParam(required = false) String filter) {
        if ("cart-is-null".equals(filter)) {
            log.debug("REST request to get all Couponss where cart is null");
            return StreamSupport
                .stream(couponsRepository.findAll().spliterator(), false)
                .filter(coupons -> coupons.getCart() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Coupons");
        return couponsRepository.findAll();
    }

    /**
     * {@code GET  /coupons/:id} : get the "id" coupons.
     *
     * @param id the id of the coupons to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coupons, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coupons/{id}")
    public ResponseEntity<Coupons> getCoupons(@PathVariable Long id) {
        log.debug("REST request to get Coupons : {}", id);
        Optional<Coupons> coupons = couponsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(coupons);
    }

    /**
     * {@code DELETE  /coupons/:id} : delete the "id" coupons.
     *
     * @param id the id of the coupons to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<Void> deleteCoupons(@PathVariable Long id) {
        log.debug("REST request to delete Coupons : {}", id);
        couponsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
