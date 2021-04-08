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
 * A Invoices.
 */
@Entity
@Table(name = "invoices")
public class Invoices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gstin")
    private String gstin;

    @NotNull
    @Column(name = "invoice_date", nullable = false)
    private Instant invoiceDate;

    @Column(name = "terms")
    private String terms;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(unique = true)
    private Customers customers;

    @OneToOne
    @JoinColumn(unique = true)
    private BusinessDetails businessDetails;

    @OneToMany(mappedBy = "invoices")
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

    public Invoices gstin(String gstin) {
        this.gstin = gstin;
        return this;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public Instant getInvoiceDate() {
        return invoiceDate;
    }

    public Invoices invoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getTerms() {
        return terms;
    }

    public Invoices terms(String terms) {
        this.terms = terms;
        return this;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public Boolean isIsPaid() {
        return isPaid;
    }

    public Invoices isPaid(Boolean isPaid) {
        this.isPaid = isPaid;
        return this;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Invoices amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Customers getCustomers() {
        return customers;
    }

    public Invoices customers(Customers customers) {
        this.customers = customers;
        return this;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public Invoices businessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
        return this;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

    public Set<DocDetails> getDocDetails() {
        return docDetails;
    }

    public Invoices docDetails(Set<DocDetails> docDetails) {
        this.docDetails = docDetails;
        return this;
    }

    public Invoices addDocDetails(DocDetails docDetails) {
        this.docDetails.add(docDetails);
        docDetails.setInvoices(this);
        return this;
    }

    public Invoices removeDocDetails(DocDetails docDetails) {
        this.docDetails.remove(docDetails);
        docDetails.setInvoices(null);
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
        if (!(o instanceof Invoices)) {
            return false;
        }
        return id != null && id.equals(((Invoices) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoices{" +
            "id=" + getId() +
            ", gstin='" + getGstin() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", terms='" + getTerms() + "'" +
            ", isPaid='" + isIsPaid() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
