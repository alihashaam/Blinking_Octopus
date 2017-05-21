package dbse.fopj.blinktopus.api.sv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.annotation.JsonProperty;

import dbse.fopj.blinktopus.api.datamodel.LineItem;
import dbse.fopj.blinktopus.api.datamodel.Order;
import dbse.fopj.blinktopus.api.datamodel.Tuple;
import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.api.managers.SVManager;
import dbse.fopj.blinktopus.api.resultmodel.SVResult;
import dbse.fopj.blinktopus.resources.QueryProcessor;

public class RowSV extends SV {
	public class RowEntry {
		private int id;
		private Tuple value;
		private int pos;

		public RowEntry() {
		}

		public RowEntry(int id, Tuple value, int pos) {
			this.id = id;
			this.value = value;
			this.pos = pos;
		}

		@JsonProperty
		public int getId() {
			return id;
		}

		@JsonProperty
		public Tuple getValue() {
			return value;
		}

		@JsonProperty
		public int getPosition() {
			return pos;
		}
	}

	private List<RowEntry> rowData;

	public RowSV() {
	}

	public RowSV(String id, String table, String attr, double lower, double higher) {
		super(id, "Row", table, attr, lower, higher);
		List<Tuple> all = LogManager.getLogManager().getAllLog().getResultTuples();
		List<Tuple> lr = LogManager.getLogManager().scan(table, attr, lower, higher).getResultTuples();
		this.rowData = IntStream.range(0, lr.size())
				.mapToObj(i -> new RowEntry(i + 1, lr.get(i), all.indexOf(lr.get(i)) + 1)).collect(Collectors.toList());
		this.setSize(rowData.size());
	}

	public RowSV(String id, String table, String attr, double lower, double higher, List<RowEntry> rowData) {
		super(id, "Row", table, attr, lower, higher);
		this.rowData = rowData;
		this.setSize(rowData.size());
	}

	@JsonProperty
	public List<RowEntry> getRowData() {
		return rowData;
	}

	public SVResult query(String table, String attr, double lower, double higher) {
		long start = System.nanoTime();
		if (this.getTable().equals(table) && this.getAttr().equals(attr) && this.getLower() <= lower
				&& this.getHigher() >= higher) {
			System.out.println("!!!!!!!!!!CRACK!!!!!!!!!!!!!!!!!");
			List<RowEntry> res = new ArrayList<RowEntry>();
			if (table.toLowerCase().equals("orders")) {
				switch (QueryProcessor.attrIndex.get(attr.toLowerCase())) {
				case 0:
					res = this.rowData.stream().filter((RowEntry e) -> ((Order) e.getValue()).getTotalPrice() >= lower
							&& ((Order) e.getValue()).getTotalPrice() <= higher).collect(Collectors.toList());
					break;
				case 1:
					res = this.rowData.stream()
							.filter((RowEntry e) -> ((Order) e.getValue()).getOrderDate().getTime() >= lower
									&& ((Order) e.getValue()).getOrderDate().getTime() <= higher)
							.collect(Collectors.toList());
					break;
				default:
					res = null;
					break;
				}
			} else {
				switch (QueryProcessor.attrIndex.get(attr.toLowerCase())) {
				case 0:
					res = this.rowData.stream()
							.filter((RowEntry e) -> ((LineItem) e.getValue()).getLineNumber() >= lower
									&& ((LineItem) e.getValue()).getLineNumber() <= higher)
							.collect(Collectors.toList());
					break;
				case 1:
					res = this.rowData.stream().filter((RowEntry e) -> ((LineItem) e.getValue()).getQuantity() >= lower
							&& ((LineItem) e.getValue()).getQuantity() <= higher).collect(Collectors.toList());
					break;
				case 2:
					res = this.rowData.stream()
							.filter((RowEntry e) -> ((LineItem) e.getValue()).getExtendedPrice() >= lower
									&& ((LineItem) e.getValue()).getExtendedPrice() <= higher)
							.collect(Collectors.toList());
					break;
				case 3:
					res = this.rowData.stream().filter((RowEntry e) -> ((LineItem) e.getValue()).getDiscount() >= lower
							&& ((LineItem) e.getValue()).getDiscount() <= higher).collect(Collectors.toList());
					break;
				case 4:
					res = this.rowData.stream().filter((RowEntry e) -> ((LineItem) e.getValue()).getTax() >= lower
							&& ((LineItem) e.getValue()).getTax() <= higher).collect(Collectors.toList());
					break;
				case 5:
					res = this.rowData.stream()
							.filter((RowEntry e) -> ((LineItem) e.getValue()).getShipDate().getTime() >= lower
									&& ((LineItem) e.getValue()).getShipDate().getTime() <= higher)
							.collect(Collectors.toList());
					break;
				case 6:
					res = this.rowData.stream()
							.filter((RowEntry e) -> ((LineItem) e.getValue()).getCommitDate().getTime() >= lower
									&& ((LineItem) e.getValue()).getCommitDate().getTime() <= higher)
							.collect(Collectors.toList());
					break;
				case 7:
					res = this.rowData.stream()
							.filter((RowEntry e) -> ((LineItem) e.getValue()).getReceiptDate().getTime() >= lower
									&& ((LineItem) e.getValue()).getReceiptDate().getTime() <= higher)
							.collect(Collectors.toList());
					break;
				default:
					res = null;
					break;
				}
			}
			return new SVResult(this.getId() + "tmp", "Row", table, attr, lower, higher, System.nanoTime() - start,
					res.size(), 0, new RowSV(this.getId() + "tmp", table, attr, lower, higher, res));

		} else {
			return SVManager.getSVManager().maintain(this.getId(), "Row", table, attr, lower, higher, true);
		}

	}

}
