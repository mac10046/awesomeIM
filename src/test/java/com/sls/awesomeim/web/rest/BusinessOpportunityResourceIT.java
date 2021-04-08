package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.BusinessOpportunity;
import com.sls.awesomeim.repository.BusinessOpportunityRepository;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BusinessOpportunityResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BusinessOpportunityResourceIT {

    private static final BigDecimal DEFAULT_INVESTMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INVESTMENT_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLAN_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BusinessOpportunityRepository businessOpportunityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessOpportunityMockMvc;

    private BusinessOpportunity businessOpportunity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessOpportunity createEntity(EntityManager em) {
        BusinessOpportunity businessOpportunity = new BusinessOpportunity()
            .investmentAmount(DEFAULT_INVESTMENT_AMOUNT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .planDescription(DEFAULT_PLAN_DESCRIPTION);
        return businessOpportunity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessOpportunity createUpdatedEntity(EntityManager em) {
        BusinessOpportunity businessOpportunity = new BusinessOpportunity()
            .investmentAmount(UPDATED_INVESTMENT_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .planDescription(UPDATED_PLAN_DESCRIPTION);
        return businessOpportunity;
    }

    @BeforeEach
    public void initTest() {
        businessOpportunity = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessOpportunity() throws Exception {
        int databaseSizeBeforeCreate = businessOpportunityRepository.findAll().size();
        // Create the BusinessOpportunity
        restBusinessOpportunityMockMvc.perform(post("/api/business-opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOpportunity)))
            .andExpect(status().isCreated());

        // Validate the BusinessOpportunity in the database
        List<BusinessOpportunity> businessOpportunityList = businessOpportunityRepository.findAll();
        assertThat(businessOpportunityList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessOpportunity testBusinessOpportunity = businessOpportunityList.get(businessOpportunityList.size() - 1);
        assertThat(testBusinessOpportunity.getInvestmentAmount()).isEqualTo(DEFAULT_INVESTMENT_AMOUNT);
        assertThat(testBusinessOpportunity.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBusinessOpportunity.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBusinessOpportunity.getPlanDescription()).isEqualTo(DEFAULT_PLAN_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBusinessOpportunityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessOpportunityRepository.findAll().size();

        // Create the BusinessOpportunity with an existing ID
        businessOpportunity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessOpportunityMockMvc.perform(post("/api/business-opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOpportunity)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessOpportunity in the database
        List<BusinessOpportunity> businessOpportunityList = businessOpportunityRepository.findAll();
        assertThat(businessOpportunityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInvestmentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessOpportunityRepository.findAll().size();
        // set the field null
        businessOpportunity.setInvestmentAmount(null);

        // Create the BusinessOpportunity, which fails.


        restBusinessOpportunityMockMvc.perform(post("/api/business-opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOpportunity)))
            .andExpect(status().isBadRequest());

        List<BusinessOpportunity> businessOpportunityList = businessOpportunityRepository.findAll();
        assertThat(businessOpportunityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlanDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessOpportunityRepository.findAll().size();
        // set the field null
        businessOpportunity.setPlanDescription(null);

        // Create the BusinessOpportunity, which fails.


        restBusinessOpportunityMockMvc.perform(post("/api/business-opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOpportunity)))
            .andExpect(status().isBadRequest());

        List<BusinessOpportunity> businessOpportunityList = businessOpportunityRepository.findAll();
        assertThat(businessOpportunityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinessOpportunities() throws Exception {
        // Initialize the database
        businessOpportunityRepository.saveAndFlush(businessOpportunity);

        // Get all the businessOpportunityList
        restBusinessOpportunityMockMvc.perform(get("/api/business-opportunities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessOpportunity.getId().intValue())))
            .andExpect(jsonPath("$.[*].investmentAmount").value(hasItem(DEFAULT_INVESTMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].planDescription").value(hasItem(DEFAULT_PLAN_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getBusinessOpportunity() throws Exception {
        // Initialize the database
        businessOpportunityRepository.saveAndFlush(businessOpportunity);

        // Get the businessOpportunity
        restBusinessOpportunityMockMvc.perform(get("/api/business-opportunities/{id}", businessOpportunity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessOpportunity.getId().intValue()))
            .andExpect(jsonPath("$.investmentAmount").value(DEFAULT_INVESTMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.planDescription").value(DEFAULT_PLAN_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingBusinessOpportunity() throws Exception {
        // Get the businessOpportunity
        restBusinessOpportunityMockMvc.perform(get("/api/business-opportunities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessOpportunity() throws Exception {
        // Initialize the database
        businessOpportunityRepository.saveAndFlush(businessOpportunity);

        int databaseSizeBeforeUpdate = businessOpportunityRepository.findAll().size();

        // Update the businessOpportunity
        BusinessOpportunity updatedBusinessOpportunity = businessOpportunityRepository.findById(businessOpportunity.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessOpportunity are not directly saved in db
        em.detach(updatedBusinessOpportunity);
        updatedBusinessOpportunity
            .investmentAmount(UPDATED_INVESTMENT_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .planDescription(UPDATED_PLAN_DESCRIPTION);

        restBusinessOpportunityMockMvc.perform(put("/api/business-opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusinessOpportunity)))
            .andExpect(status().isOk());

        // Validate the BusinessOpportunity in the database
        List<BusinessOpportunity> businessOpportunityList = businessOpportunityRepository.findAll();
        assertThat(businessOpportunityList).hasSize(databaseSizeBeforeUpdate);
        BusinessOpportunity testBusinessOpportunity = businessOpportunityList.get(businessOpportunityList.size() - 1);
        assertThat(testBusinessOpportunity.getInvestmentAmount()).isEqualTo(UPDATED_INVESTMENT_AMOUNT);
        assertThat(testBusinessOpportunity.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBusinessOpportunity.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBusinessOpportunity.getPlanDescription()).isEqualTo(UPDATED_PLAN_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = businessOpportunityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessOpportunityMockMvc.perform(put("/api/business-opportunities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessOpportunity)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessOpportunity in the database
        List<BusinessOpportunity> businessOpportunityList = businessOpportunityRepository.findAll();
        assertThat(businessOpportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessOpportunity() throws Exception {
        // Initialize the database
        businessOpportunityRepository.saveAndFlush(businessOpportunity);

        int databaseSizeBeforeDelete = businessOpportunityRepository.findAll().size();

        // Delete the businessOpportunity
        restBusinessOpportunityMockMvc.perform(delete("/api/business-opportunities/{id}", businessOpportunity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessOpportunity> businessOpportunityList = businessOpportunityRepository.findAll();
        assertThat(businessOpportunityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
