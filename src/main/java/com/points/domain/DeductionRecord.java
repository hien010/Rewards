package com.points.domain;

public class DeductionRecord {
	private String recName;
	private int recPoint;
	private String now;
	
	public DeductionRecord() {}
	
	public DeductionRecord(String recName, int recPoint, String now) {
		super();
		this.recName = recName;
		this.recPoint = recPoint;
		this.now = now;
	}
	
	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public int getRecPoint() {
		return recPoint;
	}

	public void setRecPoint(int recPoint) {
		this.recPoint = recPoint;
	}

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	@Override
	public String toString() {
		return "DeductionRecord [recName=" + recName + ", recPoint=" + recPoint + ", now=" + now + "]";
	}
}
