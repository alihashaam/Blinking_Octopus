package dbse.fopj.blinktopus.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.api.resultmodel.LogResult;
import dbse.fopj.blinktopus.api.resultmodel.Result;
import dbse.fopj.blinktopus.api.sv.*;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class QueryProcessor {

	public QueryProcessor() {

	}

	@Path("/sv")
	@GET
	@Timed
	public List<SV> getAllSVs() {
		return null;
	}

	@Path("/log")
	@GET
	@Timed
	public LogResult getAllLog() {
		return LogManager.getLogManager().scan(null, null, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	@Path("/query")
	@GET
	@Timed
	public Result query(@QueryParam("SVid") String SVId, @QueryParam("type") String type,
			@QueryParam("attr") String attr, @QueryParam("lower") double lower, @QueryParam("higher") double higher,
			@QueryParam("create") boolean createSVOrNot) {
		
		return LogManager.getLogManager().scan(type, attr, lower, higher);
	}

}
