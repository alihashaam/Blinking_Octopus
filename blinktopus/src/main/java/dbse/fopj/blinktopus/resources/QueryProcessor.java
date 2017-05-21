package dbse.fopj.blinktopus.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.api.managers.SVManager;
import dbse.fopj.blinktopus.api.resultmodel.LogResult;
import dbse.fopj.blinktopus.api.resultmodel.Result;
import dbse.fopj.blinktopus.api.resultmodel.SVManagerResult;
import dbse.fopj.blinktopus.api.sv.*;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class QueryProcessor {

	public final static HashMap<String, Integer> attrIndex = new HashMap<String, Integer>() {

		private static final long serialVersionUID = 1L;

		{
			put("totalprice", 0);
			put("orderdate", 1);

			put("linenumber", 0);
			put("quantity", 1);
			put("extendedprice", 2);
			put("discount", 3);
			put("tax", 4);
			put("shipdate", 5);
			put("commitdate", 6);
			put("receiptdate", 7);
		}
	};

	public QueryProcessor() {

	}

	@Path("/sv")
	@GET
	@Timed
	public SVManagerResult getAllSVs() {
		return SVManager.getSVManager().getAllSV();
	}

	@Path("/log")
	@GET
	@Timed
	public LogResult getAllLog() {
		return LogManager.getLogManager().getAllLog();
	}

	@Path("/query")
	@GET
	@Timed
	public Result query(@QueryParam("SVid") String SVId, @QueryParam("type") String type,
			@QueryParam("table") String table, @QueryParam("attr") String attr, @QueryParam("lower") String lower,
			@QueryParam("higher") String higher, @QueryParam("create") String createSV) {
		if (type.toLowerCase().equals("log"))
			return LogManager.getLogManager().scan(table, attr, Double.parseDouble(lower), Double.parseDouble(higher));
		else
			return SVManager.getSVManager().maintain(SVId, type, table, attr, Double.parseDouble(lower),
					Double.parseDouble(higher), Boolean.parseBoolean(createSV));
	}

}
