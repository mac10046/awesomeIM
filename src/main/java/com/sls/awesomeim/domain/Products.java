package com.sls.awesomeim.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Products.
 */
@Entity
@Table(name = "products")
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "dimension")
    private String dimension;

    @Lob
    @Column(name = "photo_1")
    private byte[] photo1;

    @Column(name = "photo_1_content_type")
    private String photo1ContentType;

    @Lob
    @Column(name = "photo_2")
    private byte[] photo2;

    @Column(name = "photo_2_content_type")
    private String photo2ContentType;

    @Lob
    @Column(name = "photo_3")
    private byte[] photo3;

    @Column(name = "photo_3_content_type")
    private String photo3ContentType;

    @Column(name = "is_physical_product")
    private Boolean isPhysicalProduct;

    @Column(name = "maintain_inventory")
    private Boolean maintainInventory;

    @OneToOne(mappedBy = "products")
    @JsonIgnore
    private DocDetails docDetails;

    @OneToOne(mappedBy = "products")
    @JsonIgnore
    private Taxes taxes;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private BusinessDetails businessDetails;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Cart cart;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Shipping shipping;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Products name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Products description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Products price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public Products weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDimension() {
        return dimension;
    }

    public Products dimension(String dimension) {
        this.dimension = dimension;
        return this;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public byte[] getPhoto1() {
        return photo1;
    }

    public Products photo1(byte[] photo1) {
        this.photo1 = photo1;
        return this;
    }

    public void setPhoto1(byte[] photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto1ContentType() {
        return photo1ContentType;
    }

    public Products photo1ContentType(String photo1ContentType) {
        this.photo1ContentType = photo1ContentType;
        return this;
    }

    public void setPhoto1ContentType(String photo1ContentType) {
        this.photo1ContentType = photo1ContentType;
    }

    public byte[] getPhoto2() {
        return photo2;
    }

    public Products photo2(byte[] photo2) {
        this.photo2 = photo2;
        return this;
    }

    public void setPhoto2(byte[] photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto2ContentType() {
        return photo2ContentType;
    }

    public Products photo2ContentType(String photo2ContentType) {
        this.photo2ContentType = photo2ContentType;
        return this;
    }

    public void setPhoto2ContentType(String photo2ContentType) {
        this.photo2ContentType = photo2ContentType;
    }

    public byte[] getPhoto3() {
        return photo3;
    }

    public Products photo3(byte[] photo3) {
        this.photo3 = photo3;
        return this;
    }

    public void setPhoto3(byte[] photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto3ContentType() {
        return photo3ContentType;
    }

    public Products photo3ContentType(String photo3ContentType) {
        this.photo3ContentType = photo3ContentType;
        return this;
    }

    public void setPhoto3ContentType(String photo3ContentType) {
        this.photo3ContentType = photo3ContentType;
    }

    public Boolean isIsPhysicalProduct() {
        return isPhysicalProduct;
    }

    public Products isPhysicalProduct(Boolean isPhysicalProduct) {
        this.isPhysicalProduct = isPhysicalProduct;
        return this;
    }

    public void setIsPhysicalProduct(Boolean isPhysicalProduct) {
        this.isPhysicalProduct = isPhysicalProduct;
    }

    public Boolean isMaintainInventory() {
        return maintainInventory;
    }

    public Products maintainInventory(Boolean maintainInventory) {
        this.maintainInventory = maintainInventory;
        return this;
    }

    public void setMaintainInventory(Boolean maintainInventory) {
        this.maintainInventory = maintainInventory;
    }

    public DocDetails getDocDetails() {
        return docDetails;
    }

    public Products docDetails(DocDetails docDetails) {
        this.docDetails = docDetails;
        return this;
    }

    public void setDocDetails(DocDetails docDetails) {
        this.docDetails = docDetails;
    }

    public Taxes getTaxes() {
        return taxes;
    }

    public Products taxes(Taxes taxes) {
        this.taxes = taxes;
        return this;
    }

    public void setTaxes(Taxes taxes) {
        this.taxes = taxes;
    }

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public Products businessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
        return this;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

    public Cart getCart() {
        return cart;
    }

    public Products cart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public Products shipping(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Products)) {
            return false;
        }
        return id != null && id.equals(((Products) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Products{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", weight=" + getWeight() +
            ", dimension='" + getDimension() + "'" +
            ", photo1='" + getPhoto1() + "'" +
            ", photo1ContentType='" + getPhoto1ContentType() + "'" +
            ", photo2='" + getPhoto2() + "'" +
            ", photo2ContentType='" + getPhoto2ContentType() + "'" +
            ", photo3='" + getPhoto3() + "'" +
            ", photo3ContentType='" + getPhoto3ContentType() + "'" +
            ", isPhysicalProduct='" + isIsPhysicalProduct() + "'" +
            ", maintainInventory='" + isMaintainInventory() + "'" +
            "}";
    }
}
