package com.sls.awesomeim.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

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
 * A Payments.
 */
@Entity
@Table(name = "payments")
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "gateway_id", nullable = false)
    private String gatewayId;

    @NotNull
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "bank_txn")
    private String bankTxn;

    @Column(name = "txn_token")
    private String txnToken;

    @Column(name = "response_timestamp")
    private Instant responseTimestamp;

    @Column(name = "checksum")
    private String checksum;

    @Column(name = "txn_amount", precision = 21, scale = 2)
    private BigDecimal txnAmount;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "response_message")
    private String responseMessage;

    @Column(name = "result")
    private String result;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private Orders orders;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public Payments gatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
        return this;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public Payments paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getBankTxn() {
        return bankTxn;
    }

    public Payments bankTxn(String bankTxn) {
        this.bankTxn = bankTxn;
        return this;
    }

    public void setBankTxn(String bankTxn) {
        this.bankTxn = bankTxn;
    }

    public String getTxnToken() {
        return txnToken;
    }

    public Payments txnToken(String txnToken) {
        this.txnToken = txnToken;
        return this;
    }

    public void setTxnToken(String txnToken) {
        this.txnToken = txnToken;
    }

    public Instant getResponseTimestamp() {
        return responseTimestamp;
    }

    public Payments responseTimestamp(Instant responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
        return this;
    }

    public void setResponseTimestamp(Instant responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public String getChecksum() {
        return checksum;
    }

    public Payments checksum(String checksum) {
        this.checksum = checksum;
        return this;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public BigDecimal getTxnAmount() {
        return txnAmount;
    }

    public Payments txnAmount(BigDecimal txnAmount) {
        this.txnAmount = txnAmount;
        return this;
    }

    public void setTxnAmount(BigDecimal txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getBankName() {
        return bankName;
    }

    public Payments bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public Payments responseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public Payments responseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResult() {
        return result;
    }

    public Payments result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getEmail() {
        return email;
    }

    public Payments email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Orders getOrders() {
        return orders;
    }

    public Payments orders(Orders orders) {
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
        if (!(o instanceof Payments)) {
            return false;
        }
        return id != null && id.equals(((Payments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payments{" +
            "id=" + getId() +
            ", gatewayId='" + getGatewayId() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", bankTxn='" + getBankTxn() + "'" +
            ", txnToken='" + getTxnToken() + "'" +
            ", responseTimestamp='" + getResponseTimestamp() + "'" +
            ", checksum='" + getChecksum() + "'" +
            ", txnAmount=" + getTxnAmount() +
            ", bankName='" + getBankName() + "'" +
            ", responseCode='" + getResponseCode() + "'" +
            ", responseMessage='" + getResponseMessage() + "'" +
            ", result='" + getResult() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
