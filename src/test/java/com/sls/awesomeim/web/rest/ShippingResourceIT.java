package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.Shipping;
import com.sls.awesomeim.repository.ShippingRepository;

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
 * Integration tests for the {@link ShippingResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ShippingResourceIT {

    private static final LocalDate DEFAULT_SHIP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHIP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TRACKING_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_ID = "BBBBBBBBBB";

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShippingMockMvc;

    private Shipping shipping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipping createEntity(EntityManager em) {
        Shipping shipping = new Shipping()
            .shipDate(DEFAULT_SHIP_DATE)
            .trackingId(DEFAULT_TRACKING_ID);
        return shipping;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shipping createUpdatedEntity(EntityManager em) {
        Shipping shipping = new Shipping()
            .shipDate(UPDATED_SHIP_DATE)
            .trackingId(UPDATED_TRACKING_ID);
        return shipping;
    }

    @BeforeEach
    public void initTest() {
        shipping = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipping() throws Exception {
        int databaseSizeBeforeCreate = shippingRepository.findAll().size();
        // Create the Shipping
        restShippingMockMvc.perform(post("/api/shippings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipping)))
            .andExpect(status().isCreated());

        // Validate the Shipping in the database
        List<Shipping> shippingList = shippingRepository.findAll();
        assertThat(shippingList).hasSize(databaseSizeBeforeCreate + 1);
        Shipping testShipping = shippingList.get(shippingList.size() - 1);
        assertThat(testShipping.getShipDate()).isEqualTo(DEFAULT_SHIP_DATE);
        assertThat(testShipping.getTrackingId()).isEqualTo(DEFAULT_TRACKING_ID);
    }

    @Test
    @Transactional
    public void createShippingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingRepository.findAll().size();

        // Create the Shipping with an existing ID
        shipping.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingMockMvc.perform(post("/api/shippings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipping)))
            .andExpect(status().isBadRequest());

        // Validate the Shipping in the database
        List<Shipping> shippingList = shippingRepository.findAll();
        assertThat(shippingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkShipDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingRepository.findAll().size();
        // set the field null
        shipping.setShipDate(null);

        // Create the Shipping, which fails.


        restShippingMockMvc.perform(post("/api/shippings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipping)))
            .andExpect(status().isBadRequest());

        List<Shipping> shippingList = shippingRepository.findAll();
        assertThat(shippingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrackingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingRepository.findAll().size();
        // set the field null
        shipping.setTrackingId(null);

        // Create the Shipping, which fails.


        restShippingMockMvc.perform(post("/api/shippings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipping)))
            .andExpect(status().isBadRequest());

        List<Shipping> shippingList = shippingRepository.findAll();
        assertThat(shippingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShippings() throws Exception {
        // Initialize the database
        shippingRepository.saveAndFlush(shipping);

        // Get all the shippingList
        restShippingMockMvc.perform(get("/api/shippings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipping.getId().intValue())))
            .andExpect(jsonPath("$.[*].shipDate").value(hasItem(DEFAULT_SHIP_DATE.toString())))
            .andExpect(jsonPath("$.[*].trackingId").value(hasItem(DEFAULT_TRACKING_ID)));
    }
    
    @Test
    @Transactional
    public void getShipping() throws Exception {
        // Initialize the database
        shippingRepository.saveAndFlush(shipping);

        // Get the shipping
        restShippingMockMvc.perform(get("/api/shippings/{id}", shipping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipping.getId().intValue()))
            .andExpect(jsonPath("$.shipDate").value(DEFAULT_SHIP_DATE.toString()))
            .andExpect(jsonPath("$.trackingId").value(DEFAULT_TRACKING_ID));
    }
    @Test
    @Transactional
    public void getNonExistingShipping() throws Exception {
        // Get the shipping
        restShippingMockMvc.perform(get("/api/shippings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipping() throws Exception {
        // Initialize the database
        shippingRepository.saveAndFlush(shipping);

        int databaseSizeBeforeUpdate = shippingRepository.findAll().size();

        // Update the shipping
        Shipping updatedShipping = shippingRepository.findById(shipping.getId()).get();
        // Disconnect from session so that the updates on updatedShipping are not directly saved in db
        em.detach(updatedShipping);
        updatedShipping
            .shipDate(UPDATED_SHIP_DATE)
            .trackingId(UPDATED_TRACKING_ID);

        restShippingMockMvc.perform(put("/api/shippings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedShipping)))
            .andExpect(status().isOk());

        // Validate the Shipping in the database
        List<Shipping> shippingList = shippingRepository.findAll();
        assertThat(shippingList).hasSize(databaseSizeBeforeUpdate);
        Shipping testShipping = shippingList.get(shippingList.size() - 1);
        assertThat(testShipping.getShipDate()).isEqualTo(UPDATED_SHIP_DATE);
        assertThat(testShipping.getTrackingId()).isEqualTo(UPDATED_TRACKING_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingShipping() throws Exception {
        int databaseSizeBeforeUpdate = shippingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShippingMockMvc.perform(put("/api/shippings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shipping)))
            .andExpect(status().isBadRequest());

        // Validate the Shipping in the database
        List<Shipping> shippingList = shippingRepository.findAll();
        assertThat(shippingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShipping() throws Exception {
        // Initialize the database
        shippingRepository.saveAndFlush(shipping);

        int databaseSizeBeforeDelete = shippingRepository.findAll().size();

        // Delete the shipping
        restShippingMockMvc.perform(delete("/api/shippings/{id}", shipping.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shipping> shippingList = shippingRepository.findAll();
        assertThat(shippingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
