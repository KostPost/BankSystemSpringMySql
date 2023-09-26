package com.bank.bankSystemSpringMysql;

import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import com.bank.bank_account.bankAccountRepository;
import com.bank.bank_account.bankAccount;
import com.bank.bank_account.balanceHistory;
import com.bank.bank_account.balanceHistoryRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Controller
@ComponentScan(basePackages = {"com.bank.bank_account"})
public class MainController {

    private final bankAccountRepository bankAccountRepository;
    private final balanceHistoryRepository balanceHistoryRepository;

    @Autowired
    public MainController(bankAccountRepository bankAccountRepository, balanceHistoryRepository balanceHistoryRepository){
        this.bankAccountRepository = bankAccountRepository;
        this.balanceHistoryRepository = balanceHistoryRepository;
    }

    public void SeeBankAccounts(){

        List<bankAccount> accounts = bankAccountRepository.findAll();
        for(bankAccount account : accounts)
        {
            bankAccountPrint(account);
        }
    }
    public void bankAccountPrint(bankAccount accountPrint){
        System.out.println("\naccount id - " + accountPrint.getAccount_id());
        System.out.println("account name - " + accountPrint.getAccount_name());
        System.out.println("account password - " + accountPrint.getAccount_password());
        System.out.println("account balance - " + accountPrint.getAccount_balance());
        System.out.println("account creation date - " + accountPrint.getAccount_creation_date());
    }

    public bankAccount createBankAccount(@RequestBody bankAccount newBankAccount){
        return bankAccountRepository.save(newBankAccount);
    }

    public void SendMoney(bankAccount senderAccount, bankAccount recipientAccount, double transaction_amount){

        senderAccount.MinusBalance(transaction_amount);

        recipientAccount.PlusBalance(transaction_amount);

    }

    @Transactional
    public Optional<bankAccount> update(int account_id, bankAccountRepository AccountChange) {
        return bankAccountRepository.findById(account_id).map(target -> {

            target.PlusBalance(120);

            return target; //qwe //qwe
        });
    }


}
