package dbse.fopj.blinktopus.api.sv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.annotation.JsonProperty;

import dbse.fopj.blinktopus.api.datamodel.LineItem;
import dbse.fopj.blinktopus.api.datamodel.Order;
import dbse.fopj.blinktopus.api.datamodel.Tuple;
import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.api.resultmodel.LogResult;
import dbse.fopj.blinktopus.api.resultmodel.Result;
import dbse.fopj.blinktopus.api.resultmodel.SVResult;
import dbse.fopj.blinktopus.resources.QueryProcessor;

/**
 * 
 * @author urmikl18 Class represents column-oriented db.
 */
public class ColSV extends SV {
	class ColEntry {
		private double value;
		private int pos;

		public ColEntry() {
		}

		public ColEntry(double value, int pos) {
			this.value = value;
			this.pos = pos;
		}

		@JsonProperty
		public double getValue() {
			return value;
		}

		@JsonProperty
		public int getPos() {
			return pos;
		}
	}

	private List<ColEntry> colData;

	public ColSV() {
	}

	public ColSV(String id, String table, String attr, double lower, double higher) {
		super(id, "Col", table, attr, lower, higher);
		List<Tuple> all = LogManager.getLogManager().getAllLog().getResultTuples();
		LogResult res = LogManager.getLogManager().scan(table, attr, lower, higher, "");
		List<Tuple> lr = res.getResultTuples();
		if (table.toLowerCase().equals("orders")) {
			switch (QueryProcessor.attrIndex.get(attr.toLowerCase())) {
			case 0:
				this.colData = IntStream.range(0, lr.size())
						.mapToObj(i -> new ColEntry(((Order) lr.get(i)).getTotalPrice(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			case 1:
				this.colData = IntStream.range(0, lr.size())
						.mapToObj(
								i -> new ColEntry(((Order) lr.get(i)).getOrderDate().getTime(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			default:
				this.colData = null;
				break;
			}
		} else {
			switch (QueryProcessor.attrIndex.get(attr.toLowerCase())) {
			case 0:
				this.colData = IntStream.range(0, lr.size())
						.mapToObj(i -> new ColEntry(((LineItem) lr.get(i)).getLineNumber(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			case 1:
				this.colData = IntStream.range(0, lr.size())
						.mapToObj(i -> new ColEntry(((LineItem) lr.get(i)).getQuantity(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			case 2:
				this.colData = IntStream.range(0, lr.size())
						.mapToObj(i -> new ColEntry(((LineItem) lr.get(i)).getExtendedPrice(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			case 3:
				this.colData = IntStream.range(0, lr.size())
						.mapToObj(i -> new ColEntry(((LineItem) lr.get(i)).getDiscount(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			case 4:
				this.colData = IntStream.range(0, lr.size())
						.mapToObj(i -> new ColEntry(((LineItem) lr.get(i)).getTax(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			case 5:
				this.colData = IntStream.range(0, lr.size()).mapToObj(
						i -> new ColEntry(((LineItem) lr.get(i)).getShipDate().getTime(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			case 6:
				this.colData = IntStream.range(0, lr.size()).mapToObj(
						i -> new ColEntry(((LineItem) lr.get(i)).getCommitDate().getTime(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			case 7:
				this.colData = IntStream.range(0, lr.size()).mapToObj(
						i -> new ColEntry(((LineItem) lr.get(i)).getReceiptDate().getTime(), all.indexOf(lr.get(i))))
						.collect(Collectors.toList());
				break;
			default:
				this.colData = null;
				break;
			}
		}
		this.setTime(res.getTimeLog());
		this.setSize(colData.size());
	}

	private ColSV(String id, String table, String attr, double lower, double higher, List<ColEntry> colData) {
		super(id, "Col", table, attr, lower, higher);
		this.colData = colData;
		this.setSize(colData.size());
	}

	@JsonProperty
	public List<ColEntry> getColData() {
		return colData;
	}

	/**
	 * 
	 * @param table
	 *            - name of a relation to query on
	 * @param attr
	 *            - name of an attribute to query on
	 * @param lower
	 *            - left border of an interval
	 * @param higher
	 *            - right border of an interval
	 * @return SVResult with relevant information Works as follows: 1. Check if
	 *         table name and attribute name are of this SV, check if range is a
	 *         subrange of current one:\n 1.1 YES - create temporary RowSV and
	 *         return it as a result. 1.2 NO - create new RowSV and store it in
	 *         SVManager
	 */
	public Result query(String table, String attr, double lower, double higher) {
		List<ColEntry> res = new ArrayList<ColEntry>();
		if (this.getTable().toLowerCase().equals(table.toLowerCase())
				&& this.getAttr().toLowerCase().equals(attr.toLowerCase()) && this.getLower() <= lower
				&& this.getHigher() >= higher) {
			long start = System.nanoTime();
			res = this.colData.stream().filter((ColEntry e) -> (e.getValue() >= lower && e.getValue() <= higher))
					.collect(Collectors.toList());
			long timeSV = System.nanoTime() - start;
			long timeLog = LogManager.getLogManager().getTime(table, attr, lower, higher, "");
			return new SVResult(this.getId() + "tmp", "Col", table, attr, lower, higher, timeLog, timeSV, res.size(), 0,
					0, "OK", new ColSV(this.getId() + "tmp", table, attr, lower, higher, res));
		} else {
			if (!this.getTable().toLowerCase().equals(table.toLowerCase()))
				return LogManager.getLogManager().scan(table, attr, lower, higher,
						"SV with Id: " + this.getId() + " is not for the table: " + table);
			else if (!this.getAttr().toLowerCase().equals(attr.toLowerCase()))
				return LogManager.getLogManager().scan(table, attr, lower, higher,
						"SV with Id: " + this.getId() + " is not for attribute: " + attr);
			else
				return LogManager.getLogManager().scan(table, attr, lower, higher, "Random error");
		}
	}

	public long getCount(String table, String attr, double lower, double higher, boolean distinct, String message) {
		if (this.getTable().toLowerCase().equals(table.toLowerCase())
				&& this.getAttr().toLowerCase().equals(attr.toLowerCase()) && this.getLower() <= lower
				&& this.getHigher() >= higher) {
			if (!distinct) {
				return this.query(table, attr, lower, higher).getExactCount();
			} else {
				HashSet<Double> hs = new HashSet<>();
				for (ColEntry v : this.colData)
				{
					double val = v.getValue();
					if (val>=lower && val<=higher)
						hs.add(v.getValue());
						
				}
				return hs.size();
			}
		} else
			return LogManager.getLogManager().getCount(table, attr, lower, higher, distinct, message);
	}
}
