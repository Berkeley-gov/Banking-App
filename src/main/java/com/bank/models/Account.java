package com.bank.models;

import javax.persistence.*;

@Entity
@Table(name="Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private int accountId;

    private int userId;

    private double balance;

    private int activeId;
    
    private int accountTypeId;





}
