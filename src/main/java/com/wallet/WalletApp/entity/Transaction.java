package com.wallet.WalletApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private String id;

    private Long senderWalletId;
    private Long receiverWalletId;
    private BigDecimal amount;
    private BigDecimal fee;
    private String transactionType;
    private String status;
    private String description;
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getSenderWalletId() { return senderWalletId; }
    public void setSenderWalletId(Long senderWalletId) { this.senderWalletId = senderWalletId; }

    public Long getReceiverWalletId() { return receiverWalletId; }
    public void setReceiverWalletId(Long receiverWalletId) { this.receiverWalletId = receiverWalletId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getFee() { return fee; }
    public void setFee(BigDecimal fee) { this.fee = fee; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
