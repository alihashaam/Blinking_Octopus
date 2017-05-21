package dbse.fopj.blinktopus.api.resultmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dbse.fopj.blinktopus.api.sv.SV;

public class SVManagerResult extends Result {

	private List<SV> allSV;

	public SVManagerResult() {
	}

	public SVManagerResult(String SVid, String type, String table, String attr, double lower, double higher, long elapsedTime,
			int size, double error, List<SV> allSV) {
		super(SVid, type, table, attr, lower, higher, elapsedTime, size, error);
		this.allSV = allSV;
	}

	@JsonProperty
	public List<SV> getAllSV() {
		return allSV;
	}

}
