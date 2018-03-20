package com.jdbc.bankrepo;

import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jdbc.Account;
import com.jdbc.AccountType;
import com.jdbc.Bank;
import com.jdbc.Conn;
import com.jdbc.Patron;
import com.jdbc.Result;
import com.jdbc.transaction;
import com.mysql.jdbc.PreparedStatement;

public class BankRepository {

	PreparedStatement ps;
	Connection conn = Conn.startConnection();
	FileInputStream fis;

	public Result add(Patron p) throws SQLException, FileNotFoundException {
		fis = new FileInputStream(p.image);

		PreparedStatement ps = (PreparedStatement) conn
				.prepareStatement("insert into patron (id,name,image) values(?,?,?)");
		ps.setString(1, null);
		ps.setString(2, p.name);
		ps.setBinaryStream(3, fis, p.image.length());

		int result = ps.executeUpdate();

		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public Result remove(Patron p) throws SQLException, FileNotFoundException {
		ps = (PreparedStatement) conn.prepareStatement("Delete from patron where id= ?");
		ps.setInt(1, 4);

		int result = ps.executeUpdate();

		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public Result update(Patron p) throws SQLException, FileNotFoundException {
		fis = new FileInputStream(p.image);
		ps = (PreparedStatement) conn.prepareStatement("Update patron set name=?,image=? where id= 2");
		ps.setString(1, "HARSHA");
		ps.setBinaryStream(2, fis, p.image.length());

		int result = ps.executeUpdate();

		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public File storeStream(ByteArrayInputStream is) throws IOException {

		File out = new File("C:/Users/Kaustav Dutta/Desktop/Assignments IMCS/Assignments/JDBC/prat.png");
		OutputStream os = new FileOutputStream(out);
		byte[] buffer = new byte[8 * 1024];

		int fileread = 0;
		while ((fileread = is.read(buffer)) != -1) {
			os.write(buffer, 0, fileread);
		}

		return out;

	}

	public Patron findPatron(int id) throws SQLException {
		String selectSQL = "SELECT * FROM patron WHERE id=?";
		ps = (PreparedStatement) conn.prepareStatement(selectSQL);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		Patron person = new Patron();
		while (rs.next() == true) {
			person.id = rs.getInt(1);
			person.name = rs.getString(2);
			ByteArrayInputStream stream = (ByteArrayInputStream) rs.getBinaryStream(3);
			try {
				person.image = storeStream(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (rs.wasNull()) {
			return null;
		} else {
			return person;
		}

	}

	public List<Patron> findPatron(String name) {
		List<Patron> patrons = new ArrayList<Patron>();

		ps = null;
		String remove = "select * from patron where name = ?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Patron patron = new Patron();
				patron.id = rs.getInt("id");
				patron.name = rs.getString("name");
				ByteArrayInputStream stream = (ByteArrayInputStream) rs.getBinaryStream(3);
				try {
					patron.image = storeStream(stream);
				} catch (IOException e) {
					e.printStackTrace();
				}
				patrons.add(patron);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patrons;
	}

	public Result transact(transaction trans) {
		int result = 0;
		ps = null;
		String insert = "insert into transaction (id,amount,type,account_id) values (?,?,?,?)";
		try {
			ps = (PreparedStatement) conn.prepareStatement(insert);
			ps.setInt(1, trans.id);
			ps.setDouble(2, trans.amount);
			ps.setString(3, trans.accountType.toString());
			ps.setInt(4, trans.account.id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public transaction findTransaction(int id) {
		transaction trans = new transaction();
		PreparedStatement ps = null;
		String remove = "select * from transaction where id = ?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				trans.id = rs.getInt("id");
				int acc = rs.getInt("account_id");
				trans.account = this.findAccount(acc);
				trans.amount = rs.getDouble("amount");
				String str = rs.getString("type");
				if (str.equals("debit"))
					trans.accountType = AccountType.DEBIT;
				else
					trans.accountType = AccountType.CREDIT;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trans;
	}

	public Result add(Bank bank) {
		int result = 0;

		PreparedStatement ps = null;
		String insert = "insert into bank (id,name) values (?,?)";
		try {
			ps = (PreparedStatement) conn.prepareStatement(insert);
			ps.setInt(1, bank.id);
			ps.setString(2, bank.name);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public Result remove(Bank bank) {
		int result = 0;

		PreparedStatement ps = null;
		String remove = "delete from bank where id = ?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ps.setInt(1, bank.id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public Result update(Bank bank) {
		int result = 0;
		PreparedStatement ps = null;
		String remove = "update bank set name = ? where id = ?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ps.setString(1, "Rocky");
			ps.setInt(2, bank.id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public Bank findBank(int id) {
		Bank bank = new Bank();

		String remove = "select * from bank where id = ?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bank.id = rs.getInt("id");
				bank.name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bank;
	}

	public List<Bank> findBank(String name) {
		List<Bank> banks = new ArrayList<Bank>();

		PreparedStatement ps = null;
		String remove = "select * from bank where name = ?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Bank bank = new Bank();
				bank.id = rs.getInt("id");
				bank.name = rs.getString("name");
				banks.add(bank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return banks;
	}

	public Result add(Account account) {
		int result = 0;

		PreparedStatement ps = null;
		String insert = "insert into account (id,bank_id,patron_id) values (?,?,?)";
		try {
			ps = (PreparedStatement) conn.prepareStatement(insert);
			ps.setInt(1, account.id);
			ps.setInt(2, account.bank.id);
			ps.setInt(3, account.patron.id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public Result update(Account account) {
		int result = 0;

		PreparedStatement ps = null;
		String remove = "update account set bank_id = ?,patron_id = ? where id = ?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ps.setInt(1, 2);
			ps.setInt(2, 4);
			ps.setInt(3, 2);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public Result delete(Account account) {
		int result = 0;

		PreparedStatement ps = null;
		String remove = "delete from account where id = ?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ps.setInt(1, account.id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result > 0)
			return Result.SUCCESS;
		else
			return Result.FAILURE;

	}

	public Account findAccount(int id) {
		Account account = new Account();

		PreparedStatement ps = null;
		String remove = "select * from account where id = ?";
		try {
			ps = (PreparedStatement) conn.prepareStatement(remove);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				account.id = rs.getInt("id");
				int b_id = rs.getInt("bank_id");
				account.bank = this.findBank(b_id);
				int p_id = rs.getInt("patron_id");
				account.patron = this.findPatron(p_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}
}
