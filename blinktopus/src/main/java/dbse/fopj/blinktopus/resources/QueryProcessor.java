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
 * Resource class that parses user's query and redirects it either to LogManager or SVManager.
 * @author Pavlo Shevchenko (urmikl18)
 *
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class QueryProcessor {

	/**
	 * Map that transforms attribute names into numbers. Used to simplify query processing.
	 */
	public final static HashMap<String, Integer> attrIndex=new HashMap<String,Integer>(){

	private static final long serialVersionUID=1L;

	{put("totalprice",0);put("orderdate",1);

	put("linenumber",0);put("quantity",1);put("extendedprice",2);put("discount",3);put("tax",4);put("shipdate",5);put("commitdate",6);put("receiptdate",7);}};

	/**
	 * Default constructor.
	 */
	public QueryProcessor() {

	}

	/**
	 * 
	 * @return List of existing Storage Views , stored in SVManager.
	 */
	@Path("/sv/all")
	@GET
	@Timed
	public SVManagerResult getAllSVs() {
		return SVManager.getSVManager().getAllSV();
	}

	/**
	 * Deletes all stored Storage Views.
	 */
	@Path("/sv/clear")
	@GET
	@Timed
	public void removeAllSVs() {
		SVManager.getSVManager().clear();
	}

	/**
	 * 
	 * @return Returns all the data currently stored in the primary log.
	 */
	@Path("/log")
	@GET
	@Timed
	public LogResult getAllLog() {
		return LogManager.getLogManager().getAllLog();
	}

	/**
	 * The method that returns relevant tuples and relevant information about the query to the user.
	 * @param SVId The ID of a Storage View to be used to answer user's query.
	 * @param type The type of a Storage View to be used to answer user's query (Col,Row,AQP).
	 * @param table The table to be queried (Order/LineItem)
	 * @param attr The attribute to be queried on (e.g. totalprice/extendedprice)
	 * @param lower The lower boundary of a range query.
	 * @param higher The higher boundary of a range query.
	 * @param createSV True, if new SV of given type should be created, false, otherwise.
	 * @param distinct True, if a number of unique values in given range should be returned, false otherwise.
	 * @return An instance of a class Result that contains information about the query (table, attr, lower, higher),
	 * information about SV (Id, Type (Col,Row,AQP)), information about result (tuples, size), and
	 * analytical information (time it took to retrieve the result, and error if necessary).
	 */
	@Path("/query")
	@GET
	@Timed
	public Result query(@QueryParam("SVid") String SVId, @QueryParam("type") String type,
			@QueryParam("table") String table, @QueryParam("attr") String attr, @QueryParam("lower") String lower,
			@QueryParam("higher") String higher, @QueryParam("create") String createSV, @QueryParam("distinct") String distinct) {
		if (type.toLowerCase().equals("log"))
			return LogManager.getLogManager().scan(table, attr, Double.parseDouble(lower), Double.parseDouble(higher),"OK");
		else
		{
			if (type.toLowerCase().equals("aqp"))
				return SVManager.getSVManager().maintain(SVId, type, table, attr, Double.parseDouble(lower),
					Double.parseDouble(higher), false, Boolean.parseBoolean(distinct));
			else
				return SVManager.getSVManager().maintain(SVId, type, table, attr, Double.parseDouble(lower),
						Double.parseDouble(higher), Boolean.parseBoolean(createSV), Boolean.parseBoolean(distinct));
		}
	}

}
