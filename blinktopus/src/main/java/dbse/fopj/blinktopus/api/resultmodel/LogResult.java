package dbse.fopj.blinktopus.api.resultmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dbse.fopj.blinktopus.api.datamodel.Tuple;

/**
 * 
 * @author urmikl18 Represents JSON-file when QueryProcessor asks for all
 *         entries in a log.
 */
public class LogResult extends Result {

	private List<Tuple> logResultTuples;

	public LogResult() {
	}

	public LogResult(String SVid, String type, String table, String attr, double lower, double higher, long timeLog,
			long timeSV, long exactCount, long apprCount, double error, String message, List<Tuple> resultTuples) {
		super(SVid, type, table, attr, lower, higher, timeLog, timeSV, exactCount, apprCount, error, message);
		this.logResultTuples = resultTuples;
	}

	@JsonProperty
	public List<Tuple> getResultTuples() {
		return logResultTuples;
	}
}
