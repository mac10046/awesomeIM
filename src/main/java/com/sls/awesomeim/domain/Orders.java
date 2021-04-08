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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(unique = true)
    private Customers customers;

    @OneToMany(mappedBy = "orders")
    private Set<Shipping> shippings = new HashSet<>();

    @OneToMany(mappedBy = "orders")
    private Set<OrderDetails> orderDetails = new HashSet<>();

    @OneToOne(mappedBy = "orders")
    @JsonIgnore
    private Payments payments;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public Orders orderDate(Instant orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean isIsPaid() {
        return isPaid;
    }

    public Orders isPaid(Boolean isPaid) {
        this.isPaid = isPaid;
        return this;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Orders amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Customers getCustomers() {
        return customers;
    }

    public Orders customers(Customers customers) {
        this.customers = customers;
        return this;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
    }

    public Set<Shipping> getShippings() {
        return shippings;
    }

    public Orders shippings(Set<Shipping> shippings) {
        this.shippings = shippings;
        return this;
    }

    public Orders addShipping(Shipping shipping) {
        this.shippings.add(shipping);
        shipping.setOrders(this);
        return this;
    }

    public Orders removeShipping(Shipping shipping) {
        this.shippings.remove(shipping);
        shipping.setOrders(null);
        return this;
    }

    public void setShippings(Set<Shipping> shippings) {
        this.shippings = shippings;
    }

    public Set<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public Orders orderDetails(Set<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }

    public Orders addOrderDetails(OrderDetails orderDetails) {
        this.orderDetails.add(orderDetails);
        orderDetails.setOrders(this);
        return this;
    }

    public Orders removeOrderDetails(OrderDetails orderDetails) {
        this.orderDetails.remove(orderDetails);
        orderDetails.setOrders(null);
        return this;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Payments getPayments() {
        return payments;
    }

    public Orders payments(Payments payments) {
        this.payments = payments;
        return this;
    }

    public void setPayments(Payments payments) {
        this.payments = payments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orders)) {
            return false;
        }
        return id != null && id.equals(((Orders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", isPaid='" + isIsPaid() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
