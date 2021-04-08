package com.sls.awesomeim.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sls.awesomeim.domain.enumeration.BusinessType;

/**
 * A BusinessDetails.
 */
@Entity
@Table(name = "business_details")
public class BusinessDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "business_name", nullable = false)
    private String businessName;

    @NotNull
    @Column(name = "registered_address", nullable = false)
    private String registeredAddress;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "inception_date", nullable = false)
    private LocalDate inceptionDate;

    @NotNull
    @Column(name = "gstin", nullable = false)
    private String gstin;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @Column(name = "sub_category", nullable = false)
    private String subCategory;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @NotNull
    @Column(name = "managing_person_name", nullable = false)
    private String managingPersonName;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Lob
    @Column(name = "managing_person_image")
    private byte[] managingPersonImage;

    @Column(name = "managing_person_image_content_type")
    private String managingPersonImageContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "business_type", nullable = false)
    private BusinessType businessType;

    @Column(name = "upi")
    private String upi;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "branch_name")
    private String branchName;

    @OneToMany(mappedBy = "businessDetails")
    private Set<Products> products = new HashSet<>();

    @OneToMany(mappedBy = "businessDetails")
    private Set<Reviews> reviews = new HashSet<>();

    @OneToOne(mappedBy = "businessDetails")
    @JsonIgnore
    private Quotes quotes;

    @OneToOne(mappedBy = "businessDetails")
    @JsonIgnore
    private Invoices invoices;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public BusinessDetails businessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public BusinessDetails registeredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
        return this;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getDescription() {
        return description;
    }

    public BusinessDetails description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getInceptionDate() {
        return inceptionDate;
    }

    public BusinessDetails inceptionDate(LocalDate inceptionDate) {
        this.inceptionDate = inceptionDate;
        return this;
    }

    public void setInceptionDate(LocalDate inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    public String getGstin() {
        return gstin;
    }

    public BusinessDetails gstin(String gstin) {
        this.gstin = gstin;
        return this;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getCategory() {
        return category;
    }

    public BusinessDetails category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public BusinessDetails subCategory(String subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getEmail() {
        return email;
    }

    public BusinessDetails email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public BusinessDetails contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getManagingPersonName() {
        return managingPersonName;
    }

    public BusinessDetails managingPersonName(String managingPersonName) {
        this.managingPersonName = managingPersonName;
        return this;
    }

    public void setManagingPersonName(String managingPersonName) {
        this.managingPersonName = managingPersonName;
    }

    public byte[] getLogo() {
        return logo;
    }

    public BusinessDetails logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public BusinessDetails logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public byte[] getManagingPersonImage() {
        return managingPersonImage;
    }

    public BusinessDetails managingPersonImage(byte[] managingPersonImage) {
        this.managingPersonImage = managingPersonImage;
        return this;
    }

    public void setManagingPersonImage(byte[] managingPersonImage) {
        this.managingPersonImage = managingPersonImage;
    }

    public String getManagingPersonImageContentType() {
        return managingPersonImageContentType;
    }

    public BusinessDetails managingPersonImageContentType(String managingPersonImageContentType) {
        this.managingPersonImageContentType = managingPersonImageContentType;
        return this;
    }

    public void setManagingPersonImageContentType(String managingPersonImageContentType) {
        this.managingPersonImageContentType = managingPersonImageContentType;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public BusinessDetails businessType(BusinessType businessType) {
        this.businessType = businessType;
        return this;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public String getUpi() {
        return upi;
    }

    public BusinessDetails upi(String upi) {
        this.upi = upi;
        return this;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getBankName() {
        return bankName;
    }

    public BusinessDetails bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public BusinessDetails ifscCode(String ifscCode) {
        this.ifscCode = ifscCode;
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public BusinessDetails branchName(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public BusinessDetails products(Set<Products> products) {
        this.products = products;
        return this;
    }

    public BusinessDetails addProducts(Products products) {
        this.products.add(products);
        products.setBusinessDetails(this);
        return this;
    }

    public BusinessDetails removeProducts(Products products) {
        this.products.remove(products);
        products.setBusinessDetails(null);
        return this;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }

    public Set<Reviews> getReviews() {
        return reviews;
    }

    public BusinessDetails reviews(Set<Reviews> reviews) {
        this.reviews = reviews;
        return this;
    }

    public BusinessDetails addReviews(Reviews reviews) {
        this.reviews.add(reviews);
        reviews.setBusinessDetails(this);
        return this;
    }

    public BusinessDetails removeReviews(Reviews reviews) {
        this.reviews.remove(reviews);
        reviews.setBusinessDetails(null);
        return this;
    }

    public void setReviews(Set<Reviews> reviews) {
        this.reviews = reviews;
    }

    public Quotes getQuotes() {
        return quotes;
    }

    public BusinessDetails quotes(Quotes quotes) {
        this.quotes = quotes;
        return this;
    }

    public void setQuotes(Quotes quotes) {
        this.quotes = quotes;
    }

    public Invoices getInvoices() {
        return invoices;
    }

    public BusinessDetails invoices(Invoices invoices) {
        this.invoices = invoices;
        return this;
    }

    public void setInvoices(Invoices invoices) {
        this.invoices = invoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessDetails)) {
            return false;
        }
        return id != null && id.equals(((BusinessDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessDetails{" +
            "id=" + getId() +
            ", businessName='" + getBusinessName() + "'" +
            ", registeredAddress='" + getRegisteredAddress() + "'" +
            ", description='" + getDescription() + "'" +
            ", inceptionDate='" + getInceptionDate() + "'" +
            ", gstin='" + getGstin() + "'" +
            ", category='" + getCategory() + "'" +
            ", subCategory='" + getSubCategory() + "'" +
            ", email='" + getEmail() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", managingPersonName='" + getManagingPersonName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", managingPersonImage='" + getManagingPersonImage() + "'" +
            ", managingPersonImageContentType='" + getManagingPersonImageContentType() + "'" +
            ", businessType='" + getBusinessType() + "'" +
            ", upi='" + getUpi() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", branchName='" + getBranchName() + "'" +
            "}";
    }
}
