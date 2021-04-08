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
 * A Reviews.
 */
@Entity
@Table(name = "reviews")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @Column(name = "review_date", nullable = false)
    private Instant reviewDate;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "designation")
    private String designation;

    @ManyToOne
    @JsonIgnoreProperties(value = "reviews", allowSetters = true)
    private BusinessDetails businessDetails;

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

    public Reviews name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public Reviews message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getReviewDate() {
        return reviewDate;
    }

    public Reviews reviewDate(Instant reviewDate) {
        this.reviewDate = reviewDate;
        return this;
    }

    public void setReviewDate(Instant reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Integer getRating() {
        return rating;
    }

    public Reviews rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDesignation() {
        return designation;
    }

    public Reviews designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public Reviews businessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
        return this;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reviews)) {
            return false;
        }
        return id != null && id.equals(((Reviews) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reviews{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", message='" + getMessage() + "'" +
            ", reviewDate='" + getReviewDate() + "'" +
            ", rating=" + getRating() +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
