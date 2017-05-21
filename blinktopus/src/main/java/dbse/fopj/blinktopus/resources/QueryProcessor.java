package dbse.fopj.blinktopus.resources;

import java.util.HashMap;
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

/**
 * 
 * @author urmikl18 Class that processes queries from user.
 */
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

	/**
	 * 
	 * @return List of stored Storage Views
	 */
	@Path("/sv")
	@GET
	@Timed
	public SVManagerResult getAllSVs() {
		return SVManager.getSVManager().getAllSV();
	}

	/**
	 * 
	 * @return Tuples in the primary log.
	 */
	@Path("/log")
	@GET
	@Timed
	public LogResult getAllLog() {
		return LogManager.getLogManager().getAllLog();
	}

	/**
	 * 
	 * @param SVId
	 *            - ID of an SV
	 * @param type
	 *            - type of an SV (row, col, aqp)
	 * @param table
	 *            - name of a relation
	 * @param attr
	 *            - name of an attribute in a relation
	 * @param lower
	 *            - right border of an interval
	 * @param higher
	 *            - left border of an interval
	 * @param createSV
	 *            - indicates if new sv of type should be created
	 * @return Specified SV with results
	 */
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
