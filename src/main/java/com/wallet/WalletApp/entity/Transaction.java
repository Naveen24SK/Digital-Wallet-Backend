package com.wallet.WalletApp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 🔥 AUTO GENERATED

    @Column(name = "transaction_id", unique = true, length = 20)
    private String transactionId;

    private Long senderAccountId;
    private Long senderWalletId;
    private Long receiverAccountId;
    private Long receiverWalletId;

    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal amount;

    private String transactionType;
    private String category = "OTHERS";
    private String purpose;
    private String status = "SUCCESS";

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSenderAccountId() { return senderAccountId; }
    public void setSenderAccountId(Long senderAccountId) { this.senderAccountId = senderAccountId; }

    public Long getSenderWalletId() { return senderWalletId; }
    public void setSenderWalletId(Long senderWalletId) { this.senderWalletId = senderWalletId; }

    public Long getReceiverAccountId() { return receiverAccountId; }
    public void setReceiverAccountId(Long receiverAccountId) { this.receiverAccountId = receiverAccountId; }

    public Long getReceiverWalletId() { return receiverWalletId; }
    public void setReceiverWalletId(Long receiverWalletId) { this.receiverWalletId = receiverWalletId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void setTransactionId(String txId) {
        this.transactionId = txId;
    }
    public String getTransactionId(){ return transactionId;}

}
