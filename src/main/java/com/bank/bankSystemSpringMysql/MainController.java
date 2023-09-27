package com.bank.bankSystemSpringMysql;

import com.bank.bank_account.transactions;
import com.bank.bank_account.transactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import com.bank.bank_account.bankAccountRepository;
import com.bank.bank_account.bankAccount;
import com.bank.bank_account.transactionRepository;
import com.bank.bank_account.transactions;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Controller
@ComponentScan(basePackages = {"com.bank.bank_account"})
public class MainController {

    private final bankAccountRepository bankAccountRepository;
    private final transactionRepository transactionRepository;

    @Autowired
    public MainController(bankAccountRepository bankAccountRepository, transactionRepository transactionRepository){
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<bankAccount>ReturnBankAccounts(){
        return bankAccountRepository.findAll();
    }

    public void SeeAllBankAccounts(){
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

    public transactions NewBalance(@RequestBody transactions NewTransaction){
        return transactionRepository.save(NewTransaction);
    }

    public void SendMoney(bankAccount senderAccount, bankAccount recipientAccount, double transaction_amount){

        if(senderAccount.getAccount_balance() >= transaction_amount) {
            double newSenderBalance, newRecipientBalance;
            newSenderBalance = senderAccount.MinusBalance(transaction_amount);
            newRecipientBalance = recipientAccount.PlusBalance(transaction_amount);

            updateBankAccountBalance(senderAccount, newSenderBalance);
            updateBankAccountBalance(recipientAccount, newRecipientBalance);

            System.out.println("the transaction was successful");
        }
        else{
            System.out.println("////////////////////////////////////");
            System.out.println("FAIL");
            System.out.println("Account - " + senderAccount.getAccount_name() + "  doesn't have enough money");
            System.out.println("Need - " + transaction_amount + senderAccount.getAccount_name() + "\thave - " +
                    senderAccount.getAccount_balance());
            System.out.println("////////////////////////////////////");
        }
    }

    public bankAccount updateBankAccountBalance(bankAccount AccountToUpdate, double new_balance) {

        bankAccount updatedAccount = bankAccountRepository.findById(AccountToUpdate.getAccount_id())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        updatedAccount.setAccount_balance(new_balance);

        return bankAccountRepository.save(updatedAccount);
    }
    public bankAccount bankAccountFindId(int id){
        return bankAccountRepository.findById(id).orElse(null);
    }





}
