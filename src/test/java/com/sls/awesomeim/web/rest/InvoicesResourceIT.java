package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.Invoices;
import com.sls.awesomeim.repository.InvoicesRepository;

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
 * Integration tests for the {@link InvoicesResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoicesResourceIT {

    private static final String DEFAULT_GSTIN = "AAAAAAAAAA";
    private static final String UPDATED_GSTIN = "BBBBBBBBBB";

    private static final Instant DEFAULT_INVOICE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVOICE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TERMS = "AAAAAAAAAA";
    private static final String UPDATED_TERMS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PAID = false;
    private static final Boolean UPDATED_IS_PAID = true;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoicesMockMvc;

    private Invoices invoices;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoices createEntity(EntityManager em) {
        Invoices invoices = new Invoices()
            .gstin(DEFAULT_GSTIN)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .terms(DEFAULT_TERMS)
            .isPaid(DEFAULT_IS_PAID)
            .amount(DEFAULT_AMOUNT);
        return invoices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoices createUpdatedEntity(EntityManager em) {
        Invoices invoices = new Invoices()
            .gstin(UPDATED_GSTIN)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .terms(UPDATED_TERMS)
            .isPaid(UPDATED_IS_PAID)
            .amount(UPDATED_AMOUNT);
        return invoices;
    }

    @BeforeEach
    public void initTest() {
        invoices = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoices() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();
        // Create the Invoices
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoices)))
            .andExpect(status().isCreated());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate + 1);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getGstin()).isEqualTo(DEFAULT_GSTIN);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoices.getTerms()).isEqualTo(DEFAULT_TERMS);
        assertThat(testInvoices.isIsPaid()).isEqualTo(DEFAULT_IS_PAID);
        assertThat(testInvoices.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createInvoicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // Create the Invoices with an existing ID
        invoices.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoices)))
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInvoiceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = invoicesRepository.findAll().size();
        // set the field null
        invoices.setInvoiceDate(null);

        // Create the Invoices, which fails.


        restInvoicesMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoices)))
            .andExpect(status().isBadRequest());

        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList
        restInvoicesMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoices.getId().intValue())))
            .andExpect(jsonPath("$.[*].gstin").value(hasItem(DEFAULT_GSTIN)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].terms").value(hasItem(DEFAULT_TERMS)))
            .andExpect(jsonPath("$.[*].isPaid").value(hasItem(DEFAULT_IS_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", invoices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoices.getId().intValue()))
            .andExpect(jsonPath("$.gstin").value(DEFAULT_GSTIN))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.terms").value(DEFAULT_TERMS))
            .andExpect(jsonPath("$.isPaid").value(DEFAULT_IS_PAID.booleanValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingInvoices() throws Exception {
        // Get the invoices
        restInvoicesMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Update the invoices
        Invoices updatedInvoices = invoicesRepository.findById(invoices.getId()).get();
        // Disconnect from session so that the updates on updatedInvoices are not directly saved in db
        em.detach(updatedInvoices);
        updatedInvoices
            .gstin(UPDATED_GSTIN)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .terms(UPDATED_TERMS)
            .isPaid(UPDATED_IS_PAID)
            .amount(UPDATED_AMOUNT);

        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoices)))
            .andExpect(status().isOk());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getGstin()).isEqualTo(UPDATED_GSTIN);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoices.getTerms()).isEqualTo(UPDATED_TERMS);
        assertThat(testInvoices.isIsPaid()).isEqualTo(UPDATED_IS_PAID);
        assertThat(testInvoices.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoicesMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoices)))
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeDelete = invoicesRepository.findAll().size();

        // Delete the invoices
        restInvoicesMockMvc.perform(delete("/api/invoices/{id}", invoices.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
