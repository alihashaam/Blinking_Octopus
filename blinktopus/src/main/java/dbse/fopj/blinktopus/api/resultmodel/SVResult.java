package dbse.fopj.blinktopus.api.resultmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import dbse.fopj.blinktopus.api.sv.*;

/**
 * 
 * @author urmikl18 Class represents a JSON that should be created as a result
 *         of query processing on SV.
 */
public class SVResult extends Result {

	private SV svResult;

	public SVResult() {
	}

	public SVResult(String SVid, String type, String table, String attr, double lower, double higher, long timeLog,
			long timeSV, int size, double error, String message, SV svResult) {
		super(SVid, type, table, attr, lower, higher, timeLog, timeSV, size, error, message);
		this.svResult = svResult;
	}

	@JsonProperty
	public SV getSVResult() {
		return svResult;
	}
}
