package com.points.domain;

import java.sql.Timestamp;

public class Record {
	private String name;
	private int points;
	private Timestamp transactionDate;
	
	public Record() {}

	public Record(String name, int points, Timestamp transactionDate) {
		super();
		this.name = name;
		this.points = points;
		this.transactionDate = transactionDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "Record [name=" + name + ", points=" + points + ", transactionDate=" + transactionDate + "]";
	}
	
	
}
