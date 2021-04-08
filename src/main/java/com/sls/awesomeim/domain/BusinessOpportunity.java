package com.sls.awesomeim.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A BusinessOpportunity.
 */
@Entity
@Table(name = "business_opportunity")
public class BusinessOpportunity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "investment_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal investmentAmount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "plan_description", nullable = false)
    private String planDescription;

    @OneToMany(mappedBy = "businessOpportunity")
    private Set<BusinessOffer> businessOffers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInvestmentAmount() {
        return investmentAmount;
    }

    public BusinessOpportunity investmentAmount(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
        return this;
    }

    public void setInvestmentAmount(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public BusinessOpportunity startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BusinessOpportunity endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public BusinessOpportunity planDescription(String planDescription) {
        this.planDescription = planDescription;
        return this;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public Set<BusinessOffer> getBusinessOffers() {
        return businessOffers;
    }

    public BusinessOpportunity businessOffers(Set<BusinessOffer> businessOffers) {
        this.businessOffers = businessOffers;
        return this;
    }

    public BusinessOpportunity addBusinessOffer(BusinessOffer businessOffer) {
        this.businessOffers.add(businessOffer);
        businessOffer.setBusinessOpportunity(this);
        return this;
    }

    public BusinessOpportunity removeBusinessOffer(BusinessOffer businessOffer) {
        this.businessOffers.remove(businessOffer);
        businessOffer.setBusinessOpportunity(null);
        return this;
    }

    public void setBusinessOffers(Set<BusinessOffer> businessOffers) {
        this.businessOffers = businessOffers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessOpportunity)) {
            return false;
        }
        return id != null && id.equals(((BusinessOpportunity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessOpportunity{" +
            "id=" + getId() +
            ", investmentAmount=" + getInvestmentAmount() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", planDescription='" + getPlanDescription() + "'" +
            "}";
    }
}
