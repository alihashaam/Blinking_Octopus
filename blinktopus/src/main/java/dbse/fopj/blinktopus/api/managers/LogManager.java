package dbse.fopj.blinktopus.api.managers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import dbse.fopj.blinktopus.api.datamodel.*;
import dbse.fopj.blinktopus.api.resultmodel.LogResult;
import dbse.fopj.blinktopus.resources.QueryProcessor;

public final class LogManager {
	private static final LogManager INSTANCE = new LogManager();
	private List<Tuple> dataLog = new ArrayList<Tuple>();

	private LogManager() {
	}

	public static LogManager getLogManager() {
		return INSTANCE;
	}

	// load data from csv at pathname
	public void loadData(String pathname) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		String csvFile = pathname;
		BufferedReader br = null;
		String l = "";
		String csvSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((l = br.readLine()) != null) {
				String[] line = l.split(csvSplitBy);
				if (line.length == 8)
					this.dataLog.add(new Order(Integer.parseInt(line[0].trim()), line[1].trim().charAt(0),
							Double.parseDouble(line[2].trim()), format.parse(line[3].trim()), line[4].trim(),
							line[5].trim(), Integer.parseInt(line[6].trim()), line[7].trim()));
				else if (line.length == 14)
					this.dataLog.add(new LineItem(Long.parseLong(line[0].trim()), Integer.parseInt(line[1].trim()),
							Double.parseDouble(line[2].trim()), Double.parseDouble(line[3].trim()),
							Double.parseDouble(line[4].trim()), Double.parseDouble(line[5].trim()),
							line[6].trim().charAt(0), line[7].trim().charAt(0), format.parse(line[8].trim()),
							format.parse(line[9].trim()), format.parse(line[10].trim()), line[11].trim(),
							line[12].trim(), line[13].trim()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public LogResult getAllLog() {
		long start = System.nanoTime();
		return new LogResult("Primary","Log", "", "", 0, 0, System.nanoTime() - start, this.dataLog.size(), 0, this.dataLog);
	}

	// get results from Log of type, with range [lower,higher] applied to attr
	public LogResult scan(String table, String attr, double lower, double higher) {
		long start = System.nanoTime();
		List<Tuple> res = new ArrayList<Tuple>();

		if (table.toLowerCase().equals("orders")) {
			switch (QueryProcessor.attrIndex.get(attr.toLowerCase())) {
			case 0:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("order")
								&& ((Order) e).getTotalPrice() >= lower && ((Order) e).getTotalPrice() <= higher))
						.collect(Collectors.toList());
				break;
			case 1:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("order")
								&& ((Order) e).getOrderDate().getTime() >= lower
								&& ((Order) e).getOrderDate().getTime() <= higher))
						.collect(Collectors.toList());
				break;
			default:
				res = null;
				break;
			}
		} else {
			switch (QueryProcessor.attrIndex.get(attr.toLowerCase())) {
			case 0:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("lineitem")
								&& ((LineItem) e).getLineNumber() >= lower && ((LineItem) e).getLineNumber() <= higher))
						.collect(Collectors.toList());
				break;
			case 1:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("lineitem")
								&& ((LineItem) e).getQuantity() >= lower && ((LineItem) e).getQuantity() <= higher))
						.collect(Collectors.toList());
				break;
			case 2:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("lineitem")
								&& ((LineItem) e).getExtendedPrice() >= lower
								&& ((LineItem) e).getExtendedPrice() <= higher))
						.collect(Collectors.toList());
				break;
			case 3:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("lineitem")
								&& ((LineItem) e).getDiscount() >= lower && ((LineItem) e).getDiscount() <= higher))
						.collect(Collectors.toList());
				break;
			case 4:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("lineitem")
								&& ((LineItem) e).getTax() >= lower && ((LineItem) e).getTax() <= higher))
						.collect(Collectors.toList());
				break;
			case 5:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("lineitem")
								&& ((LineItem) e).getShipDate().getTime() >= lower
								&& ((LineItem) e).getShipDate().getTime() <= higher))
						.collect(Collectors.toList());
				break;
			case 6:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("lineitem")
								&& ((LineItem) e).getCommitDate().getTime() >= lower
								&& ((LineItem) e).getCommitDate().getTime() <= higher))
						.collect(Collectors.toList());
				break;
			case 7:
				res = this.dataLog.stream()
						.filter((Tuple e) -> (e.getType().toLowerCase().equals("lineitem")
								&& ((LineItem) e).getReceiptDate().getTime() >= lower
								&& ((LineItem) e).getReceiptDate().getTime() <= higher))
						.collect(Collectors.toList());
				break;
			default:
				res = null;
				break;
			}
		}
		return new LogResult("Primary","Log", table, attr, lower, higher, System.nanoTime() - start, res.size(), 0, res);

	}

}
