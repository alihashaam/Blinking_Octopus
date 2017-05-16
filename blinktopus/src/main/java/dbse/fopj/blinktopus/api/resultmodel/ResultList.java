package dbse.fopj.blinktopus.api.resultmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultList {
	private List<Result> results;

	public ResultList() {
	}

	public ResultList(List<Result> results) {
		this.results = results;
	}

	@JsonProperty
	public List<Result> getResults() {
		return results;
	}
}
