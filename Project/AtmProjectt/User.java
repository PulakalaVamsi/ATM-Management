package Atmprojectt;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    DecimalFormat dFormat = new DecimalFormat("'₹ '###,##0.00");

    private long customerNum;
    private int pinNumber;
    private String mobileNumber;
    private String aadhaarNumber;
    private double checkingBal = 1000;
    private double savingBal = 1000;

    private List<String> transactionHistory = new ArrayList<>();

    public void setCustomerNum(long customerNum) {
        this.customerNum = customerNum;
    }

    public long getCustomerNum() {
        return customerNum;
    }

    public void setPinNum(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public int getPinNum() {
        return pinNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public double getCheckingBalance() {
        return checkingBal;
    }

    public double getSavingBalance() {
        return savingBal;
    }

    public void withdrawChecking(double amount) {
        if (checkingBal >= amount) {
            checkingBal -= amount;
            transactionHistory.add("Withdraw Checking: ₹" + amount);
        } else {
            System.out.println("Insufficient Checking Balance.");
        }
    }

    public void withdrawSaving(double amount) {
        if (savingBal >= amount) {
            savingBal -= amount;
            transactionHistory.add("Withdraw Saving: ₹" + amount);
        } else {
            System.out.println("Insufficient Saving Balance.");
        }
    }

    public void depositChecking(double amount) {
        checkingBal += amount;
        transactionHistory.add("Deposit Checking: ₹" + amount);
    }

    public void depositSaving(double amount) {
        savingBal += amount;
        transactionHistory.add("Deposit Saving: ₹" + amount);
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("--- Transaction History ---");
            for (String t : transactionHistory) {
                System.out.println(t);
            }
        }
    }
}
