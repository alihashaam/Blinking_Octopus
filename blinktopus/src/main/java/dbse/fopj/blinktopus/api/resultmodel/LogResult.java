package dbse.fopj.blinktopus.api.resultmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dbse.fopj.blinktopus.api.datamodel.Tuple;

public class LogResult extends Result{

	private List<Tuple> logResultTuples;
	private long elapsedTime;
	private int size;

	public LogResult() {
	}

	public LogResult(List<Tuple> resultTuples, long time, int size) {
		this.logResultTuples = resultTuples;
		this.elapsedTime=time;
		this.size=size;
	}

	@JsonProperty
	public List<Tuple> getResultTuples() {
		return logResultTuples;
	}
	
	@JsonProperty
	public long getElapsedTime() {
		return elapsedTime;
	}
	
	@JsonProperty
	public int getSize() {
		return size;
	}
}
