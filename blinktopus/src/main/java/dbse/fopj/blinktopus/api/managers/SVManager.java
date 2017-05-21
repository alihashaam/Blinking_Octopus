package dbse.fopj.blinktopus.api.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dbse.fopj.blinktopus.api.resultmodel.SVManagerResult;
import dbse.fopj.blinktopus.api.resultmodel.SVResult;
import dbse.fopj.blinktopus.api.sv.*;

public final class SVManager {
	private List<SV> allSV = new ArrayList<SV>();
	private static final SVManager INSTANCE = new SVManager();
	private int idSV = 1;

	private SVManager() {
	}

	public static SVManager getSVManager() {
		return INSTANCE;
	}

	public SVManagerResult getAllSV() {
		long start = System.nanoTime();
		return new SVManagerResult("SV", "Manager", "", "", 0, 0, System.nanoTime() - start, allSV.size(), 0, allSV);
	}

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
				return null;
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
			}
		}
		return null;
	}
}
