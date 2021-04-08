package com.sls.awesomeim.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A DocDetails.
 */
@Entity
@Table(name = "doc_details")
public class DocDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "qty", nullable = false)
    private Integer qty;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "discount", precision = 21, scale = 2)
    private BigDecimal discount;

    @Column(name = "taxes", precision = 21, scale = 2)
    private BigDecimal taxes;

    @Column(name = "is_tax_percent", precision = 21, scale = 2)
    private BigDecimal isTaxPercent;

    @OneToOne
    @JoinColumn(unique = true)
    private Products products;

    @ManyToOne
    @JsonIgnoreProperties(value = "docDetails", allowSetters = true)
    private Quotes quotes;

    @ManyToOne
    @JsonIgnoreProperties(value = "docDetails", allowSetters = true)
    private Invoices invoices;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public DocDetails description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQty() {
        return qty;
    }

    public DocDetails qty(Integer qty) {
        this.qty = qty;
        return this;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public DocDetails price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public DocDetails discount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public DocDetails taxes(BigDecimal taxes) {
        this.taxes = taxes;
        return this;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getIsTaxPercent() {
        return isTaxPercent;
    }

    public DocDetails isTaxPercent(BigDecimal isTaxPercent) {
        this.isTaxPercent = isTaxPercent;
        return this;
    }

    public void setIsTaxPercent(BigDecimal isTaxPercent) {
        this.isTaxPercent = isTaxPercent;
    }

    public Products getProducts() {
        return products;
    }

    public DocDetails products(Products products) {
        this.products = products;
        return this;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Quotes getQuotes() {
        return quotes;
    }

    public DocDetails quotes(Quotes quotes) {
        this.quotes = quotes;
        return this;
    }

    public void setQuotes(Quotes quotes) {
        this.quotes = quotes;
    }

    public Invoices getInvoices() {
        return invoices;
    }

    public DocDetails invoices(Invoices invoices) {
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
        if (!(o instanceof DocDetails)) {
            return false;
        }
        return id != null && id.equals(((DocDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocDetails{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", qty=" + getQty() +
            ", price=" + getPrice() +
            ", discount=" + getDiscount() +
            ", taxes=" + getTaxes() +
            ", isTaxPercent=" + getIsTaxPercent() +
            "}";
    }
}
