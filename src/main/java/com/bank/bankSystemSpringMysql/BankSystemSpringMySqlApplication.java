package com.bank.bankSystemSpringMysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.bank.bank_account.bankAccount;
import com.bank.bank_account.transactions;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class BankSystemSpringMySqlApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(BankSystemSpringMySqlApplication.class, args);
        MainController controller = context.getBean(MainController.class); //qwe //zxc

        Scanner askAction = new Scanner(System.in);
        int action;
        boolean working = true;


//        List<transactions> all = controller.findByRecipient("vika");
//
//        controller.transactionPrint(all.get(0));


        do {

            System.out.println("Choice action: \n" +
                    "1 - See all accounts\n2 - Create account\n3 - Log in\n4 - Change account balance\n5 - Find transaction");
            action = askAction.nextInt();


            switch (action){

                case 1 -> {
                    controller.SeeAllBankAccounts();
                    System.out.println();
                }

                case 2 -> {

                    String newAccountName = null, newAccountPassword = null;
                    Scanner AskDataForNewAccount = new Scanner(System.in);
                    bankAccount checkData = null;
                    bankAccount NewAccount= new bankAccount();
                    do {
                        System.out.println("Enter a name for new account\nEnter - '2' to return ");
                        newAccountName = AskDataForNewAccount.next();

                        checkData = controller.findByAccountName(newAccountName);

                        if(Objects.equals(newAccountName, "2")) {
                            break;
                        }
                        else if(checkData != null){
                            System.out.println("This name is busy");
                            newAccountName = null;
                        }
                        else{
                            NewAccount.setAccountName(newAccountName);
                        }


                    }while(newAccountName == null);

                    if(Objects.equals(newAccountName, "2")) {
                        break;
                    }

                    System.out.println("Enter a password for new account\nEnter - '2' to return ");
                    newAccountPassword = AskDataForNewAccount.next();

                    if(Objects.equals(newAccountPassword, "2")) {
                        break;
                    }

                    NewAccount.setAccountPassword(newAccountPassword);

                    System.out.println("New account was created successfully");

                    controller.createBankAccount(NewAccount);

                }

                case 3 -> {

                    Scanner askDataForAccount = new Scanner(System.in);
                    String tryLogInName = null, tryLogInPassword = null;
                    bankAccount account = null;

                    do {
                        System.out.println("Enter a account name\nEnter - '2' to return");
                        tryLogInName = askDataForAccount.next();

                        if(Objects.equals(tryLogInName, "2"))  break;

                        else {
                            account = controller.findByAccountName(tryLogInName);

                            if (Objects.equals(tryLogInName, "2")) break;
                            else if (account == null) {
                                System.out.println("This account doesn't exist");
                            }
                        }

                    }while (account == null);

                    do{
                        if(account != null) {
                            System.out.println("Enter a password for account: " + account.getAccountName());
                            System.out.println("Enter - '2' to return");
                            tryLogInPassword = askDataForAccount.next();

                            if (Objects.equals(tryLogInPassword, "2")) break;
                            else if (Objects.equals(tryLogInPassword, account.getAccountPassword())) {
                                System.out.println("Welcome");
                                controller.bankAccountPrint(account);
                                System.out.println();
                            } else {
                                System.out.println("Wrong password");
                            }
                        }
                        else break;
                    }while(tryLogInPassword == null);

                }

                case 4 -> {
                    Scanner FindAccountById = new Scanner(System.in);
                    int idToFind = 0;

                    do{
                        System.out.println("Enter account id:");
                        idToFind = FindAccountById.nextInt();

                        bankAccount selectedAccount = controller.bankAccountFindId(idToFind);

                        if(selectedAccount != null){

                            controller.bankAccountPrint(selectedAccount);


                            int whatToDoBalance;

                        }


                    }while(true);
                }

                case 5 ->{

                    int FindBy;
                    Scanner FindTransactionBy = new Scanner(System.in);

                    do{
                        System.out.println("1 - Find by id\n2 - Find by sender\n3 - Find recipient\b4 - back");
                        FindBy = FindTransactionBy.nextInt();


                        switch (FindBy){

                            case 1 ->{
                                Scanner AskDataForFind = new Scanner(System.in);
                                int idToFind;

                                System.out.println("Enter id transaction");
                                idToFind = AskDataForFind.nextInt();

                                transactions transaction = controller.transactionFindId(idToFind);

                                if(transaction != null){
                                    controller.transactionPrint(transaction);
                                }
                                else{
                                    System.out.println("Transaction with id " + idToFind + " not found");
                                }
                            }

                            case 2 ->{
                                Scanner AskDataTransaction = new Scanner(System.in);
                                String senderToFind;

                                System.out.println("Enter sender");
                                senderToFind = AskDataTransaction.next();

                                List<transactions> foundedTransaction = controller.transactionFindBySender(senderToFind);

                                if(foundedTransaction != null){
                                    for(transactions transactionToPrint : foundedTransaction){
                                        controller.transactionPrint(transactionToPrint);
                                    }
                                }
                                else{
                                    System.out.println("Transaction with id not found");
                                }
                            }

                            case 3 ->{
                                Scanner AskDataTransaction = new Scanner(System.in);
                                String receptionToFind;

                                System.out.println("Enter sender");
                                receptionToFind = AskDataTransaction.next();

                                List<transactions> foundedTransaction = controller.transactionFindByRecipient(receptionToFind);

                                if(foundedTransaction != null){
                                    for(transactions transactionToPrint : foundedTransaction){
                                        controller.transactionPrint(transactionToPrint);
                                    }
                                }
                                else{
                                    System.out.println("Transaction with id not found");
                                }
                            }

                            case 4 -> {
                                break;
                            }

                        }


                    }while(FindBy <= 3);

                }

                case 10 -> {
                    working = false;
                    break;
                }
            }


        }while(working);




        context.close();
        SpringApplication.run(BankSystemSpringMySqlApplication.class, args);
    }

}
