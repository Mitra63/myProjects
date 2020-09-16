package com.sadad.ib.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {


    public Account(Long accountNumber, Long userId, Long balance, boolean blocked) {
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.balance = balance;
        this.blocked = blocked;
    }

    public Account() {
    }

    //GeneratedValue with ACCOUNT_SEQUENCE_ID does not work h2
    @Id
    @Column(name = "account_id")
    private Long accountID;

    @Column(name = "account_number")
    private Long accountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private RestUser user;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    private Long balance;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private boolean blocked;

    public enum AccountType {
        CURRENT,
        SAVINGS
    }

}
