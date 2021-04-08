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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sls.awesomeim.domain.Shippers;
import com.sls.awesomeim.repository.ShippersRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.Shippers}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ShippersResource {

    private final Logger log = LoggerFactory.getLogger(ShippersResource.class);

    private static final String ENTITY_NAME = "shippers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShippersRepository shippersRepository;

    public ShippersResource(ShippersRepository shippersRepository) {
        this.shippersRepository = shippersRepository;
    }

    /**
     * {@code POST  /shippers} : Create a new shippers.
     *
     * @param shippers the shippers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shippers, or with status {@code 400 (Bad Request)} if the shippers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shippers")
    public ResponseEntity<Shippers> createShippers(@Valid @RequestBody Shippers shippers) throws URISyntaxException {
        log.debug("REST request to save Shippers : {}", shippers);
        if (shippers.getId() != null) {
            throw new BadRequestAlertException("A new shippers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Shippers result = shippersRepository.save(shippers);
        return ResponseEntity.created(new URI("/api/shippers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shippers} : Updates an existing shippers.
     *
     * @param shippers the shippers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shippers,
     * or with status {@code 400 (Bad Request)} if the shippers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shippers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shippers")
    public ResponseEntity<Shippers> updateShippers(@Valid @RequestBody Shippers shippers) throws URISyntaxException {
        log.debug("REST request to update Shippers : {}", shippers);
        if (shippers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Shippers result = shippersRepository.save(shippers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shippers.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shippers} : get all the shippers.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shippers in body.
     */
    @GetMapping("/shippers")
    public ResponseEntity<List<Shippers>> getAllShippers(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("shipping-is-null".equals(filter)) {
            log.debug("REST request to get all Shipperss where shipping is null");
            return new ResponseEntity<>(StreamSupport
                .stream(shippersRepository.findAll().spliterator(), false)
                .filter(shippers -> shippers.getShipping() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Shippers");
        Page<Shippers> page = shippersRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shippers/:id} : get the "id" shippers.
     *
     * @param id the id of the shippers to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shippers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shippers/{id}")
    public ResponseEntity<Shippers> getShippers(@PathVariable Long id) {
        log.debug("REST request to get Shippers : {}", id);
        Optional<Shippers> shippers = shippersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shippers);
    }

    /**
     * {@code DELETE  /shippers/:id} : delete the "id" shippers.
     *
     * @param id the id of the shippers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shippers/{id}")
    public ResponseEntity<Void> deleteShippers(@PathVariable Long id) {
        log.debug("REST request to delete Shippers : {}", id);
        shippersRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
