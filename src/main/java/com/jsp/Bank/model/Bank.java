package com.jsp.Bank.model;

public class Bank {
	private int userId;
	private String userFirstName;
	private String userLastName;
	private String userEmailId;
	private String userPassword;
	private double userAmmount;
	private String userMobileNumber;
	private String userAccountNumber;
	private String userAddress;

	public Bank() {

	}

	public Bank(int userId, String userFirstName, String userLastName, String userEmailId, String userPassword,
			double userAmmount, String userMobileNumber, String userAccountNumber, String userAddress) {
		super();
		this.userId = userId;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmailId = userEmailId;
		this.userPassword = userPassword;
		this.userAmmount = userAmmount;
		this.userMobileNumber = userMobileNumber;
		this.userAccountNumber = userAccountNumber;
		this.userAddress = userAddress;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public double getUserAmmount() {
		return userAmmount;
	}

	public void setUserAmmount(double userAmmount) {
		this.userAmmount = userAmmount;
	}

	public String getUserMobileNumber() {
		return userMobileNumber;
	}

	public void setUserMobileNumber(String userMobileNumber) {
		this.userMobileNumber = userMobileNumber;
	}

	public String getUserAccountNumber() {
		return userAccountNumber;
	}

	public void setUserAccountNumber(String userAccountNumber) {
		this.userAccountNumber = userAccountNumber;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

}
