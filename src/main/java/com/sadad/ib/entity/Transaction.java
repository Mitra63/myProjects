package com.sadad.ib.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    public Transaction(Long transactionId, Long sourceAccountId, Long destinationAccountId, Long amount,
                       OperationType operationType, Date transactionDate, String description, Long transactionOwnerId) {
        this.transactionId = transactionId;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.operationType = operationType;
        this.transactionDate = transactionDate;
        this.description = description;
        this.transactionOwnerId = transactionOwnerId;
    }

    public Transaction() {
    }

    @Id
    @Column(name="transaction_id")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false, insertable = false)
    private Account sourceAccount;

    @Column(name = "account_id", nullable = false)
    private Long sourceAccountId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false, insertable = false)
    private Account destinationAccount;
    @Column(name = "destination_account_id", nullable = false)
    private Long destinationAccountId;

    private Long amount;

    @Column(name = "operation_type")
    private OperationType operationType;

    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private RestUser transactionOwner;
    @Column(name = "user_id", nullable = false)
    private Long transactionOwnerId;

    public enum OperationType {
        DEPOSIT,
        WITHDRAWAL
    }
}
