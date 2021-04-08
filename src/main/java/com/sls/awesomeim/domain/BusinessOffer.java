package com.sls.awesomeim.domain;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A BusinessOffer.
 */
@Entity
@Table(name = "business_offer")
public class BusinessOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    private Instant time;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JsonIgnoreProperties(value = "businessOffers", allowSetters = true)
    private BusinessOpportunity businessOpportunity;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public BusinessOffer time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public BusinessOffer message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public BusinessOffer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public BusinessOffer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BusinessOpportunity getBusinessOpportunity() {
        return businessOpportunity;
    }

    public BusinessOffer businessOpportunity(BusinessOpportunity businessOpportunity) {
        this.businessOpportunity = businessOpportunity;
        return this;
    }

    public void setBusinessOpportunity(BusinessOpportunity businessOpportunity) {
        this.businessOpportunity = businessOpportunity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessOffer)) {
            return false;
        }
        return id != null && id.equals(((BusinessOffer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessOffer{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", message='" + getMessage() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
