package com.sls.awesomeim.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A Taxes.
 */
@Entity
@Table(name = "taxes")
public class Taxes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_percent", nullable = false)
    private Boolean isPercent;

    @NotNull
    @Column(name = "figure", nullable = false)
    private Double figure;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private Products products;

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

    public Taxes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsPercent() {
        return isPercent;
    }

    public Taxes isPercent(Boolean isPercent) {
        this.isPercent = isPercent;
        return this;
    }

    public void setIsPercent(Boolean isPercent) {
        this.isPercent = isPercent;
    }

    public Double getFigure() {
        return figure;
    }

    public Taxes figure(Double figure) {
        this.figure = figure;
        return this;
    }

    public void setFigure(Double figure) {
        this.figure = figure;
    }

    public String getDescription() {
        return description;
    }

    public Taxes description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Products getProducts() {
        return products;
    }

    public Taxes products(Products products) {
        this.products = products;
        return this;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Taxes)) {
            return false;
        }
        return id != null && id.equals(((Taxes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Taxes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isPercent='" + isIsPercent() + "'" +
            ", figure=" + getFigure() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
