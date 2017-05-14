package dbse.fopj.blinktopus.api.resultmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dbse.fopj.blinktopus.api.datamodel.Tuple;

public class LogResult extends Result{

	private List<Tuple> logResultTuples;

	public LogResult() {
	}

	public LogResult(List<Tuple> resultTuples) {
		this.logResultTuples = resultTuples;
	}

	@JsonProperty
	public List<Tuple> getResultTuples() {
		return logResultTuples;
	}
}
