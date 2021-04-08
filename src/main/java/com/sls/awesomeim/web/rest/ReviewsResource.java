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

import com.sls.awesomeim.domain.Reviews;
import com.sls.awesomeim.repository.ReviewsRepository;
import com.sls.awesomeim.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sls.awesomeim.domain.Reviews}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReviewsResource {

    private final Logger log = LoggerFactory.getLogger(ReviewsResource.class);

    private static final String ENTITY_NAME = "reviews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReviewsRepository reviewsRepository;

    public ReviewsResource(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    /**
     * {@code POST  /reviews} : Create a new reviews.
     *
     * @param reviews the reviews to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reviews, or with status {@code 400 (Bad Request)} if the reviews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reviews")
    public ResponseEntity<Reviews> createReviews(@Valid @RequestBody Reviews reviews) throws URISyntaxException {
        log.debug("REST request to save Reviews : {}", reviews);
        if (reviews.getId() != null) {
            throw new BadRequestAlertException("A new reviews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reviews result = reviewsRepository.save(reviews);
        return ResponseEntity.created(new URI("/api/reviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reviews} : Updates an existing reviews.
     *
     * @param reviews the reviews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reviews,
     * or with status {@code 400 (Bad Request)} if the reviews is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reviews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reviews")
    public ResponseEntity<Reviews> updateReviews(@Valid @RequestBody Reviews reviews) throws URISyntaxException {
        log.debug("REST request to update Reviews : {}", reviews);
        if (reviews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reviews result = reviewsRepository.save(reviews);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reviews.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reviews} : get all the reviews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviews in body.
     */
    @GetMapping("/reviews")
    public List<Reviews> getAllReviews() {
        log.debug("REST request to get all Reviews");
        return reviewsRepository.findAll();
    }

    /**
     * {@code GET  /reviews/:id} : get the "id" reviews.
     *
     * @param id the id of the reviews to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reviews, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reviews/{id}")
    public ResponseEntity<Reviews> getReviews(@PathVariable Long id) {
        log.debug("REST request to get Reviews : {}", id);
        Optional<Reviews> reviews = reviewsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reviews);
    }

    /**
     * {@code DELETE  /reviews/:id} : delete the "id" reviews.
     *
     * @param id the id of the reviews to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReviews(@PathVariable Long id) {
        log.debug("REST request to delete Reviews : {}", id);
        reviewsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
