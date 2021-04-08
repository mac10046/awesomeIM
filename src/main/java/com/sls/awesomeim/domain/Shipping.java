package com.sls.awesomeim.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Shipping.
 */
@Entity
@Table(name = "shipping")
public class Shipping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ship_date", nullable = false)
    private LocalDate shipDate;

    @NotNull
    @Column(name = "tracking_id", nullable = false)
    private String trackingId;

    @OneToOne
    @JoinColumn(unique = true)
    private Shippers shippers;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "shipping")
    private Set<Products> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "shippings", allowSetters = true)
    private Orders orders;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public Shipping shipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
        return this;
    }

    public void setShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public Shipping trackingId(String trackingId) {
        this.trackingId = trackingId;
        return this;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Shippers getShippers() {
        return shippers;
    }

    public Shipping shippers(Shippers shippers) {
        this.shippers = shippers;
        return this;
    }

    public void setShippers(Shippers shippers) {
        this.shippers = shippers;
    }

    public Address getAddress() {
        return address;
    }

    public Shipping address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public Shipping products(Set<Products> products) {
        this.products = products;
        return this;
    }

    public Shipping addProducts(Products products) {
        this.products.add(products);
        products.setShipping(this);
        return this;
    }

    public Shipping removeProducts(Products products) {
        this.products.remove(products);
        products.setShipping(null);
        return this;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }

    public Orders getOrders() {
        return orders;
    }

    public Shipping orders(Orders orders) {
        this.orders = orders;
        return this;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shipping)) {
            return false;
        }
        return id != null && id.equals(((Shipping) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shipping{" +
            "id=" + getId() +
            ", shipDate='" + getShipDate() + "'" +
            ", trackingId='" + getTrackingId() + "'" +
            "}";
    }
}
