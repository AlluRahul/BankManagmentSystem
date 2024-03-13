package com.jsp.Bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import com.jsp.Bank.model.Bank;

public class BankDaoImp implements BankDao {
	private Connection conn = null;
	private PreparedStatement pre = null;
	private ResultSet rs = null;
	private Scanner sc = new Scanner(System.in);

	public BankDaoImp() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/userinformation?user=root&password=12345");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean userRegistration(Bank bank) {
		try {
			pre = conn.prepareStatement(
					"INSERT INTO userdetails (user_firstname, user_lastname,user_emailid,user_password,user_amount,user_mobilenumber,user_accountnumber,user_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

			pre.setString(1, bank.getUserFirstName());
			pre.setString(2, bank.getUserLastName());
			pre.setString(3, bank.getUserEmailId());
			String tempName = bank.getUserFirstName().toLowerCase();
			Random random = new Random();
			int tempnum = random.nextInt(1000);
			String bankemailid = tempName + tempnum + "@teca52.com";
			pre.setString(4, bank.getUserPassword());
			pre.setDouble(5, bank.getUserAmmount());
			pre.setString(6, bank.getUserMobileNumber());
			long userAccountNumber = generateRandomAccountNumber();
			pre.setString(7, String.valueOf(userAccountNumber));
			pre.setString(8, bank.getUserAddress());

			int result = pre.executeUpdate();
			return result != 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private long generateRandomAccountNumber() {
		Random random = new Random();
		long userAccountNumber = random.nextLong(9000000000L) + 1000000000L;
		return userAccountNumber;
	}

	@Override
	public void credit() {
		// Implement credit logic here
	}

	@Override
	public void debit(String userAccountNumber, String userPassword) {
		try {
			pre = conn.prepareStatement("SELECT * FROM userdetails WHERE user_accountnumber = ? AND user_password = ?");
			pre.setString(1, userAccountNumber);
			pre.setString(2, userPassword);

			rs = pre.executeQuery();

			if (rs.next()) {
				System.out.println("Enter the amount to debit:");
				double userAmount = sc.nextDouble();

				if (userAmount >= 0) {
					double databaseAmount = rs.getDouble("user_amount");

					if (databaseAmount >= userAmount) {
						double balance = databaseAmount - userAmount;

						pre = conn.prepareStatement(
								"UPDATE userdetails SET user_amount = ? WHERE user_accountnumber = ? AND user_password = ?");
						pre.setDouble(1, balance);
						pre.setString(2, userAccountNumber);
						pre.setString(3, userPassword);

						int result1 = pre.executeUpdate();

						if (result1 != 0) {
							System.out.println("Amount debited successfully");
						} else {
							System.out.println("Failed to update user details");
						}
					} else {
						System.out.println("Insufficient funds");
					}
				} else {
					System.out.println("Entered amount is invalid. Please enter a non-negative value.");
				}
			} else {
				System.out.println("Invalid account details");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changingPassword() {
		// Implement password change logic here
	}

	// ... (previous code)
	@Override
	public void mobileToMobileTransactions(String userMobileNumber) {
	    try {
	        PreparedStatement selectSender = conn.prepareStatement("SELECT * FROM userdetails WHERE user_mobilenumber=?");
	        selectSender.setString(1, userMobileNumber);
	        ResultSet senderResultSet = selectSender.executeQuery();

	        if (senderResultSet.next()) {
	            System.out.println("Enter Receiver's Mobile Number:");
	            String receiverMobileNumber = sc.next();

	            PreparedStatement selectReceiver = conn.prepareStatement("SELECT * FROM userdetails WHERE user_mobilenumber=?");
	            selectReceiver.setString(1, receiverMobileNumber);
	            ResultSet receiverResultSet = selectReceiver.executeQuery();

	            if (receiverResultSet.next()) {
	                boolean enteredAmount = true;
	                while (enteredAmount) {
	                    System.out.println("Enter your amount:");
	                    double amount = sc.nextDouble();

	                    if (amount >= 0) {
	                        double senderBalance = senderResultSet.getDouble("user_amount");
	                        System.out.println("Enter sender's password:");
	                        String senderPassword = sc.next();

	                        // Verify the password
	                        if (senderResultSet.getString("user_password").trim().equals(senderPassword)) {
	                            if (senderBalance >= amount) {
	                                double newSenderBalance = senderBalance - amount;
	                                PreparedStatement updateSender = conn.prepareStatement(
	                                        "UPDATE userdetails SET user_amount=? WHERE user_mobilenumber=?");
	                                updateSender.setDouble(1, newSenderBalance);
	                                updateSender.setString(2, userMobileNumber);
	                                updateSender.executeUpdate();

	                                double receiverBalance = receiverResultSet.getDouble("user_amount");
	                                double newReceiverBalance = receiverBalance + amount;
	                                PreparedStatement updateReceiver = conn.prepareStatement(
	                                        "UPDATE userdetails SET user_amount=? WHERE user_mobilenumber=?");
	                                updateReceiver.setDouble(1, newReceiverBalance);
	                                updateReceiver.setString(2, receiverMobileNumber);
	                                updateReceiver.executeUpdate();

	                                System.out.println("Transaction successful");
	                                enteredAmount = false;
	                            } else {
	                                System.out.println("Insufficient funds. Sender's Balance: " + senderBalance);
	                                System.out.println("Do you want to try again? (Y/N)");
	                                String tryAgain = sc.next();
	                                if (!tryAgain.equalsIgnoreCase("Y")) {
	                                    enteredAmount = false;
	                                }
	                            }
	                        } else {
	                            System.out.println("Invalid Password. Do you want to try again? (Y/N)");
	                            String tryAgain = sc.next();
	                            if (!tryAgain.equalsIgnoreCase("Y")) {
	                                enteredAmount = false;
	                            }
	                        }
	                    } else {
	                        System.out.println("Entered amount is invalid. Please enter a non-negative value.");
	                    }
	                }
	            } else {
	                System.out.println("Invalid Receiver Mobile Number. Do you want to try again? (Y/N)");
	                String tryAgain = sc.next();
	                if (!tryAgain.equalsIgnoreCase("Y")) {
	                    mobileToMobileTransactions(userMobileNumber); // Retry the transaction
	                }
	            }
	        } else {
	            System.out.println("Invalid Mobile Number. Do you want to try again? (Y/N)");
	            String tryAgain = sc.next();
	            if (!tryAgain.equalsIgnoreCase("Y")) {
	                mobileToMobileTransactions(userMobileNumber); // Retry the transaction
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void BalanceEnquery() {
		// TODO Auto-generated method stub

	}

}
