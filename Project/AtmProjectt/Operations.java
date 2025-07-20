package AtmProjectt;

import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Operations {
    private HashMap<Long, User> data = new HashMap<>();
    Scanner sc = new Scanner(System.in);
    Random rand = new Random();
    final String FILE = "accounts.dat";

    public Operations() {
        loadAccounts();
    }

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(data);
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    private void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            data = (HashMap<Long, User>) ois.readObject();
        } catch (Exception e) {
            data = new HashMap<>();
        }
    }

    public void getLogin() {
        System.out.println("\n--- ATM SYSTEM ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Forgot PIN");
        System.out.print("Select option: ");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                loginProcess();
                break;
            case 2:
                register();
                break;
            case 3:
                forgotPin();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void register() {
        System.out.print("Enter 11 Digit Account Number: ");
        long accNum = sc.nextLong();
        if (data.containsKey(accNum)) {
            System.out.println("Account already exists. Please login.");
            return;
        }
        System.out.print("Enter Mobile Number: ");
        String mob = sc.next();
        int otp = 1000 + rand.nextInt(9000);
        System.out.println("Your OTP: " + otp);
        System.out.print("Enter OTP: ");
        int enteredOtp = sc.nextInt();
        if (enteredOtp == otp) {
            System.out.print("Set 4-digit PIN: ");
            int pin1 = sc.nextInt();
            System.out.print("Re-enter PIN: ");
            int pin2 = sc.nextInt();
            if (pin1 == pin2 && String.valueOf(pin1).length() == 4) {
                User u = new User();
                u.setCustomerNum(accNum);
                u.setPinNum(pin1);
                u.setMobileNumber(mob);
                data.put(accNum, u);
                saveAccounts();
                System.out.println("Account created with ₹10,000 Checking & Saving Balance!");
            } else {
                System.out.println("PIN mismatch or invalid.");
            }
        } else {
            System.out.println("Wrong OTP.");
        }
    }

    private void forgotPin() {
        System.out.print("Enter Account Number: ");
        long accNum = sc.nextLong();
        if (!data.containsKey(accNum)) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter Registered Mobile Number: ");
        String mob = sc.next();
        User u = data.get(accNum);
        if (u.getMobileNumber().equals(mob)) {
            int otp = 1000 + rand.nextInt(9000);
            System.out.println("Your OTP: " + otp);
            System.out.print("Enter OTP: ");
            int enteredOtp = sc.nextInt();
            if (enteredOtp == otp) {
                System.out.print("Enter new PIN: ");
                int pin1 = sc.nextInt();
                System.out.print("Re-enter new PIN: ");
                int pin2 = sc.nextInt();
                if (pin1 == pin2 && String.valueOf(pin1).length() == 4) {
                    u.setPinNum(pin1);
                    saveAccounts();
                    System.out.println("PIN updated.");
                } else {
                    System.out.println("PIN mismatch or invalid.");
                }
            } else {
                System.out.println("Wrong OTP.");
            }
        } else {
            System.out.println("Mobile number mismatch.");
        }
    }

    private void loginProcess() {
        System.out.print("Enter Account Number: ");
        long accNum = sc.nextLong();
        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        if (data.containsKey(accNum)) {
            User u = data.get(accNum);
            if (u.getPinNum() == pin) {
                System.out.println("Login Successful!");
                menu(u);
            } else {
                System.out.println("Invalid PIN.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private void menu(User u) {
        while (true) {
            System.out.println("\n1. View Details");
            System.out.println("2. Withdraw Checking Account");
            System.out.println("3. Deposit Checking Account");
            System.out.println("4. Withdraw Saving Account");
            System.out.println("5. Deposit Saving Account");
            System.out.println("6. Transaction History");
            System.out.println("7. Change Mobile Number");
            System.out.println("8. Delete Account");
            System.out.println("9. Logout");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Account Number : " + u.getCustomerNum());
                    System.out.println("Mobile Number : " + u.getMobileNumber());
                    System.out.println("Checking Account Balance : " + u.dFormat.format(u.getCheckingBalance()));
                    System.out.println("Saving Account Balance : " + u.dFormat.format(u.getSavingBalance()));
                    break;
                case 2:
                    System.out.print("Eneter Amount: ");
                    u.withdrawChecking(sc.nextDouble());
                    saveAccounts();
                    break;
                case 3:
                    System.out.print(" Enter Amount: ");
                    u.depositChecking(sc.nextDouble());
                    saveAccounts();
                    break;
                case 4:
                    System.out.print("Enter Amount: ");
                    u.withdrawSaving(sc.nextDouble());
                    saveAccounts();
                    break;
                case 5:
                    System.out.print("Enter Amount: ");
                    u.depositSaving(sc.nextDouble());
                    saveAccounts();
                    break;
                case 6:
                    u.showTransactionHistory();
                    break;
                case 7:
                    changeMobile(u);
                    break;
                case 8:
                    deleteAccount(u);
                    return;
                case 9:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void changeMobile(User u) {
        System.out.print("Enter Current Mobile: ");
        String oldMob = sc.next();
        if (!u.getMobileNumber().equals(oldMob)) {
            System.out.println("Mobile number mismatch.");
            return;
        }
        System.out.print("Enter New Mobile: ");
        String newMob = sc.next();
        int otp = 1000 + rand.nextInt(9000);
        System.out.println("Your OTP: " + otp);
        System.out.print("Enter OTP: ");
        int enteredOtp = sc.nextInt();
        if (enteredOtp == otp) {
            u.setMobileNumber(newMob);
            saveAccounts();
            System.out.println("Mobile updated.");
        } else {
            System.out.println("Wrong OTP.");
        }
    }

    private void deleteAccount(User u) {
        System.out.print("Confirm Delete? yes/no: ");
        String confirm = sc.next();
        if (confirm.equalsIgnoreCase("yes")) {
            data.remove(u.getCustomerNum());
            saveAccounts();
            System.out.println("Account deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }
}

