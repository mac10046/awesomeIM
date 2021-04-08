package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.Payments;
import com.sls.awesomeim.repository.PaymentsRepository;

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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaymentsResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentsResourceIT {

    private static final String DEFAULT_GATEWAY_ID = "AAAAAAAAAA";
    private static final String UPDATED_GATEWAY_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BANK_TXN = "AAAAAAAAAA";
    private static final String UPDATED_BANK_TXN = "BBBBBBBBBB";

    private static final String DEFAULT_TXN_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TXN_TOKEN = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESPONSE_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESPONSE_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CHECKSUM = "AAAAAAAAAA";
    private static final String UPDATED_CHECKSUM = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TXN_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TXN_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentsMockMvc;

    private Payments payments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createEntity(EntityManager em) {
        Payments payments = new Payments()
            .gatewayId(DEFAULT_GATEWAY_ID)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .bankTxn(DEFAULT_BANK_TXN)
            .txnToken(DEFAULT_TXN_TOKEN)
            .responseTimestamp(DEFAULT_RESPONSE_TIMESTAMP)
            .checksum(DEFAULT_CHECKSUM)
            .txnAmount(DEFAULT_TXN_AMOUNT)
            .bankName(DEFAULT_BANK_NAME)
            .responseCode(DEFAULT_RESPONSE_CODE)
            .responseMessage(DEFAULT_RESPONSE_MESSAGE)
            .result(DEFAULT_RESULT)
            .email(DEFAULT_EMAIL);
        return payments;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createUpdatedEntity(EntityManager em) {
        Payments payments = new Payments()
            .gatewayId(UPDATED_GATEWAY_ID)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .bankTxn(UPDATED_BANK_TXN)
            .txnToken(UPDATED_TXN_TOKEN)
            .responseTimestamp(UPDATED_RESPONSE_TIMESTAMP)
            .checksum(UPDATED_CHECKSUM)
            .txnAmount(UPDATED_TXN_AMOUNT)
            .bankName(UPDATED_BANK_NAME)
            .responseCode(UPDATED_RESPONSE_CODE)
            .responseMessage(UPDATED_RESPONSE_MESSAGE)
            .result(UPDATED_RESULT)
            .email(UPDATED_EMAIL);
        return payments;
    }

    @BeforeEach
    public void initTest() {
        payments = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayments() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();
        // Create the Payments
        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isCreated());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate + 1);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getGatewayId()).isEqualTo(DEFAULT_GATEWAY_ID);
        assertThat(testPayments.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPayments.getBankTxn()).isEqualTo(DEFAULT_BANK_TXN);
        assertThat(testPayments.getTxnToken()).isEqualTo(DEFAULT_TXN_TOKEN);
        assertThat(testPayments.getResponseTimestamp()).isEqualTo(DEFAULT_RESPONSE_TIMESTAMP);
        assertThat(testPayments.getChecksum()).isEqualTo(DEFAULT_CHECKSUM);
        assertThat(testPayments.getTxnAmount()).isEqualTo(DEFAULT_TXN_AMOUNT);
        assertThat(testPayments.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testPayments.getResponseCode()).isEqualTo(DEFAULT_RESPONSE_CODE);
        assertThat(testPayments.getResponseMessage()).isEqualTo(DEFAULT_RESPONSE_MESSAGE);
        assertThat(testPayments.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testPayments.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createPaymentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();

        // Create the Payments with an existing ID
        payments.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGatewayIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setGatewayId(null);

        // Create the Payments, which fails.


        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentsRepository.findAll().size();
        // set the field null
        payments.setPaymentDate(null);

        // Create the Payments, which fails.


        restPaymentsMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList
        restPaymentsMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payments.getId().intValue())))
            .andExpect(jsonPath("$.[*].gatewayId").value(hasItem(DEFAULT_GATEWAY_ID)))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankTxn").value(hasItem(DEFAULT_BANK_TXN)))
            .andExpect(jsonPath("$.[*].txnToken").value(hasItem(DEFAULT_TXN_TOKEN)))
            .andExpect(jsonPath("$.[*].responseTimestamp").value(hasItem(DEFAULT_RESPONSE_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].checksum").value(hasItem(DEFAULT_CHECKSUM)))
            .andExpect(jsonPath("$.[*].txnAmount").value(hasItem(DEFAULT_TXN_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].responseMessage").value(hasItem(DEFAULT_RESPONSE_MESSAGE)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get the payments
        restPaymentsMockMvc.perform(get("/api/payments/{id}", payments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payments.getId().intValue()))
            .andExpect(jsonPath("$.gatewayId").value(DEFAULT_GATEWAY_ID))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.bankTxn").value(DEFAULT_BANK_TXN))
            .andExpect(jsonPath("$.txnToken").value(DEFAULT_TXN_TOKEN))
            .andExpect(jsonPath("$.responseTimestamp").value(DEFAULT_RESPONSE_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.checksum").value(DEFAULT_CHECKSUM))
            .andExpect(jsonPath("$.txnAmount").value(DEFAULT_TXN_AMOUNT.intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.responseCode").value(DEFAULT_RESPONSE_CODE))
            .andExpect(jsonPath("$.responseMessage").value(DEFAULT_RESPONSE_MESSAGE))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
    @Test
    @Transactional
    public void getNonExistingPayments() throws Exception {
        // Get the payments
        restPaymentsMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments
        Payments updatedPayments = paymentsRepository.findById(payments.getId()).get();
        // Disconnect from session so that the updates on updatedPayments are not directly saved in db
        em.detach(updatedPayments);
        updatedPayments
            .gatewayId(UPDATED_GATEWAY_ID)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .bankTxn(UPDATED_BANK_TXN)
            .txnToken(UPDATED_TXN_TOKEN)
            .responseTimestamp(UPDATED_RESPONSE_TIMESTAMP)
            .checksum(UPDATED_CHECKSUM)
            .txnAmount(UPDATED_TXN_AMOUNT)
            .bankName(UPDATED_BANK_NAME)
            .responseCode(UPDATED_RESPONSE_CODE)
            .responseMessage(UPDATED_RESPONSE_MESSAGE)
            .result(UPDATED_RESULT)
            .email(UPDATED_EMAIL);

        restPaymentsMockMvc.perform(put("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayments)))
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getGatewayId()).isEqualTo(UPDATED_GATEWAY_ID);
        assertThat(testPayments.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPayments.getBankTxn()).isEqualTo(UPDATED_BANK_TXN);
        assertThat(testPayments.getTxnToken()).isEqualTo(UPDATED_TXN_TOKEN);
        assertThat(testPayments.getResponseTimestamp()).isEqualTo(UPDATED_RESPONSE_TIMESTAMP);
        assertThat(testPayments.getChecksum()).isEqualTo(UPDATED_CHECKSUM);
        assertThat(testPayments.getTxnAmount()).isEqualTo(UPDATED_TXN_AMOUNT);
        assertThat(testPayments.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPayments.getResponseCode()).isEqualTo(UPDATED_RESPONSE_CODE);
        assertThat(testPayments.getResponseMessage()).isEqualTo(UPDATED_RESPONSE_MESSAGE);
        assertThat(testPayments.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testPayments.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc.perform(put("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payments)))
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeDelete = paymentsRepository.findAll().size();

        // Delete the payments
        restPaymentsMockMvc.perform(delete("/api/payments/{id}", payments.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
