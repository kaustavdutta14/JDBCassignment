package com.jdbc;

public class Account {
	public int id;
	public Bank bank;
	public Patron patron;
	public Account(int id, Bank bank, Patron patron) {
		super();
		this.id = id;
		this.bank = bank;
		this.patron = patron;
	}
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", bank=" + bank + ", patron=" + patron + "]";
	}
	public Account() {
		// TODO Auto-generated constructor stub
	}
	
}
