package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.Coupons;
import com.sls.awesomeim.repository.CouponsRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CouponsResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CouponsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CouponsRepository couponsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCouponsMockMvc;

    private Coupons coupons;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coupons createEntity(EntityManager em) {
        Coupons coupons = new Coupons()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return coupons;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coupons createUpdatedEntity(EntityManager em) {
        Coupons coupons = new Coupons()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return coupons;
    }

    @BeforeEach
    public void initTest() {
        coupons = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoupons() throws Exception {
        int databaseSizeBeforeCreate = couponsRepository.findAll().size();
        // Create the Coupons
        restCouponsMockMvc.perform(post("/api/coupons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coupons)))
            .andExpect(status().isCreated());

        // Validate the Coupons in the database
        List<Coupons> couponsList = couponsRepository.findAll();
        assertThat(couponsList).hasSize(databaseSizeBeforeCreate + 1);
        Coupons testCoupons = couponsList.get(couponsList.size() - 1);
        assertThat(testCoupons.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoupons.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCoupons.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCoupons.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createCouponsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = couponsRepository.findAll().size();

        // Create the Coupons with an existing ID
        coupons.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouponsMockMvc.perform(post("/api/coupons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coupons)))
            .andExpect(status().isBadRequest());

        // Validate the Coupons in the database
        List<Coupons> couponsList = couponsRepository.findAll();
        assertThat(couponsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponsRepository.findAll().size();
        // set the field null
        coupons.setName(null);

        // Create the Coupons, which fails.


        restCouponsMockMvc.perform(post("/api/coupons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coupons)))
            .andExpect(status().isBadRequest());

        List<Coupons> couponsList = couponsRepository.findAll();
        assertThat(couponsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponsRepository.findAll().size();
        // set the field null
        coupons.setCode(null);

        // Create the Coupons, which fails.


        restCouponsMockMvc.perform(post("/api/coupons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coupons)))
            .andExpect(status().isBadRequest());

        List<Coupons> couponsList = couponsRepository.findAll();
        assertThat(couponsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponsRepository.findAll().size();
        // set the field null
        coupons.setStartDate(null);

        // Create the Coupons, which fails.


        restCouponsMockMvc.perform(post("/api/coupons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coupons)))
            .andExpect(status().isBadRequest());

        List<Coupons> couponsList = couponsRepository.findAll();
        assertThat(couponsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = couponsRepository.findAll().size();
        // set the field null
        coupons.setEndDate(null);

        // Create the Coupons, which fails.


        restCouponsMockMvc.perform(post("/api/coupons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coupons)))
            .andExpect(status().isBadRequest());

        List<Coupons> couponsList = couponsRepository.findAll();
        assertThat(couponsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoupons() throws Exception {
        // Initialize the database
        couponsRepository.saveAndFlush(coupons);

        // Get all the couponsList
        restCouponsMockMvc.perform(get("/api/coupons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coupons.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCoupons() throws Exception {
        // Initialize the database
        couponsRepository.saveAndFlush(coupons);

        // Get the coupons
        restCouponsMockMvc.perform(get("/api/coupons/{id}", coupons.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coupons.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCoupons() throws Exception {
        // Get the coupons
        restCouponsMockMvc.perform(get("/api/coupons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoupons() throws Exception {
        // Initialize the database
        couponsRepository.saveAndFlush(coupons);

        int databaseSizeBeforeUpdate = couponsRepository.findAll().size();

        // Update the coupons
        Coupons updatedCoupons = couponsRepository.findById(coupons.getId()).get();
        // Disconnect from session so that the updates on updatedCoupons are not directly saved in db
        em.detach(updatedCoupons);
        updatedCoupons
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restCouponsMockMvc.perform(put("/api/coupons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoupons)))
            .andExpect(status().isOk());

        // Validate the Coupons in the database
        List<Coupons> couponsList = couponsRepository.findAll();
        assertThat(couponsList).hasSize(databaseSizeBeforeUpdate);
        Coupons testCoupons = couponsList.get(couponsList.size() - 1);
        assertThat(testCoupons.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoupons.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCoupons.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCoupons.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCoupons() throws Exception {
        int databaseSizeBeforeUpdate = couponsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouponsMockMvc.perform(put("/api/coupons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coupons)))
            .andExpect(status().isBadRequest());

        // Validate the Coupons in the database
        List<Coupons> couponsList = couponsRepository.findAll();
        assertThat(couponsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoupons() throws Exception {
        // Initialize the database
        couponsRepository.saveAndFlush(coupons);

        int databaseSizeBeforeDelete = couponsRepository.findAll().size();

        // Delete the coupons
        restCouponsMockMvc.perform(delete("/api/coupons/{id}", coupons.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coupons> couponsList = couponsRepository.findAll();
        assertThat(couponsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
