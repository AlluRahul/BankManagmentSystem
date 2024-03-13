package com.jsp.Bank.dao;

import com.jsp.Bank.model.Bank;

public interface BankDao {
	boolean userRegistration(Bank bank);
	void credit();
	void debit(String user_accountnumber, String user_password);;
	void changingPassword();
	void BalanceEnquery();
	void mobileToMobileTransactions(String userMobileNumber);
}
