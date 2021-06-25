package mvc.Model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int accountNumber;
    private String accountHolder;
    private double balance;
    private List<Transacctions> transacctions;

    public Account(int accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        balance = 0;
        transacctions = new ArrayList<Transacctions>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(Transacctions tran){
        transacctions.add(tran);
    }

    public List<Transacctions> getTransacctions() {
        return transacctions;
    }

    public void setTransacctions(List<Transacctions> transacctions) {
        this.transacctions = transacctions;
    }
}
