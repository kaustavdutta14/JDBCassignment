package com.jdbc;

public class Bank {
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bank [id=" + id + ", name=" + name + "]";
	}
	public int id;
	public String name;
	public Bank(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Bank() {
		// TODO Auto-generated constructor stub
	}

	
}
