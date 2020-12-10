package com.points.domain;

import java.sql.Timestamp;

public class LinkRecord {
	private int id;
	private int points;
	private Timestamp ts;
	
	public LinkRecord() {
		
	}

	public LinkRecord(int id, int points, Timestamp ts) {
		super();
		this.id = id;
		this.points = points;
		this.ts = ts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	@Override
	public String toString() {
		return "LinkRecord [id=" + id + ", points=" + points + ", ts=" + ts + "]";
	}
	
	
}
