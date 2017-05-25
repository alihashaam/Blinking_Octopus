package dbse.fopj.blinktopus.api.resultmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author urmikl18 Represents JSON-file as a result of query processing.
 */
public abstract class Result {
	private String SVid;
	private String type;
	private String table;
	private String attr;
	private double lower;
	private double higher;
	private long timeLog;
	private long timeSV;
	private int size;
	private double error;
	private String message;

	public Result() {

	}

	public Result(String SVid, String type, String table, String attr, double lower, double higher, long timeLog,
			long timeSV, int size, double error, String message) {
		this.SVid = SVid;
		this.type = type;
		this.table = table;
		this.attr = attr;
		this.lower = lower;
		this.higher = higher;
		this.timeLog = timeLog;
		this.timeSV = timeSV;
		this.size = size;
		this.error = error;
		this.message = message;
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
	public long getTimeLog() {
		return timeLog;
	}

	@JsonProperty
	public long getTimeSV() {
		return timeSV;
	}

	@JsonProperty
	public int getSize() {
		return size;
	}

	@JsonProperty
	public double getError() {
		return error;
	}

	@JsonProperty
	public String getMessage() {
		return message;
	}
}
