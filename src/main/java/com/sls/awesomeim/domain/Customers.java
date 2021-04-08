package com.sls.awesomeim.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Customers.
 */
@Entity
@Table(name = "customers")
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "customers")
    private Set<Address> addresses = new HashSet<>();

    @OneToOne(mappedBy = "customers")
    @JsonIgnore
    private Invoices invoices;

    @OneToOne(mappedBy = "customers")
    @JsonIgnore
    private Quotes quotes;

    @OneToOne(mappedBy = "customers")
    @JsonIgnore
    private Orders orders;

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

    public Customers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Customers contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public Customers email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Customers addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Customers addAddress(Address address) {
        this.addresses.add(address);
        address.setCustomers(this);
        return this;
    }

    public Customers removeAddress(Address address) {
        this.addresses.remove(address);
        address.setCustomers(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Invoices getInvoices() {
        return invoices;
    }

    public Customers invoices(Invoices invoices) {
        this.invoices = invoices;
        return this;
    }

    public void setInvoices(Invoices invoices) {
        this.invoices = invoices;
    }

    public Quotes getQuotes() {
        return quotes;
    }

    public Customers quotes(Quotes quotes) {
        this.quotes = quotes;
        return this;
    }

    public void setQuotes(Quotes quotes) {
        this.quotes = quotes;
    }

    public Orders getOrders() {
        return orders;
    }

    public Customers orders(Orders orders) {
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
        if (!(o instanceof Customers)) {
            return false;
        }
        return id != null && id.equals(((Customers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customers{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
