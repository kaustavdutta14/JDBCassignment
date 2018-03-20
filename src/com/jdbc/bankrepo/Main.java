package com.jdbc.bankrepo;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import com.jdbc.Account;
import com.jdbc.AccountType;
import com.jdbc.Bank;
import com.jdbc.Patron;
import com.jdbc.Result;
import com.jdbc.transaction;

public class Main {

	public static void main(String args[]) throws FileNotFoundException
	{
		
 		//Patron p= new Patron(1,"george","C:/Users/Kaustav Dutta/Desktop/Assignments IMCS/Assignments/JDBC/george.png");
		BankRepository br= new BankRepository();
		Account acc= br.findAccount(1);
		//Bank bank=br.findBank(1);
		
 		transaction trans=new transaction(4,400,AccountType.DEBIT,acc);
		
		
		//Result r=br.remove(p);
		//Result r=br.update(p);
		//System.out.println(r);
		//List<Patron> name =br.findPatron(p.name);
		//System.out.println(name);
		Result r=br.transact(trans);
		//Account r=br.findAccount(1);
		System.out.println(trans);
		//System.out.println(r);
		
		
	}
}
