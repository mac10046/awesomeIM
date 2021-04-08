package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.Quotes;
import com.sls.awesomeim.repository.QuotesRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QuotesResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuotesResourceIT {

    private static final String DEFAULT_GSTIN = "AAAAAAAAAA";
    private static final String UPDATED_GSTIN = "BBBBBBBBBB";

    private static final Instant DEFAULT_QUOTE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_QUOTE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TERMS = "AAAAAAAAAA";
    private static final String UPDATED_TERMS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Autowired
    private QuotesRepository quotesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotesMockMvc;

    private Quotes quotes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotes createEntity(EntityManager em) {
        Quotes quotes = new Quotes()
            .gstin(DEFAULT_GSTIN)
            .quoteDate(DEFAULT_QUOTE_DATE)
            .terms(DEFAULT_TERMS)
            .amount(DEFAULT_AMOUNT);
        return quotes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotes createUpdatedEntity(EntityManager em) {
        Quotes quotes = new Quotes()
            .gstin(UPDATED_GSTIN)
            .quoteDate(UPDATED_QUOTE_DATE)
            .terms(UPDATED_TERMS)
            .amount(UPDATED_AMOUNT);
        return quotes;
    }

    @BeforeEach
    public void initTest() {
        quotes = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuotes() throws Exception {
        int databaseSizeBeforeCreate = quotesRepository.findAll().size();
        // Create the Quotes
        restQuotesMockMvc.perform(post("/api/quotes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotes)))
            .andExpect(status().isCreated());

        // Validate the Quotes in the database
        List<Quotes> quotesList = quotesRepository.findAll();
        assertThat(quotesList).hasSize(databaseSizeBeforeCreate + 1);
        Quotes testQuotes = quotesList.get(quotesList.size() - 1);
        assertThat(testQuotes.getGstin()).isEqualTo(DEFAULT_GSTIN);
        assertThat(testQuotes.getQuoteDate()).isEqualTo(DEFAULT_QUOTE_DATE);
        assertThat(testQuotes.getTerms()).isEqualTo(DEFAULT_TERMS);
        assertThat(testQuotes.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createQuotesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quotesRepository.findAll().size();

        // Create the Quotes with an existing ID
        quotes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotesMockMvc.perform(post("/api/quotes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotes)))
            .andExpect(status().isBadRequest());

        // Validate the Quotes in the database
        List<Quotes> quotesList = quotesRepository.findAll();
        assertThat(quotesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuoteDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotesRepository.findAll().size();
        // set the field null
        quotes.setQuoteDate(null);

        // Create the Quotes, which fails.


        restQuotesMockMvc.perform(post("/api/quotes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotes)))
            .andExpect(status().isBadRequest());

        List<Quotes> quotesList = quotesRepository.findAll();
        assertThat(quotesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuotes() throws Exception {
        // Initialize the database
        quotesRepository.saveAndFlush(quotes);

        // Get all the quotesList
        restQuotesMockMvc.perform(get("/api/quotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].gstin").value(hasItem(DEFAULT_GSTIN)))
            .andExpect(jsonPath("$.[*].quoteDate").value(hasItem(DEFAULT_QUOTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].terms").value(hasItem(DEFAULT_TERMS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getQuotes() throws Exception {
        // Initialize the database
        quotesRepository.saveAndFlush(quotes);

        // Get the quotes
        restQuotesMockMvc.perform(get("/api/quotes/{id}", quotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotes.getId().intValue()))
            .andExpect(jsonPath("$.gstin").value(DEFAULT_GSTIN))
            .andExpect(jsonPath("$.quoteDate").value(DEFAULT_QUOTE_DATE.toString()))
            .andExpect(jsonPath("$.terms").value(DEFAULT_TERMS))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingQuotes() throws Exception {
        // Get the quotes
        restQuotesMockMvc.perform(get("/api/quotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuotes() throws Exception {
        // Initialize the database
        quotesRepository.saveAndFlush(quotes);

        int databaseSizeBeforeUpdate = quotesRepository.findAll().size();

        // Update the quotes
        Quotes updatedQuotes = quotesRepository.findById(quotes.getId()).get();
        // Disconnect from session so that the updates on updatedQuotes are not directly saved in db
        em.detach(updatedQuotes);
        updatedQuotes
            .gstin(UPDATED_GSTIN)
            .quoteDate(UPDATED_QUOTE_DATE)
            .terms(UPDATED_TERMS)
            .amount(UPDATED_AMOUNT);

        restQuotesMockMvc.perform(put("/api/quotes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuotes)))
            .andExpect(status().isOk());

        // Validate the Quotes in the database
        List<Quotes> quotesList = quotesRepository.findAll();
        assertThat(quotesList).hasSize(databaseSizeBeforeUpdate);
        Quotes testQuotes = quotesList.get(quotesList.size() - 1);
        assertThat(testQuotes.getGstin()).isEqualTo(UPDATED_GSTIN);
        assertThat(testQuotes.getQuoteDate()).isEqualTo(UPDATED_QUOTE_DATE);
        assertThat(testQuotes.getTerms()).isEqualTo(UPDATED_TERMS);
        assertThat(testQuotes.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingQuotes() throws Exception {
        int databaseSizeBeforeUpdate = quotesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotesMockMvc.perform(put("/api/quotes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(quotes)))
            .andExpect(status().isBadRequest());

        // Validate the Quotes in the database
        List<Quotes> quotesList = quotesRepository.findAll();
        assertThat(quotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuotes() throws Exception {
        // Initialize the database
        quotesRepository.saveAndFlush(quotes);

        int databaseSizeBeforeDelete = quotesRepository.findAll().size();

        // Delete the quotes
        restQuotesMockMvc.perform(delete("/api/quotes/{id}", quotes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quotes> quotesList = quotesRepository.findAll();
        assertThat(quotesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
