package com.bank.bankSystemSpringMysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.bank.bank_account.bankAccountRepository;
import com.bank.bank_account.bankAccount;
import com.bank.bank_account.transactionRepository;
import com.bank.bank_account.transactions;

import java.util.List;

@SpringBootApplication
public class BankSystemSpringMySqlApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(BankSystemSpringMySqlApplication.class, args);
        MainController controller = context.getBean(MainController.class); //qwe //zxc

//        List<bankAccount> all = controller.ReturnBankAccounts();
//
//        bankAccount c1 = all.get(0);
//        bankAccount c2 = all.get(1);
//
//        controller.SendMoney(c1,c2,7);
//
//        controller.SeeAllBankAccounts();

        controller.SeeAllTransaction();
        controller.SeeAllTransaction();

        context.close();
        SpringApplication.run(BankSystemSpringMySqlApplication.class, args);
    }

}
