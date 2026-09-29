package org.example;
import javax.naming.directory.AttributeModificationException;
import java.util.*;
abstract class Account{
    private String accountNumber;
    protected double balance;

    Account(String accountNumber,double balance ){
        this.accountNumber=accountNumber;
        this.balance=balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance(){
        return balance;
    }
    public void deposit(double amount){
        balance+=amount;
        System.out.println("Amount deposisted successfully:"+accountNumber);
        return;
    }
    public void withdraw(double amount){
        if(balance<amount){
            System.out.println("insufficient balance :");
            return;
        }
        balance-=amount;
        System.out.println("Withdraw success"+amount);
        System.out.println("Balance :"+balance);
    }
}
class Savingsaccount extends Account{
    Savingsaccount(String accountNumber,double balance){
        super(accountNumber,balance);
    }
}
class Currentaccount extends Account{
    Currentaccount(String accountNumber,double amount){
        super(accountNumber,amount);
    }
}
class Card{
    private final String cardNumber;
    private int pin;
    private final String accountNumber;

    Card(String cardNumber,int pin,String accountNumber){
        this.cardNumber=cardNumber;
        this.pin=pin;
        this.accountNumber=accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public boolean checkPin(int checkPin){
        return pin==checkPin;
    }
    public void change(int newPin){
        pin=newPin;
        System.out.println("new Pin change Successfully");
    }
}
class Bank{
    private HashMap<String,Account>accounts=new HashMap<>();
    public void addAccount(Account account){
        if(accounts.containsKey(account.getAccountNumber())){
            System.out.println("This account already Exists");
            return;
        }
        accounts.put(account.getAccountNumber(),account);
        System.out.println("New account add successfully");
        return;
    }
    public Account getAccount(String accountNumber){
         return accounts.get(accountNumber);
    }
    public boolean validateCard(Card card){
        return accounts.containsKey(card.getAccountNumber());
    }
}
class CashDispenser{
    private double cashAvailable;
    CashDispenser(double cash){
        this.cashAvailable=cash;
    }
    public void addCash(double cash){
        cashAvailable+=cash;
        System.out.println("cash add in dispenser");
    }
    public boolean cashCandispense(double amount){
       return amount>0 && cashAvailable >=amount;
    }
    public double getCashAvailabe(){
        return cashAvailable;
    }
    public void cashDispense(double amount){
        if(cashCandispense(amount)) {
            cashAvailable -= amount;
            System.out.println("Please collect amount thank you");
        }
    }
}
class ATM{
    private Bank bank;
    private CashDispenser cashDispenser;
    private Card card;
    private Account currentAccount;
    private boolean authendication;

    ATM(Bank bank,CashDispenser cashDispenser){
        this.bank=bank;
        this.cashDispenser=cashDispenser;
    }
    public void insertCard(Card card){
        if(card==null){
            System.out.println("Insert your card first");
            return;
        }
        if(!bank.validateCard(card)){
            System.out.println("Insert valid card");
            return;
        }
        this.card=card;
        this.currentAccount=bank.getAccount(card.getAccountNumber());
        authendication=false;
        System.out.println("Card inserted successfully");
        System.out.println("Enter pin ");
    }
    public void enterPin(int pin){
        if(card==null){
            System.out.println("Insert card first");
            return;
        }
        if(authendication){
            System.out.println("Already Authendicated");
            return;
        }
        if(card.checkPin(pin)){
            authendication=true;
            System.out.println("pin success");
            return;
        }
        else{
            System.out.println("Wrong pin");
            return;
        }
    }
    public void checkBalance(){
        if(!authendication){
            System.out.println("Authendicate first");
            return;
        }
        System.out.println("Balance : "+currentAccount.getBalance());
        return;
    }
    public void deposit(double amount){
        if(!authendication){
            System.out.println("Authendicate first");
            return;
        }
        currentAccount.deposit(amount);
        return;
    }
    public void withdraw(double amount){
        if(!authendication){
            System.out.println("Authendicate first");
            return;
        }
        currentAccount.withdraw(amount);
        return;
    }
    public void pinChange(int pin){
        if(!authendication){
            System.out.println("Authendicate first");
            return;
        }
        if(pin>=1000 && pin<=9999){
            card.change(pin);
            return;
        }
        System.out.println("Not valid pin");
        return;
    }
    public void ejectedCard(){
        card=null;
        currentAccount=null;
        authendication=false;
        System.out.println("Card ejected successfully Thank you ");
    }

}
public class Main{
    public static void main(String [] args){
       Bank bank=new Bank();
       Account acc1=new Savingsaccount("acc0001",1000);
       Account acc2=new Currentaccount("acc0002",5000);
       bank.addAccount(acc1);
       bank.addAccount(acc2);
       Card card1=new Card("acc10001",1958,"acc0001");
       Card card=new Card("acc20002",1951,"acc0002");
       CashDispenser cashDispenser=new CashDispenser(5000);
       ATM atm=new ATM(bank,cashDispenser);
       atm.insertCard(card1);
       atm.enterPin(1958);
       atm.checkBalance();
       atm.withdraw(500);
       atm.ejectedCard();
    }
}