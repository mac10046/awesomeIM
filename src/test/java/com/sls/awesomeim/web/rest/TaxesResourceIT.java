package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.Taxes;
import com.sls.awesomeim.repository.TaxesRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TaxesResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaxesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PERCENT = false;
    private static final Boolean UPDATED_IS_PERCENT = true;

    private static final Double DEFAULT_FIGURE = 1D;
    private static final Double UPDATED_FIGURE = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TaxesRepository taxesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxesMockMvc;

    private Taxes taxes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taxes createEntity(EntityManager em) {
        Taxes taxes = new Taxes()
            .name(DEFAULT_NAME)
            .isPercent(DEFAULT_IS_PERCENT)
            .figure(DEFAULT_FIGURE)
            .description(DEFAULT_DESCRIPTION);
        return taxes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taxes createUpdatedEntity(EntityManager em) {
        Taxes taxes = new Taxes()
            .name(UPDATED_NAME)
            .isPercent(UPDATED_IS_PERCENT)
            .figure(UPDATED_FIGURE)
            .description(UPDATED_DESCRIPTION);
        return taxes;
    }

    @BeforeEach
    public void initTest() {
        taxes = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxes() throws Exception {
        int databaseSizeBeforeCreate = taxesRepository.findAll().size();
        // Create the Taxes
        restTaxesMockMvc.perform(post("/api/taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxes)))
            .andExpect(status().isCreated());

        // Validate the Taxes in the database
        List<Taxes> taxesList = taxesRepository.findAll();
        assertThat(taxesList).hasSize(databaseSizeBeforeCreate + 1);
        Taxes testTaxes = taxesList.get(taxesList.size() - 1);
        assertThat(testTaxes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaxes.isIsPercent()).isEqualTo(DEFAULT_IS_PERCENT);
        assertThat(testTaxes.getFigure()).isEqualTo(DEFAULT_FIGURE);
        assertThat(testTaxes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTaxesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxesRepository.findAll().size();

        // Create the Taxes with an existing ID
        taxes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxesMockMvc.perform(post("/api/taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxes)))
            .andExpect(status().isBadRequest());

        // Validate the Taxes in the database
        List<Taxes> taxesList = taxesRepository.findAll();
        assertThat(taxesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxesRepository.findAll().size();
        // set the field null
        taxes.setName(null);

        // Create the Taxes, which fails.


        restTaxesMockMvc.perform(post("/api/taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxes)))
            .andExpect(status().isBadRequest());

        List<Taxes> taxesList = taxesRepository.findAll();
        assertThat(taxesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxesRepository.findAll().size();
        // set the field null
        taxes.setIsPercent(null);

        // Create the Taxes, which fails.


        restTaxesMockMvc.perform(post("/api/taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxes)))
            .andExpect(status().isBadRequest());

        List<Taxes> taxesList = taxesRepository.findAll();
        assertThat(taxesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFigureIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxesRepository.findAll().size();
        // set the field null
        taxes.setFigure(null);

        // Create the Taxes, which fails.


        restTaxesMockMvc.perform(post("/api/taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxes)))
            .andExpect(status().isBadRequest());

        List<Taxes> taxesList = taxesRepository.findAll();
        assertThat(taxesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxes() throws Exception {
        // Initialize the database
        taxesRepository.saveAndFlush(taxes);

        // Get all the taxesList
        restTaxesMockMvc.perform(get("/api/taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isPercent").value(hasItem(DEFAULT_IS_PERCENT.booleanValue())))
            .andExpect(jsonPath("$.[*].figure").value(hasItem(DEFAULT_FIGURE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getTaxes() throws Exception {
        // Initialize the database
        taxesRepository.saveAndFlush(taxes);

        // Get the taxes
        restTaxesMockMvc.perform(get("/api/taxes/{id}", taxes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isPercent").value(DEFAULT_IS_PERCENT.booleanValue()))
            .andExpect(jsonPath("$.figure").value(DEFAULT_FIGURE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingTaxes() throws Exception {
        // Get the taxes
        restTaxesMockMvc.perform(get("/api/taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxes() throws Exception {
        // Initialize the database
        taxesRepository.saveAndFlush(taxes);

        int databaseSizeBeforeUpdate = taxesRepository.findAll().size();

        // Update the taxes
        Taxes updatedTaxes = taxesRepository.findById(taxes.getId()).get();
        // Disconnect from session so that the updates on updatedTaxes are not directly saved in db
        em.detach(updatedTaxes);
        updatedTaxes
            .name(UPDATED_NAME)
            .isPercent(UPDATED_IS_PERCENT)
            .figure(UPDATED_FIGURE)
            .description(UPDATED_DESCRIPTION);

        restTaxesMockMvc.perform(put("/api/taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxes)))
            .andExpect(status().isOk());

        // Validate the Taxes in the database
        List<Taxes> taxesList = taxesRepository.findAll();
        assertThat(taxesList).hasSize(databaseSizeBeforeUpdate);
        Taxes testTaxes = taxesList.get(taxesList.size() - 1);
        assertThat(testTaxes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxes.isIsPercent()).isEqualTo(UPDATED_IS_PERCENT);
        assertThat(testTaxes.getFigure()).isEqualTo(UPDATED_FIGURE);
        assertThat(testTaxes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxes() throws Exception {
        int databaseSizeBeforeUpdate = taxesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxesMockMvc.perform(put("/api/taxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxes)))
            .andExpect(status().isBadRequest());

        // Validate the Taxes in the database
        List<Taxes> taxesList = taxesRepository.findAll();
        assertThat(taxesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxes() throws Exception {
        // Initialize the database
        taxesRepository.saveAndFlush(taxes);

        int databaseSizeBeforeDelete = taxesRepository.findAll().size();

        // Delete the taxes
        restTaxesMockMvc.perform(delete("/api/taxes/{id}", taxes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Taxes> taxesList = taxesRepository.findAll();
        assertThat(taxesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
