package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.DocDetails;
import com.sls.awesomeim.repository.DocDetailsRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocDetailsResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DocDetailsResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAXES = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAXES = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IS_TAX_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_IS_TAX_PERCENT = new BigDecimal(2);

    @Autowired
    private DocDetailsRepository docDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocDetailsMockMvc;

    private DocDetails docDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocDetails createEntity(EntityManager em) {
        DocDetails docDetails = new DocDetails()
            .description(DEFAULT_DESCRIPTION)
            .qty(DEFAULT_QTY)
            .price(DEFAULT_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .taxes(DEFAULT_TAXES)
            .isTaxPercent(DEFAULT_IS_TAX_PERCENT);
        return docDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocDetails createUpdatedEntity(EntityManager em) {
        DocDetails docDetails = new DocDetails()
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .price(UPDATED_PRICE)
            .discount(UPDATED_DISCOUNT)
            .taxes(UPDATED_TAXES)
            .isTaxPercent(UPDATED_IS_TAX_PERCENT);
        return docDetails;
    }

    @BeforeEach
    public void initTest() {
        docDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocDetails() throws Exception {
        int databaseSizeBeforeCreate = docDetailsRepository.findAll().size();
        // Create the DocDetails
        restDocDetailsMockMvc.perform(post("/api/doc-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDetails)))
            .andExpect(status().isCreated());

        // Validate the DocDetails in the database
        List<DocDetails> docDetailsList = docDetailsRepository.findAll();
        assertThat(docDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DocDetails testDocDetails = docDetailsList.get(docDetailsList.size() - 1);
        assertThat(testDocDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDocDetails.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testDocDetails.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testDocDetails.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testDocDetails.getTaxes()).isEqualTo(DEFAULT_TAXES);
        assertThat(testDocDetails.getIsTaxPercent()).isEqualTo(DEFAULT_IS_TAX_PERCENT);
    }

    @Test
    @Transactional
    public void createDocDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docDetailsRepository.findAll().size();

        // Create the DocDetails with an existing ID
        docDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocDetailsMockMvc.perform(post("/api/doc-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDetails)))
            .andExpect(status().isBadRequest());

        // Validate the DocDetails in the database
        List<DocDetails> docDetailsList = docDetailsRepository.findAll();
        assertThat(docDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = docDetailsRepository.findAll().size();
        // set the field null
        docDetails.setQty(null);

        // Create the DocDetails, which fails.


        restDocDetailsMockMvc.perform(post("/api/doc-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDetails)))
            .andExpect(status().isBadRequest());

        List<DocDetails> docDetailsList = docDetailsRepository.findAll();
        assertThat(docDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = docDetailsRepository.findAll().size();
        // set the field null
        docDetails.setPrice(null);

        // Create the DocDetails, which fails.


        restDocDetailsMockMvc.perform(post("/api/doc-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDetails)))
            .andExpect(status().isBadRequest());

        List<DocDetails> docDetailsList = docDetailsRepository.findAll();
        assertThat(docDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocDetails() throws Exception {
        // Initialize the database
        docDetailsRepository.saveAndFlush(docDetails);

        // Get all the docDetailsList
        restDocDetailsMockMvc.perform(get("/api/doc-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].taxes").value(hasItem(DEFAULT_TAXES.intValue())))
            .andExpect(jsonPath("$.[*].isTaxPercent").value(hasItem(DEFAULT_IS_TAX_PERCENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getDocDetails() throws Exception {
        // Initialize the database
        docDetailsRepository.saveAndFlush(docDetails);

        // Get the docDetails
        restDocDetailsMockMvc.perform(get("/api/doc-details/{id}", docDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(docDetails.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.taxes").value(DEFAULT_TAXES.intValue()))
            .andExpect(jsonPath("$.isTaxPercent").value(DEFAULT_IS_TAX_PERCENT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDocDetails() throws Exception {
        // Get the docDetails
        restDocDetailsMockMvc.perform(get("/api/doc-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocDetails() throws Exception {
        // Initialize the database
        docDetailsRepository.saveAndFlush(docDetails);

        int databaseSizeBeforeUpdate = docDetailsRepository.findAll().size();

        // Update the docDetails
        DocDetails updatedDocDetails = docDetailsRepository.findById(docDetails.getId()).get();
        // Disconnect from session so that the updates on updatedDocDetails are not directly saved in db
        em.detach(updatedDocDetails);
        updatedDocDetails
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .price(UPDATED_PRICE)
            .discount(UPDATED_DISCOUNT)
            .taxes(UPDATED_TAXES)
            .isTaxPercent(UPDATED_IS_TAX_PERCENT);

        restDocDetailsMockMvc.perform(put("/api/doc-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocDetails)))
            .andExpect(status().isOk());

        // Validate the DocDetails in the database
        List<DocDetails> docDetailsList = docDetailsRepository.findAll();
        assertThat(docDetailsList).hasSize(databaseSizeBeforeUpdate);
        DocDetails testDocDetails = docDetailsList.get(docDetailsList.size() - 1);
        assertThat(testDocDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDocDetails.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testDocDetails.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDocDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testDocDetails.getTaxes()).isEqualTo(UPDATED_TAXES);
        assertThat(testDocDetails.getIsTaxPercent()).isEqualTo(UPDATED_IS_TAX_PERCENT);
    }

    @Test
    @Transactional
    public void updateNonExistingDocDetails() throws Exception {
        int databaseSizeBeforeUpdate = docDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocDetailsMockMvc.perform(put("/api/doc-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(docDetails)))
            .andExpect(status().isBadRequest());

        // Validate the DocDetails in the database
        List<DocDetails> docDetailsList = docDetailsRepository.findAll();
        assertThat(docDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocDetails() throws Exception {
        // Initialize the database
        docDetailsRepository.saveAndFlush(docDetails);

        int databaseSizeBeforeDelete = docDetailsRepository.findAll().size();

        // Delete the docDetails
        restDocDetailsMockMvc.perform(delete("/api/doc-details/{id}", docDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocDetails> docDetailsList = docDetailsRepository.findAll();
        assertThat(docDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
