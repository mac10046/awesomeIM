package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.Shippers;
import com.sls.awesomeim.repository.ShippersRepository;

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
 * Integration tests for the {@link ShippersResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ShippersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private ShippersRepository shippersRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShippersMockMvc;

    private Shippers shippers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shippers createEntity(EntityManager em) {
        Shippers shippers = new Shippers()
            .name(DEFAULT_NAME)
            .contactNumber(DEFAULT_CONTACT_NUMBER);
        return shippers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shippers createUpdatedEntity(EntityManager em) {
        Shippers shippers = new Shippers()
            .name(UPDATED_NAME)
            .contactNumber(UPDATED_CONTACT_NUMBER);
        return shippers;
    }

    @BeforeEach
    public void initTest() {
        shippers = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippers() throws Exception {
        int databaseSizeBeforeCreate = shippersRepository.findAll().size();
        // Create the Shippers
        restShippersMockMvc.perform(post("/api/shippers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippers)))
            .andExpect(status().isCreated());

        // Validate the Shippers in the database
        List<Shippers> shippersList = shippersRepository.findAll();
        assertThat(shippersList).hasSize(databaseSizeBeforeCreate + 1);
        Shippers testShippers = shippersList.get(shippersList.size() - 1);
        assertThat(testShippers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShippers.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void createShippersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippersRepository.findAll().size();

        // Create the Shippers with an existing ID
        shippers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippersMockMvc.perform(post("/api/shippers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippers)))
            .andExpect(status().isBadRequest());

        // Validate the Shippers in the database
        List<Shippers> shippersList = shippersRepository.findAll();
        assertThat(shippersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippersRepository.findAll().size();
        // set the field null
        shippers.setName(null);

        // Create the Shippers, which fails.


        restShippersMockMvc.perform(post("/api/shippers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippers)))
            .andExpect(status().isBadRequest());

        List<Shippers> shippersList = shippersRepository.findAll();
        assertThat(shippersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShippers() throws Exception {
        // Initialize the database
        shippersRepository.saveAndFlush(shippers);

        // Get all the shippersList
        restShippersMockMvc.perform(get("/api/shippers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getShippers() throws Exception {
        // Initialize the database
        shippersRepository.saveAndFlush(shippers);

        // Get the shippers
        restShippersMockMvc.perform(get("/api/shippers/{id}", shippers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shippers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER));
    }
    @Test
    @Transactional
    public void getNonExistingShippers() throws Exception {
        // Get the shippers
        restShippersMockMvc.perform(get("/api/shippers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippers() throws Exception {
        // Initialize the database
        shippersRepository.saveAndFlush(shippers);

        int databaseSizeBeforeUpdate = shippersRepository.findAll().size();

        // Update the shippers
        Shippers updatedShippers = shippersRepository.findById(shippers.getId()).get();
        // Disconnect from session so that the updates on updatedShippers are not directly saved in db
        em.detach(updatedShippers);
        updatedShippers
            .name(UPDATED_NAME)
            .contactNumber(UPDATED_CONTACT_NUMBER);

        restShippersMockMvc.perform(put("/api/shippers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedShippers)))
            .andExpect(status().isOk());

        // Validate the Shippers in the database
        List<Shippers> shippersList = shippersRepository.findAll();
        assertThat(shippersList).hasSize(databaseSizeBeforeUpdate);
        Shippers testShippers = shippersList.get(shippersList.size() - 1);
        assertThat(testShippers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShippers.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingShippers() throws Exception {
        int databaseSizeBeforeUpdate = shippersRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShippersMockMvc.perform(put("/api/shippers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shippers)))
            .andExpect(status().isBadRequest());

        // Validate the Shippers in the database
        List<Shippers> shippersList = shippersRepository.findAll();
        assertThat(shippersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShippers() throws Exception {
        // Initialize the database
        shippersRepository.saveAndFlush(shippers);

        int databaseSizeBeforeDelete = shippersRepository.findAll().size();

        // Delete the shippers
        restShippersMockMvc.perform(delete("/api/shippers/{id}", shippers.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shippers> shippersList = shippersRepository.findAll();
        assertThat(shippersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
