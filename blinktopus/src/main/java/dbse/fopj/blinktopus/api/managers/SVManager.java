package dbse.fopj.blinktopus.api.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dbse.fopj.blinktopus.api.resultmodel.Result;
import dbse.fopj.blinktopus.api.resultmodel.SVManagerResult;
import dbse.fopj.blinktopus.api.resultmodel.SVResult;
import dbse.fopj.blinktopus.api.sv.*;

/**
 * 
 * @author urmikl18 Class that manages list of SVs. Singleton.
 */
public final class SVManager {
	private List<SV> allSV = new ArrayList<SV>();
	private static final SVManager INSTANCE = new SVManager();
	private int idSV = 1;

	private SVManager() {
	}

	public static SVManager getSVManager() {
		return INSTANCE;
	}

	/**
	 * 
	 * @return List of currently stored SVs
	 */
	public SVManagerResult getAllSV() {
		long start = System.nanoTime();
		return new SVManagerResult("SV", "Manager", "", "", 0, 0, 0, System.nanoTime() - start, allSV.size(),0, 0, "OK",
				allSV);
	}
	
	public void clear()
	{
		this.allSV.clear();
		this.idSV=1;
	}

	/**
	 * 
	 * @param SVId
	 *            - ID of used SV
	 * @param type
	 *            - type of SV
	 * @param table
	 *            - name of a relation
	 * @param attr
	 *            - name of an attribute
	 * @param lower
	 *            - left border of an interval
	 * @param higher
	 *            - right border of an interval
	 * @param createSV
	 *            - indicates whether new SV should be created
	 * @return SV with results. Work flow:\n 1. Checks if new SV must be
	 *         created:\n 1.1.YES - Create new SV of specified type and store
	 *         it. \n 1.2.NO - Try to find SV with given ID. \n 1.2.1 Found -
	 *         Call method that creates temporary view on this SV. 1.2.2 Not
	 *         found - Create new SV with given parameters.
	 * 
	 */
	public Result maintain(String SVId, String type, String table, String attr, double lower, double higher,
			boolean createSV) {
		if (createSV) {
			if (type.toLowerCase().equals("row")) {
				String rowId = "Row" + (idSV++);
				RowSV res = new RowSV(rowId, table, attr, lower, higher);
				this.allSV.add(res);
				long timeSV = res.getTime();
				return new SVResult(rowId, type, table, attr, lower, higher, 0, timeSV, res.getSize(),0, 0, "OK",
						res);
			} else if (type.toLowerCase().equals("col")) {
				String colId = "Col" + (idSV++);
				ColSV res = new ColSV(colId, table, attr, lower, higher);
				this.allSV.add(res);
				long timeSV = res.getTime();
				return new SVResult(colId, type, table, attr, lower, higher, 0, timeSV, res.getSize(),0, 0, "OK",
						res);
			} else {
				String aqpId = "AQP";
				AqpSV res = new AqpSV();
				this.allSV.add(res);
								
				long exactCount = LogManager.getLogManager().getCount(table, attr, lower, higher, false, "Exact count");
				long apprCount = res.query(table, attr, lower, higher);
				return new SVResult(aqpId, type, table, attr, lower, higher, 0, res.getTime(),
						exactCount, apprCount, apprCount - exactCount, "OK", res);			
			}
		} else {
			SV sv = new SV();
			try {
				sv = this.allSV.stream().filter((SV r) -> r.getId().toLowerCase().equals(SVId.toLowerCase()))
						.collect(Collectors.toList()).get(0);

			} catch (IndexOutOfBoundsException e) {
				return LogManager.getLogManager().scan(table, attr, lower, higher, "SV with Id: "+SVId+" doesn't exist");
			}
			if (sv == null)
				return LogManager.getLogManager().scan(table, attr, lower, higher, "SV with Id: "+SVId+" doesn't exist");
			if (!sv.getType().toLowerCase().equals(type.toLowerCase()))
				return LogManager.getLogManager().scan(table, attr, lower, higher, "SV with Id: "+SVId+" doesn't have type: "+type);

			if (sv.getType().toLowerCase().equals("row")) {
				RowSV rowSV = (RowSV) sv;
				return rowSV.query(table, attr, lower, higher);
			} else if (sv.getType().toLowerCase().equals("col")) {
				ColSV colSV = (ColSV) sv;
				return colSV.query(table, attr, lower, higher);
			} else {
				AqpSV aqpSV = (AqpSV) sv;
				long startTime = System.nanoTime();
				long exactCount = LogManager.getLogManager().getCount(table, attr, lower, higher, false, "Exact count");
				long logTime = System.nanoTime()-startTime;
				long apprCount = aqpSV.query(table, attr, lower, higher);
				return new SVResult(SVId, type, table, attr, lower, higher, logTime, aqpSV.getTime(),
						exactCount, apprCount, apprCount - exactCount, "OK", aqpSV);
			}
		}
	}
}
