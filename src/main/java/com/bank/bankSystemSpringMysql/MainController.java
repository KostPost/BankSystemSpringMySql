package com.bank.bankSystemSpringMysql;

import com.bank.bank_account.transactions;
import com.bank.bank_account.transactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import com.bank.bank_account.bankAccountRepository;
import com.bank.bank_account.bankAccount;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    public void SeeAllTransaction(){
        List<transactions> AllTransactions = transactionRepository.findAll();

        for(transactions transaction : AllTransactions){
            transactionPrint(transaction);
        }
    }

    public void transactionPrint(transactions transaction){
        System.out.println("////////////////////////////////////////////////////////////////");
        System.out.println("Transaction data - " + transaction.getTransaction_data() + " ||| Transaction id - " + transaction.getTransaction_id() + " ||| Transaction sum - " + transaction.getTransaction_sum());
        System.out.println("Sender - " + transaction.getSender() + " ||| id - " + transaction.getSender_id());
        System.out.println("Sender balance before transaction - " + transaction.getSender_balance_before_transaction() + " ||| After - " +
                transaction.getSender_balance_after_transaction());
        System.out.println("----------------------------------------------------------------");
        System.out.println("Recipient - " + transaction.getRecipient() + " ||| id - " + transaction.getRecipient_id());
        System.out.println("Recipient balance before transaction - " + transaction.getRecipient_balance_before_transaction() + " ||| After - " +
                transaction.getRecipient_balance_after_transaction());
        System.out.println("////////////////////////////////////////////////////////////////\n");


    }


    public void bankAccountPrint(bankAccount accountPrint){
        System.out.println("\naccount id - " + accountPrint.getAccount_id());
        System.out.println("account name - " + accountPrint.getAccountName());
        System.out.println("account password - " + accountPrint.getAccountPassword());
        System.out.println("account balance - " + accountPrint.getAccount_balance());
        System.out.println("account creation date - " + accountPrint.getAccount_creation_date());
    }

    public bankAccount createBankAccount(@RequestBody bankAccount newBankAccount){
        return bankAccountRepository.save(newBankAccount);
    }

    public transactions AddNewTransaction(@RequestBody transactions NewTransaction){
        return transactionRepository.save(NewTransaction);
    }

    public void SendMoney(bankAccount senderAccount, bankAccount recipientAccount, double transaction_sum){

        if(senderAccount.getAccount_balance() >= transaction_sum) {

            transactions NewTransaction = new transactions();

            NewTransaction.setTransaction_sum(transaction_sum);
            NewTransaction.setSender(senderAccount.getAccountName());
            NewTransaction.setSender_id(senderAccount.getAccount_id());
            NewTransaction.setSender_balance_before_transaction(senderAccount.getAccount_balance());

            NewTransaction.setRecipient(recipientAccount.getAccountName());
            NewTransaction.setRecipient_id(recipientAccount.getAccount_id());
            NewTransaction.setRecipient_balance_before_transaction(recipientAccount.getAccount_balance());

            double newSenderBalance, newRecipientBalance;
            newSenderBalance = senderAccount.MinusBalance(transaction_sum);
            newRecipientBalance = recipientAccount.PlusBalance(transaction_sum);

            updateBankAccountBalance(senderAccount, newSenderBalance);
            updateBankAccountBalance(recipientAccount, newRecipientBalance);

            NewTransaction.setSender_balance_after_transaction(senderAccount.getAccount_balance());
            NewTransaction.setRecipient_balance_after_transaction(recipientAccount.getAccount_balance());

            AddNewTransaction(NewTransaction);

            System.out.println("the transaction was successful");
        }
        else{
            System.out.println("////////////////////////////////////");
            System.out.println("FAIL");
            System.out.println("Account - " + senderAccount.getAccountName() + "  doesn't have enough money");
            System.out.println("Need - " + transaction_sum + senderAccount.getAccountName() + "\thave - " +
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

    public bankAccount findByAccountName(@RequestParam String AccountName) {
        return bankAccountRepository.findByAccountName(AccountName);
    }

    public transactions transactionFindId(int id){
        return transactionRepository.findById(id).orElse(null);
    }

    public List<transactions> findBySender(@RequestParam String FirstName) {
        return transactionRepository.findBySender(FirstName);
    }

    public List<transactions> findByRecipient(@RequestParam String FirstName) {
        return transactionRepository.findByRecipient(FirstName);
    }

}
