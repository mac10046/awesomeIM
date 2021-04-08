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

import com.sls.awesomeim.domain.Shipping;
import com.sls.awesomeim.repository.ShippingRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.Shipping}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ShippingResource {

    private final Logger log = LoggerFactory.getLogger(ShippingResource.class);

    private static final String ENTITY_NAME = "shipping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShippingRepository shippingRepository;

    public ShippingResource(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    /**
     * {@code POST  /shippings} : Create a new shipping.
     *
     * @param shipping the shipping to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipping, or with status {@code 400 (Bad Request)} if the shipping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shippings")
    public ResponseEntity<Shipping> createShipping(@Valid @RequestBody Shipping shipping) throws URISyntaxException {
        log.debug("REST request to save Shipping : {}", shipping);
        if (shipping.getId() != null) {
            throw new BadRequestAlertException("A new shipping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Shipping result = shippingRepository.save(shipping);
        return ResponseEntity.created(new URI("/api/shippings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shippings} : Updates an existing shipping.
     *
     * @param shipping the shipping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipping,
     * or with status {@code 400 (Bad Request)} if the shipping is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shippings")
    public ResponseEntity<Shipping> updateShipping(@Valid @RequestBody Shipping shipping) throws URISyntaxException {
        log.debug("REST request to update Shipping : {}", shipping);
        if (shipping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Shipping result = shippingRepository.save(shipping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shipping.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shippings} : get all the shippings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shippings in body.
     */
    @GetMapping("/shippings")
    public ResponseEntity<List<Shipping>> getAllShippings(Pageable pageable) {
        log.debug("REST request to get a page of Shippings");
        Page<Shipping> page = shippingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shippings/:id} : get the "id" shipping.
     *
     * @param id the id of the shipping to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipping, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shippings/{id}")
    public ResponseEntity<Shipping> getShipping(@PathVariable Long id) {
        log.debug("REST request to get Shipping : {}", id);
        Optional<Shipping> shipping = shippingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shipping);
    }

    /**
     * {@code DELETE  /shippings/:id} : delete the "id" shipping.
     *
     * @param id the id of the shipping to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shippings/{id}")
    public ResponseEntity<Void> deleteShipping(@PathVariable Long id) {
        log.debug("REST request to delete Shipping : {}", id);
        shippingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
