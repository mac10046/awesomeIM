package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.Reviews;
import com.sls.awesomeim.repository.ReviewsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReviewsResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReviewsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_REVIEW_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REVIEW_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReviewsMockMvc;

    private Reviews reviews;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reviews createEntity(EntityManager em) {
        Reviews reviews = new Reviews()
            .name(DEFAULT_NAME)
            .message(DEFAULT_MESSAGE)
            .reviewDate(DEFAULT_REVIEW_DATE)
            .rating(DEFAULT_RATING)
            .designation(DEFAULT_DESIGNATION);
        return reviews;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reviews createUpdatedEntity(EntityManager em) {
        Reviews reviews = new Reviews()
            .name(UPDATED_NAME)
            .message(UPDATED_MESSAGE)
            .reviewDate(UPDATED_REVIEW_DATE)
            .rating(UPDATED_RATING)
            .designation(UPDATED_DESIGNATION);
        return reviews;
    }

    @BeforeEach
    public void initTest() {
        reviews = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviews() throws Exception {
        int databaseSizeBeforeCreate = reviewsRepository.findAll().size();
        // Create the Reviews
        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isCreated());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeCreate + 1);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReviews.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testReviews.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
        assertThat(testReviews.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testReviews.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    public void createReviewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewsRepository.findAll().size();

        // Create the Reviews with an existing ID
        reviews.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewsRepository.findAll().size();
        // set the field null
        reviews.setName(null);

        // Create the Reviews, which fails.


        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewsRepository.findAll().size();
        // set the field null
        reviews.setMessage(null);

        // Create the Reviews, which fails.


        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReviewDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewsRepository.findAll().size();
        // set the field null
        reviews.setReviewDate(null);

        // Create the Reviews, which fails.


        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewsRepository.findAll().size();
        // set the field null
        reviews.setRating(null);

        // Create the Reviews, which fails.


        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList
        restReviewsMockMvc.perform(get("/api/reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviews.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }
    
    @Test
    @Transactional
    public void getReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get the reviews
        restReviewsMockMvc.perform(get("/api/reviews/{id}", reviews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reviews.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }
    @Test
    @Transactional
    public void getNonExistingReviews() throws Exception {
        // Get the reviews
        restReviewsMockMvc.perform(get("/api/reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();

        // Update the reviews
        Reviews updatedReviews = reviewsRepository.findById(reviews.getId()).get();
        // Disconnect from session so that the updates on updatedReviews are not directly saved in db
        em.detach(updatedReviews);
        updatedReviews
            .name(UPDATED_NAME)
            .message(UPDATED_MESSAGE)
            .reviewDate(UPDATED_REVIEW_DATE)
            .rating(UPDATED_RATING)
            .designation(UPDATED_DESIGNATION);

        restReviewsMockMvc.perform(put("/api/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedReviews)))
            .andExpect(status().isOk());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReviews.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testReviews.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testReviews.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testReviews.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void updateNonExistingReviews() throws Exception {
        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewsMockMvc.perform(put("/api/reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reviews)))
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        int databaseSizeBeforeDelete = reviewsRepository.findAll().size();

        // Delete the reviews
        restReviewsMockMvc.perform(delete("/api/reviews/{id}", reviews.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
