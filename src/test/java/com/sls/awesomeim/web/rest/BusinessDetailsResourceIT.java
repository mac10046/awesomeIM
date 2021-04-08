package com.sls.awesomeim.web.rest;

import com.sls.awesomeim.AwesomeimApp;
import com.sls.awesomeim.domain.BusinessDetails;
import com.sls.awesomeim.repository.BusinessDetailsRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sls.awesomeim.domain.enumeration.BusinessType;
/**
 * Integration tests for the {@link BusinessDetailsResource} REST controller.
 */
@SpringBootTest(classes = AwesomeimApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BusinessDetailsResourceIT {

    private static final String DEFAULT_BUSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTERED_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_REGISTERED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INCEPTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INCEPTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GSTIN = "AAAAAAAAAA";
    private static final String UPDATED_GSTIN = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_SUB_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGING_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MANAGING_PERSON_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MANAGING_PERSON_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MANAGING_PERSON_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MANAGING_PERSON_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MANAGING_PERSON_IMAGE_CONTENT_TYPE = "image/png";

    private static final BusinessType DEFAULT_BUSINESS_TYPE = BusinessType.Service;
    private static final BusinessType UPDATED_BUSINESS_TYPE = BusinessType.Manufacturing;

    private static final String DEFAULT_UPI = "AAAAAAAAAA";
    private static final String UPDATED_UPI = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    @Autowired
    private BusinessDetailsRepository businessDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessDetailsMockMvc;

    private BusinessDetails businessDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessDetails createEntity(EntityManager em) {
        BusinessDetails businessDetails = new BusinessDetails()
            .businessName(DEFAULT_BUSINESS_NAME)
            .registeredAddress(DEFAULT_REGISTERED_ADDRESS)
            .description(DEFAULT_DESCRIPTION)
            .inceptionDate(DEFAULT_INCEPTION_DATE)
            .gstin(DEFAULT_GSTIN)
            .category(DEFAULT_CATEGORY)
            .subCategory(DEFAULT_SUB_CATEGORY)
            .email(DEFAULT_EMAIL)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .managingPersonName(DEFAULT_MANAGING_PERSON_NAME)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .managingPersonImage(DEFAULT_MANAGING_PERSON_IMAGE)
            .managingPersonImageContentType(DEFAULT_MANAGING_PERSON_IMAGE_CONTENT_TYPE)
            .businessType(DEFAULT_BUSINESS_TYPE)
            .upi(DEFAULT_UPI)
            .bankName(DEFAULT_BANK_NAME)
            .ifscCode(DEFAULT_IFSC_CODE)
            .branchName(DEFAULT_BRANCH_NAME);
        return businessDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessDetails createUpdatedEntity(EntityManager em) {
        BusinessDetails businessDetails = new BusinessDetails()
            .businessName(UPDATED_BUSINESS_NAME)
            .registeredAddress(UPDATED_REGISTERED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .inceptionDate(UPDATED_INCEPTION_DATE)
            .gstin(UPDATED_GSTIN)
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .email(UPDATED_EMAIL)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .managingPersonName(UPDATED_MANAGING_PERSON_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .managingPersonImage(UPDATED_MANAGING_PERSON_IMAGE)
            .managingPersonImageContentType(UPDATED_MANAGING_PERSON_IMAGE_CONTENT_TYPE)
            .businessType(UPDATED_BUSINESS_TYPE)
            .upi(UPDATED_UPI)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .branchName(UPDATED_BRANCH_NAME);
        return businessDetails;
    }

    @BeforeEach
    public void initTest() {
        businessDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessDetails() throws Exception {
        int databaseSizeBeforeCreate = businessDetailsRepository.findAll().size();
        // Create the BusinessDetails
        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isCreated());

        // Validate the BusinessDetails in the database
        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessDetails testBusinessDetails = businessDetailsList.get(businessDetailsList.size() - 1);
        assertThat(testBusinessDetails.getBusinessName()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testBusinessDetails.getRegisteredAddress()).isEqualTo(DEFAULT_REGISTERED_ADDRESS);
        assertThat(testBusinessDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBusinessDetails.getInceptionDate()).isEqualTo(DEFAULT_INCEPTION_DATE);
        assertThat(testBusinessDetails.getGstin()).isEqualTo(DEFAULT_GSTIN);
        assertThat(testBusinessDetails.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testBusinessDetails.getSubCategory()).isEqualTo(DEFAULT_SUB_CATEGORY);
        assertThat(testBusinessDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBusinessDetails.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testBusinessDetails.getManagingPersonName()).isEqualTo(DEFAULT_MANAGING_PERSON_NAME);
        assertThat(testBusinessDetails.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testBusinessDetails.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testBusinessDetails.getManagingPersonImage()).isEqualTo(DEFAULT_MANAGING_PERSON_IMAGE);
        assertThat(testBusinessDetails.getManagingPersonImageContentType()).isEqualTo(DEFAULT_MANAGING_PERSON_IMAGE_CONTENT_TYPE);
        assertThat(testBusinessDetails.getBusinessType()).isEqualTo(DEFAULT_BUSINESS_TYPE);
        assertThat(testBusinessDetails.getUpi()).isEqualTo(DEFAULT_UPI);
        assertThat(testBusinessDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBusinessDetails.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testBusinessDetails.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void createBusinessDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessDetailsRepository.findAll().size();

        // Create the BusinessDetails with an existing ID
        businessDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessDetails in the database
        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBusinessNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setBusinessName(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegisteredAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setRegisteredAddress(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInceptionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setInceptionDate(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGstinIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setGstin(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setCategory(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setSubCategory(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setEmail(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setContactNumber(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkManagingPersonNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setManagingPersonName(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusinessTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessDetailsRepository.findAll().size();
        // set the field null
        businessDetails.setBusinessType(null);

        // Create the BusinessDetails, which fails.


        restBusinessDetailsMockMvc.perform(post("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBusinessDetails() throws Exception {
        // Initialize the database
        businessDetailsRepository.saveAndFlush(businessDetails);

        // Get all the businessDetailsList
        restBusinessDetailsMockMvc.perform(get("/api/business-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME)))
            .andExpect(jsonPath("$.[*].registeredAddress").value(hasItem(DEFAULT_REGISTERED_ADDRESS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].inceptionDate").value(hasItem(DEFAULT_INCEPTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].gstin").value(hasItem(DEFAULT_GSTIN)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].managingPersonName").value(hasItem(DEFAULT_MANAGING_PERSON_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].managingPersonImageContentType").value(hasItem(DEFAULT_MANAGING_PERSON_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].managingPersonImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_MANAGING_PERSON_IMAGE))))
            .andExpect(jsonPath("$.[*].businessType").value(hasItem(DEFAULT_BUSINESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].upi").value(hasItem(DEFAULT_UPI)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)));
    }
    
    @Test
    @Transactional
    public void getBusinessDetails() throws Exception {
        // Initialize the database
        businessDetailsRepository.saveAndFlush(businessDetails);

        // Get the businessDetails
        restBusinessDetailsMockMvc.perform(get("/api/business-details/{id}", businessDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessDetails.getId().intValue()))
            .andExpect(jsonPath("$.businessName").value(DEFAULT_BUSINESS_NAME))
            .andExpect(jsonPath("$.registeredAddress").value(DEFAULT_REGISTERED_ADDRESS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.inceptionDate").value(DEFAULT_INCEPTION_DATE.toString()))
            .andExpect(jsonPath("$.gstin").value(DEFAULT_GSTIN))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.subCategory").value(DEFAULT_SUB_CATEGORY))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER))
            .andExpect(jsonPath("$.managingPersonName").value(DEFAULT_MANAGING_PERSON_NAME))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.managingPersonImageContentType").value(DEFAULT_MANAGING_PERSON_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.managingPersonImage").value(Base64Utils.encodeToString(DEFAULT_MANAGING_PERSON_IMAGE)))
            .andExpect(jsonPath("$.businessType").value(DEFAULT_BUSINESS_TYPE.toString()))
            .andExpect(jsonPath("$.upi").value(DEFAULT_UPI))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingBusinessDetails() throws Exception {
        // Get the businessDetails
        restBusinessDetailsMockMvc.perform(get("/api/business-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessDetails() throws Exception {
        // Initialize the database
        businessDetailsRepository.saveAndFlush(businessDetails);

        int databaseSizeBeforeUpdate = businessDetailsRepository.findAll().size();

        // Update the businessDetails
        BusinessDetails updatedBusinessDetails = businessDetailsRepository.findById(businessDetails.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessDetails are not directly saved in db
        em.detach(updatedBusinessDetails);
        updatedBusinessDetails
            .businessName(UPDATED_BUSINESS_NAME)
            .registeredAddress(UPDATED_REGISTERED_ADDRESS)
            .description(UPDATED_DESCRIPTION)
            .inceptionDate(UPDATED_INCEPTION_DATE)
            .gstin(UPDATED_GSTIN)
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .email(UPDATED_EMAIL)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .managingPersonName(UPDATED_MANAGING_PERSON_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .managingPersonImage(UPDATED_MANAGING_PERSON_IMAGE)
            .managingPersonImageContentType(UPDATED_MANAGING_PERSON_IMAGE_CONTENT_TYPE)
            .businessType(UPDATED_BUSINESS_TYPE)
            .upi(UPDATED_UPI)
            .bankName(UPDATED_BANK_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .branchName(UPDATED_BRANCH_NAME);

        restBusinessDetailsMockMvc.perform(put("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusinessDetails)))
            .andExpect(status().isOk());

        // Validate the BusinessDetails in the database
        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeUpdate);
        BusinessDetails testBusinessDetails = businessDetailsList.get(businessDetailsList.size() - 1);
        assertThat(testBusinessDetails.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testBusinessDetails.getRegisteredAddress()).isEqualTo(UPDATED_REGISTERED_ADDRESS);
        assertThat(testBusinessDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBusinessDetails.getInceptionDate()).isEqualTo(UPDATED_INCEPTION_DATE);
        assertThat(testBusinessDetails.getGstin()).isEqualTo(UPDATED_GSTIN);
        assertThat(testBusinessDetails.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testBusinessDetails.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testBusinessDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBusinessDetails.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testBusinessDetails.getManagingPersonName()).isEqualTo(UPDATED_MANAGING_PERSON_NAME);
        assertThat(testBusinessDetails.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testBusinessDetails.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testBusinessDetails.getManagingPersonImage()).isEqualTo(UPDATED_MANAGING_PERSON_IMAGE);
        assertThat(testBusinessDetails.getManagingPersonImageContentType()).isEqualTo(UPDATED_MANAGING_PERSON_IMAGE_CONTENT_TYPE);
        assertThat(testBusinessDetails.getBusinessType()).isEqualTo(UPDATED_BUSINESS_TYPE);
        assertThat(testBusinessDetails.getUpi()).isEqualTo(UPDATED_UPI);
        assertThat(testBusinessDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBusinessDetails.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testBusinessDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessDetails() throws Exception {
        int databaseSizeBeforeUpdate = businessDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessDetailsMockMvc.perform(put("/api/business-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessDetails)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessDetails in the database
        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessDetails() throws Exception {
        // Initialize the database
        businessDetailsRepository.saveAndFlush(businessDetails);

        int databaseSizeBeforeDelete = businessDetailsRepository.findAll().size();

        // Delete the businessDetails
        restBusinessDetailsMockMvc.perform(delete("/api/business-details/{id}", businessDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessDetails> businessDetailsList = businessDetailsRepository.findAll();
        assertThat(businessDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
