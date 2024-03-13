package com.jsp.Bank;

import java.util.Scanner;

import com.jsp.Bank.dao.BankDao;
import com.jsp.Bank.dao.BankHelpDesk;
import com.jsp.Bank.model.Bank;

public class App {
    public static void main(String[] args) {
        BankDao bankDao = BankHelpDesk.customerService();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter " + "\n 1. For Registration" + "\n 2. For Credit" + "\n 3. For Debit"
                + "\n 4. For Changing Password" + "\n 5. For Balance Inquiry"
                + "\n 6. For Mobile To Mobile Transaction");

        int response = scanner.nextInt();

        switch (response) {
            case 1:
                registerUser(scanner, bankDao);
                break;
            case 3:
                performDebit(scanner, bankDao);
                break;
            case 6:
                performMobileToMobileTransaction(scanner, bankDao);
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private static void registerUser(Scanner scanner, BankDao bankDao) {
        System.out.println("Enter Your First Name");
        String userFirstName = scanner.next();

        System.out.println("Enter Your Last Name");
        String userLastName = scanner.next();

        System.out.println("Enter Your EmailId");
        String userEmailId = scanner.next();

        if (userEmailId.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
        } else {
            System.out.println("Invalid email format. Please use a valid email address.");
            return;
        }

        System.out.println("Enter Your Password");
        String userPassword = scanner.next();

        System.out.println("Enter Your Amount");
        double userAmount = scanner.nextDouble();

        System.out.println("Enter your MobileNumber");
        String userMobileNumber = scanner.next();

        System.out.println("Enter Your Account Number");
        String userAccountNumber = scanner.next();

        System.out.println("Enter Your Address");
        String userAddress = scanner.next();

        System.out.println();

        // Create a Bank object with user information
        Bank userInformation = new Bank(0, userFirstName, userLastName, userEmailId, userPassword, userAmount,
                userMobileNumber, userAccountNumber, userAddress);

        // Register the user
        boolean registrationStatus = bankDao.userRegistration(userInformation);

        if (registrationStatus) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }

    private static void performDebit(Scanner scanner, BankDao bankDao) {
        System.out.println("Enter your account number");
        String userAccountNumber = scanner.next();
        System.out.println("Enter your password");
        String userPassword = scanner.next();
        bankDao.debit(userAccountNumber, userPassword);
    }

    private static void performMobileToMobileTransaction(Scanner scanner, BankDao bankDao) {
        System.out.println("Enter the senders mobile number");
        String userMobileNumber = scanner.next();
        bankDao.mobileToMobileTransactions(userMobileNumber);
    }
}
