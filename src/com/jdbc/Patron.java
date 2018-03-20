package com.jdbc;

import java.io.File;

public class Patron {
	public int id;
	public String name;
	public File image;
	
	public Patron(int id, String name, String image) {
		super();
		this.id = id;
		this.name = name;
		this.image = new File(image);
	}

	public Patron() {
		
	}

	@Override
	public String toString() {
		return "Patron [id=" + id + ", name=" + name + ", image=" + image + "]";
	}
	
}
