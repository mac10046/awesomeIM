package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.Products;
import com.sls.awesomeim.repository.ProductsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductsResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final String DEFAULT_DIMENSION = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PHOTO_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_PHOTO_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_3_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_PHYSICAL_PRODUCT = false;
    private static final Boolean UPDATED_IS_PHYSICAL_PRODUCT = true;

    private static final Boolean DEFAULT_MAINTAIN_INVENTORY = false;
    private static final Boolean UPDATED_MAINTAIN_INVENTORY = true;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductsMockMvc;

    private Products products;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Products createEntity(EntityManager em) {
        Products products = new Products()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .weight(DEFAULT_WEIGHT)
            .dimension(DEFAULT_DIMENSION)
            .photo1(DEFAULT_PHOTO_1)
            .photo1ContentType(DEFAULT_PHOTO_1_CONTENT_TYPE)
            .photo2(DEFAULT_PHOTO_2)
            .photo2ContentType(DEFAULT_PHOTO_2_CONTENT_TYPE)
            .photo3(DEFAULT_PHOTO_3)
            .photo3ContentType(DEFAULT_PHOTO_3_CONTENT_TYPE)
            .isPhysicalProduct(DEFAULT_IS_PHYSICAL_PRODUCT)
            .maintainInventory(DEFAULT_MAINTAIN_INVENTORY);
        return products;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Products createUpdatedEntity(EntityManager em) {
        Products products = new Products()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .weight(UPDATED_WEIGHT)
            .dimension(UPDATED_DIMENSION)
            .photo1(UPDATED_PHOTO_1)
            .photo1ContentType(UPDATED_PHOTO_1_CONTENT_TYPE)
            .photo2(UPDATED_PHOTO_2)
            .photo2ContentType(UPDATED_PHOTO_2_CONTENT_TYPE)
            .photo3(UPDATED_PHOTO_3)
            .photo3ContentType(UPDATED_PHOTO_3_CONTENT_TYPE)
            .isPhysicalProduct(UPDATED_IS_PHYSICAL_PRODUCT)
            .maintainInventory(UPDATED_MAINTAIN_INVENTORY);
        return products;
    }

    @BeforeEach
    public void initTest() {
        products = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducts() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();
        // Create the Products
        restProductsMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(products)))
            .andExpect(status().isCreated());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate + 1);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProducts.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProducts.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProducts.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProducts.getDimension()).isEqualTo(DEFAULT_DIMENSION);
        assertThat(testProducts.getPhoto1()).isEqualTo(DEFAULT_PHOTO_1);
        assertThat(testProducts.getPhoto1ContentType()).isEqualTo(DEFAULT_PHOTO_1_CONTENT_TYPE);
        assertThat(testProducts.getPhoto2()).isEqualTo(DEFAULT_PHOTO_2);
        assertThat(testProducts.getPhoto2ContentType()).isEqualTo(DEFAULT_PHOTO_2_CONTENT_TYPE);
        assertThat(testProducts.getPhoto3()).isEqualTo(DEFAULT_PHOTO_3);
        assertThat(testProducts.getPhoto3ContentType()).isEqualTo(DEFAULT_PHOTO_3_CONTENT_TYPE);
        assertThat(testProducts.isIsPhysicalProduct()).isEqualTo(DEFAULT_IS_PHYSICAL_PRODUCT);
        assertThat(testProducts.isMaintainInventory()).isEqualTo(DEFAULT_MAINTAIN_INVENTORY);
    }

    @Test
    @Transactional
    public void createProductsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();

        // Create the Products with an existing ID
        products.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(products)))
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setName(null);

        // Create the Products, which fails.


        restProductsMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(products)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList
        restProductsMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].dimension").value(hasItem(DEFAULT_DIMENSION)))
            .andExpect(jsonPath("$.[*].photo1ContentType").value(hasItem(DEFAULT_PHOTO_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo1").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_1))))
            .andExpect(jsonPath("$.[*].photo2ContentType").value(hasItem(DEFAULT_PHOTO_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo2").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_2))))
            .andExpect(jsonPath("$.[*].photo3ContentType").value(hasItem(DEFAULT_PHOTO_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo3").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO_3))))
            .andExpect(jsonPath("$.[*].isPhysicalProduct").value(hasItem(DEFAULT_IS_PHYSICAL_PRODUCT.booleanValue())))
            .andExpect(jsonPath("$.[*].maintainInventory").value(hasItem(DEFAULT_MAINTAIN_INVENTORY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get the products
        restProductsMockMvc.perform(get("/api/products/{id}", products.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(products.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.dimension").value(DEFAULT_DIMENSION))
            .andExpect(jsonPath("$.photo1ContentType").value(DEFAULT_PHOTO_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo1").value(Base64Utils.encodeToString(DEFAULT_PHOTO_1)))
            .andExpect(jsonPath("$.photo2ContentType").value(DEFAULT_PHOTO_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo2").value(Base64Utils.encodeToString(DEFAULT_PHOTO_2)))
            .andExpect(jsonPath("$.photo3ContentType").value(DEFAULT_PHOTO_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo3").value(Base64Utils.encodeToString(DEFAULT_PHOTO_3)))
            .andExpect(jsonPath("$.isPhysicalProduct").value(DEFAULT_IS_PHYSICAL_PRODUCT.booleanValue()))
            .andExpect(jsonPath("$.maintainInventory").value(DEFAULT_MAINTAIN_INVENTORY.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProducts() throws Exception {
        // Get the products
        restProductsMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Update the products
        Products updatedProducts = productsRepository.findById(products.getId()).get();
        // Disconnect from session so that the updates on updatedProducts are not directly saved in db
        em.detach(updatedProducts);
        updatedProducts
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .weight(UPDATED_WEIGHT)
            .dimension(UPDATED_DIMENSION)
            .photo1(UPDATED_PHOTO_1)
            .photo1ContentType(UPDATED_PHOTO_1_CONTENT_TYPE)
            .photo2(UPDATED_PHOTO_2)
            .photo2ContentType(UPDATED_PHOTO_2_CONTENT_TYPE)
            .photo3(UPDATED_PHOTO_3)
            .photo3ContentType(UPDATED_PHOTO_3_CONTENT_TYPE)
            .isPhysicalProduct(UPDATED_IS_PHYSICAL_PRODUCT)
            .maintainInventory(UPDATED_MAINTAIN_INVENTORY);

        restProductsMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProducts)))
            .andExpect(status().isOk());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducts.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProducts.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProducts.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProducts.getDimension()).isEqualTo(UPDATED_DIMENSION);
        assertThat(testProducts.getPhoto1()).isEqualTo(UPDATED_PHOTO_1);
        assertThat(testProducts.getPhoto1ContentType()).isEqualTo(UPDATED_PHOTO_1_CONTENT_TYPE);
        assertThat(testProducts.getPhoto2()).isEqualTo(UPDATED_PHOTO_2);
        assertThat(testProducts.getPhoto2ContentType()).isEqualTo(UPDATED_PHOTO_2_CONTENT_TYPE);
        assertThat(testProducts.getPhoto3()).isEqualTo(UPDATED_PHOTO_3);
        assertThat(testProducts.getPhoto3ContentType()).isEqualTo(UPDATED_PHOTO_3_CONTENT_TYPE);
        assertThat(testProducts.isIsPhysicalProduct()).isEqualTo(UPDATED_IS_PHYSICAL_PRODUCT);
        assertThat(testProducts.isMaintainInventory()).isEqualTo(UPDATED_MAINTAIN_INVENTORY);
    }

    @Test
    @Transactional
    public void updateNonExistingProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(products)))
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeDelete = productsRepository.findAll().size();

        // Delete the products
        restProductsMockMvc.perform(delete("/api/products/{id}", products.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
