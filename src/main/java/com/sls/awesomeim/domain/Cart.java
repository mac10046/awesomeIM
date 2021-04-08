package com.sls.awesomeim.domain;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Coupons coupons;

    @OneToMany(mappedBy = "cart")
    private Set<Products> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coupons getCoupons() {
        return coupons;
    }

    public Cart coupons(Coupons coupons) {
        this.coupons = coupons;
        return this;
    }

    public void setCoupons(Coupons coupons) {
        this.coupons = coupons;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public Cart products(Set<Products> products) {
        this.products = products;
        return this;
    }

    public Cart addProducts(Products products) {
        this.products.add(products);
        products.setCart(this);
        return this;
    }

    public Cart removeProducts(Products products) {
        this.products.remove(products);
        products.setCart(null);
        return this;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cart)) {
            return false;
        }
        return id != null && id.equals(((Cart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            "}";
    }
}
