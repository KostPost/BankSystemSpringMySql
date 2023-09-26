package com.bank.bankSystemSpringMysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.bank.bank_account.bankAccountRepository;
import com.bank.bank_account.bankAccount;
import com.bank.bank_account.balanceHistory;
import com.bank.bank_account.balanceHistoryRepository;

@SpringBootApplication
public class BankSystemSpringMySqlApplication {

    public static void main(String[] args) { //qwe //zxc

        ConfigurableApplicationContext context = SpringApplication.run(BankSystemSpringMySqlApplication.class, args);


        MainController controller = context.getBean(MainController.class); //qwe //zxc
//qwe


        controller.SeeBankAccounts(); //qwe


        context.close();
        SpringApplication.run(BankSystemSpringMySqlApplication.class, args);
    }

}
