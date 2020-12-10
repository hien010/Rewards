package com.points.domain;

public class RecordResult {
	private String recName;
	private int recPoint;
	
	public RecordResult() {}
	
	
	public RecordResult(String recName, int recPoint) {
		super();
		this.recName = recName;
		this.recPoint = recPoint;
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


	@Override
	public String toString() {
		return "RecordResult [recName=" + recName + ", recPoint=" + recPoint + "]";
	}
}
