package com.jdbc;

public class transaction {
	public int id;
	public Account account;
	public double amount;
	public AccountType accountType;
	
	public transaction(int id,double amount, AccountType accountType,  Account account) {
		super();
		this.id = id;
		this.account =account ;
		this.amount = amount;
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "transaction [id=" + id + ", account=" + account + ", amount=" + amount + ", accountType=" + accountType
				+ "]";
	}
	public transaction() {
		// TODO Auto-generated constructor stub
	}
	
	
}
