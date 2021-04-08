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

import com.sls.awesomeim.domain.Quotes;
import com.sls.awesomeim.repository.QuotesRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.Quotes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QuotesResource {

    private final Logger log = LoggerFactory.getLogger(QuotesResource.class);

    private static final String ENTITY_NAME = "quotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotesRepository quotesRepository;

    public QuotesResource(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    /**
     * {@code POST  /quotes} : Create a new quotes.
     *
     * @param quotes the quotes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotes, or with status {@code 400 (Bad Request)} if the quotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotes")
    public ResponseEntity<Quotes> createQuotes(@Valid @RequestBody Quotes quotes) throws URISyntaxException {
        log.debug("REST request to save Quotes : {}", quotes);
        if (quotes.getId() != null) {
            throw new BadRequestAlertException("A new quotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quotes result = quotesRepository.save(quotes);
        return ResponseEntity.created(new URI("/api/quotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quotes} : Updates an existing quotes.
     *
     * @param quotes the quotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotes,
     * or with status {@code 400 (Bad Request)} if the quotes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotes")
    public ResponseEntity<Quotes> updateQuotes(@Valid @RequestBody Quotes quotes) throws URISyntaxException {
        log.debug("REST request to update Quotes : {}", quotes);
        if (quotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Quotes result = quotesRepository.save(quotes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quotes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quotes} : get all the quotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotes in body.
     */
    @GetMapping("/quotes")
    public ResponseEntity<List<Quotes>> getAllQuotes(Pageable pageable) {
        log.debug("REST request to get a page of Quotes");
        Page<Quotes> page = quotesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quotes/:id} : get the "id" quotes.
     *
     * @param id the id of the quotes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotes/{id}")
    public ResponseEntity<Quotes> getQuotes(@PathVariable Long id) {
        log.debug("REST request to get Quotes : {}", id);
        Optional<Quotes> quotes = quotesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quotes);
    }

    /**
     * {@code DELETE  /quotes/:id} : delete the "id" quotes.
     *
     * @param id the id of the quotes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotes/{id}")
    public ResponseEntity<Void> deleteQuotes(@PathVariable Long id) {
        log.debug("REST request to delete Quotes : {}", id);
        quotesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
