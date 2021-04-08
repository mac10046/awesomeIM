package com.sls.awesomeim.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Quotes.
 */
@Entity
@Table(name = "quotes")
public class Quotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gstin")
    private String gstin;

    @NotNull
    @Column(name = "quote_date", nullable = false)
    private Instant quoteDate;

    @Column(name = "terms")
    private String terms;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(unique = true)
    private Customers customers;

    @OneToOne
    @JoinColumn(unique = true)
    private BusinessDetails businessDetails;

    @OneToMany(mappedBy = "quotes")
    private Set<DocDetails> docDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGstin() {
        return gstin;
    }

    public Quotes gstin(String gstin) {
        this.gstin = gstin;
        return this;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public Instant getQuoteDate() {
        return quoteDate;
    }

    public Quotes quoteDate(Instant quoteDate) {
        this.quoteDate = quoteDate;
        return this;
    }

    public void setQuoteDate(Instant quoteDate) {
        this.quoteDate = quoteDate;
    }

    public String getTerms() {
        return terms;
    }

    public Quotes terms(String terms) {
        this.terms = terms;
        return this;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Quotes amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Customers getCustomers() {
        return customers;
    }

    public Quotes customers(Customers customers) {
        this.customers = customers;
        return this;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public Quotes businessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
        return this;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

    public Set<DocDetails> getDocDetails() {
        return docDetails;
    }

    public Quotes docDetails(Set<DocDetails> docDetails) {
        this.docDetails = docDetails;
        return this;
    }

    public Quotes addDocDetails(DocDetails docDetails) {
        this.docDetails.add(docDetails);
        docDetails.setQuotes(this);
        return this;
    }

    public Quotes removeDocDetails(DocDetails docDetails) {
        this.docDetails.remove(docDetails);
        docDetails.setQuotes(null);
        return this;
    }

    public void setDocDetails(Set<DocDetails> docDetails) {
        this.docDetails = docDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quotes)) {
            return false;
        }
        return id != null && id.equals(((Quotes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quotes{" +
            "id=" + getId() +
            ", gstin='" + getGstin() + "'" +
            ", quoteDate='" + getQuoteDate() + "'" +
            ", terms='" + getTerms() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
