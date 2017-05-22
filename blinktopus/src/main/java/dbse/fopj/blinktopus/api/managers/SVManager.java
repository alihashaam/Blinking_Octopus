package dbse.fopj.blinktopus.api.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
		return new SVManagerResult("SV", "Manager", "", "", 0, 0, System.nanoTime() - start, allSV.size(), 0, allSV);
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
	public SVResult maintain(String SVId, String type, String table, String attr, double lower, double higher,
			boolean createSV) {
		if (createSV) {
			if (type.toLowerCase().equals("row")) {
				String rowId = "Row" + (idSV++);
				long start = System.nanoTime();
				RowSV res = new RowSV(rowId, table, attr, lower, higher);
				this.allSV.add(res);
				return new SVResult(rowId, type, table, attr, lower, higher, System.nanoTime() - start, res.getSize(),
						0, res);
			} else if (type.toLowerCase().equals("col")) {
				String colId = "Col" + (idSV++);
				long start = System.nanoTime();
				ColSV res = new ColSV(colId, table, attr, lower, higher);
				this.allSV.add(res);
				return new SVResult(colId, type, table, attr, lower, higher, System.nanoTime() - start, res.getSize(),
						0, res);
			} else {
				String aqpId = "Aqp" + (idSV++);
				long start = System.nanoTime();
				// AqpSV res = new AqpSV(aqpId, table, attr, lower, higher);
				AqpSV res = null;
				this.allSV.add(res);
				return new SVResult(aqpId, type, table, attr, lower, higher, System.nanoTime() - start, res.getSize(),
						0, res);
			}
		} else {
			SV sv = new SV();
			try {
				sv = this.allSV.stream().filter((SV r) -> r.getId().toLowerCase().equals(SVId.toLowerCase()))
						.collect(Collectors.toList()).get(0);

			} catch (IndexOutOfBoundsException e) {
				return maintain(SVId, type, table, attr, lower, higher, true);
			}
			if (sv == null)
				return maintain(SVId, type, table, attr, lower, higher, true);

			if (sv.getType().toLowerCase().equals("row")) {
				RowSV rowSV = (RowSV) sv;
				return rowSV.query(table, attr, lower, higher);
			} else if (sv.getType().toLowerCase().equals("col")) {
				ColSV colSV = (ColSV) sv;
				return colSV.query(table, attr, lower, higher);
			} else {
				return null;
			}
		}
	}
}
