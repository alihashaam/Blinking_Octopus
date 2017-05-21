package dbse.fopj.blinktopus.api.resultmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
	private String SVid;
	private String type;
	private String table;
	private String attr;
	private double lower;
	private double higher;
	private long elapsedTime;
	private int size;
	private double error;

	public Result() {

	}

	public Result(String SVid, String type, String table, String attr, double lower, double higher, long elapsedTime,
			int size, double error) {
		this.SVid = SVid;
		this.type = type;
		this.table = table;
		this.attr = attr;
		this.lower = lower;
		this.higher = higher;
		this.elapsedTime = elapsedTime;
		this.size = size;
		this.error = error;
	}

	@JsonProperty
	public String getSVid() {
		return SVid;
	}

	@JsonProperty
	public String getType() {
		return type;
	}

	@JsonProperty
	public String getTable() {
		return table;
	}

	@JsonProperty
	public String getAttr() {
		return attr;
	}

	@JsonProperty
	public double getLower() {
		return lower;
	}

	@JsonProperty
	public double getHigher() {
		return higher;
	}

	@JsonProperty
	public long getElapsedTime() {
		return elapsedTime;
	}

	@JsonProperty
	public int getSize() {
		return size;
	}

	@JsonProperty
	public double getError() {
		return error;
	}
}
