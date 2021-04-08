package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.OrderDetails;
import com.sls.awesomeim.repository.OrderDetailsRepository;

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
 * Integration tests for the {@link OrderDetailsResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderDetailsResourceIT {

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
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderDetailsMockMvc;

    private OrderDetails orderDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDetails createEntity(EntityManager em) {
        OrderDetails orderDetails = new OrderDetails()
            .description(DEFAULT_DESCRIPTION)
            .qty(DEFAULT_QTY)
            .price(DEFAULT_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .taxes(DEFAULT_TAXES)
            .isTaxPercent(DEFAULT_IS_TAX_PERCENT);
        return orderDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDetails createUpdatedEntity(EntityManager em) {
        OrderDetails orderDetails = new OrderDetails()
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .price(UPDATED_PRICE)
            .discount(UPDATED_DISCOUNT)
            .taxes(UPDATED_TAXES)
            .isTaxPercent(UPDATED_IS_TAX_PERCENT);
        return orderDetails;
    }

    @BeforeEach
    public void initTest() {
        orderDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderDetails() throws Exception {
        int databaseSizeBeforeCreate = orderDetailsRepository.findAll().size();
        // Create the OrderDetails
        restOrderDetailsMockMvc.perform(post("/api/order-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDetails)))
            .andExpect(status().isCreated());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        OrderDetails testOrderDetails = orderDetailsList.get(orderDetailsList.size() - 1);
        assertThat(testOrderDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrderDetails.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testOrderDetails.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOrderDetails.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testOrderDetails.getTaxes()).isEqualTo(DEFAULT_TAXES);
        assertThat(testOrderDetails.getIsTaxPercent()).isEqualTo(DEFAULT_IS_TAX_PERCENT);
    }

    @Test
    @Transactional
    public void createOrderDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderDetailsRepository.findAll().size();

        // Create the OrderDetails with an existing ID
        orderDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderDetailsMockMvc.perform(post("/api/order-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDetails)))
            .andExpect(status().isBadRequest());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQtyIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderDetailsRepository.findAll().size();
        // set the field null
        orderDetails.setQty(null);

        // Create the OrderDetails, which fails.


        restOrderDetailsMockMvc.perform(post("/api/order-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDetails)))
            .andExpect(status().isBadRequest());

        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderDetailsRepository.findAll().size();
        // set the field null
        orderDetails.setPrice(null);

        // Create the OrderDetails, which fails.


        restOrderDetailsMockMvc.perform(post("/api/order-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDetails)))
            .andExpect(status().isBadRequest());

        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        // Get all the orderDetailsList
        restOrderDetailsMockMvc.perform(get("/api/order-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].taxes").value(hasItem(DEFAULT_TAXES.intValue())))
            .andExpect(jsonPath("$.[*].isTaxPercent").value(hasItem(DEFAULT_IS_TAX_PERCENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        // Get the orderDetails
        restOrderDetailsMockMvc.perform(get("/api/order-details/{id}", orderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderDetails.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.taxes").value(DEFAULT_TAXES.intValue()))
            .andExpect(jsonPath("$.isTaxPercent").value(DEFAULT_IS_TAX_PERCENT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOrderDetails() throws Exception {
        // Get the orderDetails
        restOrderDetailsMockMvc.perform(get("/api/order-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();

        // Update the orderDetails
        OrderDetails updatedOrderDetails = orderDetailsRepository.findById(orderDetails.getId()).get();
        // Disconnect from session so that the updates on updatedOrderDetails are not directly saved in db
        em.detach(updatedOrderDetails);
        updatedOrderDetails
            .description(UPDATED_DESCRIPTION)
            .qty(UPDATED_QTY)
            .price(UPDATED_PRICE)
            .discount(UPDATED_DISCOUNT)
            .taxes(UPDATED_TAXES)
            .isTaxPercent(UPDATED_IS_TAX_PERCENT);

        restOrderDetailsMockMvc.perform(put("/api/order-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderDetails)))
            .andExpect(status().isOk());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrderDetails testOrderDetails = orderDetailsList.get(orderDetailsList.size() - 1);
        assertThat(testOrderDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrderDetails.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testOrderDetails.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOrderDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testOrderDetails.getTaxes()).isEqualTo(UPDATED_TAXES);
        assertThat(testOrderDetails.getIsTaxPercent()).isEqualTo(UPDATED_IS_TAX_PERCENT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderDetails() throws Exception {
        int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderDetailsMockMvc.perform(put("/api/order-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDetails)))
            .andExpect(status().isBadRequest());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        int databaseSizeBeforeDelete = orderDetailsRepository.findAll().size();

        // Delete the orderDetails
        restOrderDetailsMockMvc.perform(delete("/api/order-details/{id}", orderDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();
        assertThat(orderDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
