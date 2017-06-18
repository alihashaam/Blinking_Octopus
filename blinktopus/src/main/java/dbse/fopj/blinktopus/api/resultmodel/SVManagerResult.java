package dbse.fopj.blinktopus.api.resultmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dbse.fopj.blinktopus.api.sv.SV;

/**
 * 
 * @author urmikl18 Class represents a JSON-file that is returned after
 *         QueryProcessor asks for all SVs.
 */
public class SVManagerResult extends Result {

	private List<SV> allSV;

	public SVManagerResult() {
	}

	public SVManagerResult(String SVid, String type, String table, String attr, double lower, double higher,
			long timeLog, long timeSV, double exactCount, double apprCount, double error, String message, List<SV> allSV) {
		super(SVid, type, table, attr, lower, higher, timeLog, timeSV, exactCount, apprCount, error, message);
		this.allSV = allSV;
	}

	@JsonProperty
	public List<SV> getAllSV() {
		return allSV;
	}

}
