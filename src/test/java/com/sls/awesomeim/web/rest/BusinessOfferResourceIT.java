package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.BusinessOffer;
import com.sls.awesomeim.repository.BusinessOfferRepository;

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
 * Integration tests for the {@link BusinessOfferResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BusinessOfferResourceIT {

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private BusinessOfferRepository businessOfferRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessOfferMockMvc;

    private BusinessOffer businessOffer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessOffer createEntity(EntityManager em) {
        BusinessOffer businessOffer = new BusinessOffer()
            .time(DEFAULT_TIME)
            .message(DEFAULT_MESSAGE)
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL);
        return businessOffer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessOffer createUpdatedEntity(EntityManager em) {
        BusinessOffer businessOffer = new BusinessOffer()
            .time(UPDATED_TIME)
            .message(UPDATED_MESSAGE)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL);
        return businessOffer;
    }

    @BeforeEach
    public void initTest() {
        businessOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessOffer() throws Exception {
        int databaseSizeBeforeCreate = businessOfferRepository.findAll().size();
        // Create the BusinessOffer
        restBusinessOfferMockMvc.perform(post("/api/business-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOffer)))
            .andExpect(status().isCreated());

        // Validate the BusinessOffer in the database
        List<BusinessOffer> businessOfferList = businessOfferRepository.findAll();
        assertThat(businessOfferList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessOffer testBusinessOffer = businessOfferList.get(businessOfferList.size() - 1);
        assertThat(testBusinessOffer.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testBusinessOffer.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testBusinessOffer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBusinessOffer.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createBusinessOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessOfferRepository.findAll().size();

        // Create the BusinessOffer with an existing ID
        businessOffer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessOfferMockMvc.perform(post("/api/business-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOffer)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessOffer in the database
        List<BusinessOffer> businessOfferList = businessOfferRepository.findAll();
        assertThat(businessOfferList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessOfferRepository.findAll().size();
        // set the field null
        businessOffer.setMessage(null);

        // Create the BusinessOffer, which fails.


        restBusinessOfferMockMvc.perform(post("/api/business-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOffer)))
            .andExpect(status().isBadRequest());

        List<BusinessOffer> businessOfferList = businessOfferRepository.findAll();
        assertThat(businessOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessOfferRepository.findAll().size();
        // set the field null
        businessOffer.setName(null);

        // Create the BusinessOffer, which fails.


        restBusinessOfferMockMvc.perform(post("/api/business-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOffer)))
            .andExpect(status().isBadRequest());

        List<BusinessOffer> businessOfferList = businessOfferRepository.findAll();
        assertThat(businessOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessOfferRepository.findAll().size();
        // set the field null
        businessOffer.setEmail(null);

        // Create the BusinessOffer, which fails.


        restBusinessOfferMockMvc.perform(post("/api/business-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOffer)))
            .andExpect(status().isBadRequest());

        List<BusinessOffer> businessOfferList = businessOfferRepository.findAll();
        assertThat(businessOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinessOffers() throws Exception {
        // Initialize the database
        businessOfferRepository.saveAndFlush(businessOffer);

        // Get all the businessOfferList
        restBusinessOfferMockMvc.perform(get("/api/business-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getBusinessOffer() throws Exception {
        // Initialize the database
        businessOfferRepository.saveAndFlush(businessOffer);

        // Get the businessOffer
        restBusinessOfferMockMvc.perform(get("/api/business-offers/{id}", businessOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessOffer.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
    @Test
    @Transactional
    public void getNonExistingBusinessOffer() throws Exception {
        // Get the businessOffer
        restBusinessOfferMockMvc.perform(get("/api/business-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessOffer() throws Exception {
        // Initialize the database
        businessOfferRepository.saveAndFlush(businessOffer);

        int databaseSizeBeforeUpdate = businessOfferRepository.findAll().size();

        // Update the businessOffer
        BusinessOffer updatedBusinessOffer = businessOfferRepository.findById(businessOffer.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessOffer are not directly saved in db
        em.detach(updatedBusinessOffer);
        updatedBusinessOffer
            .time(UPDATED_TIME)
            .message(UPDATED_MESSAGE)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL);

        restBusinessOfferMockMvc.perform(put("/api/business-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusinessOffer)))
            .andExpect(status().isOk());

        // Validate the BusinessOffer in the database
        List<BusinessOffer> businessOfferList = businessOfferRepository.findAll();
        assertThat(businessOfferList).hasSize(databaseSizeBeforeUpdate);
        BusinessOffer testBusinessOffer = businessOfferList.get(businessOfferList.size() - 1);
        assertThat(testBusinessOffer.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testBusinessOffer.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testBusinessOffer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessOffer.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessOffer() throws Exception {
        int databaseSizeBeforeUpdate = businessOfferRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessOfferMockMvc.perform(put("/api/business-offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOffer)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessOffer in the database
        List<BusinessOffer> businessOfferList = businessOfferRepository.findAll();
        assertThat(businessOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessOffer() throws Exception {
        // Initialize the database
        businessOfferRepository.saveAndFlush(businessOffer);

        int databaseSizeBeforeDelete = businessOfferRepository.findAll().size();

        // Delete the businessOffer
        restBusinessOfferMockMvc.perform(delete("/api/business-offers/{id}", businessOffer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessOffer> businessOfferList = businessOfferRepository.findAll();
        assertThat(businessOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
