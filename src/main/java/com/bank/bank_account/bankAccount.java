package com.bank.bank_account;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Getter
@Setter
@Entity
@Table(name = "bank_account")
public class bankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private int account_id;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_password")
    private String accountPassword;

    @Column(name = "account_balance")
    private double account_balance;

    @Column(name = "account_creation_date")
    private LocalDate account_creation_date = null;

    public bankAccount(){
        if(account_creation_date == null){
            account_creation_date = LocalDate.now();
        }
    }

    public double PlusBalance(double sum){
        account_balance += sum;
        return account_balance;
    }

    public double MinusBalance( double sum){
        account_balance -= sum;
        return account_balance;
    }


}
