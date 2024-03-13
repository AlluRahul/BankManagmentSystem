package com.jsp.Bank.dao;

public class BankHelpDesk {
	public static BankDao customerService() {
		// TODO Auto-generated method stub
		BankDao bankdao = new BankDaoImp();
		return bankdao;
	}
}
