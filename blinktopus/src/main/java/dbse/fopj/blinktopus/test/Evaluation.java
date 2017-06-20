package dbse.fopj.blinktopus.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.api.managers.SVManager;
import dbse.fopj.blinktopus.api.resultmodel.Result;
import dbse.fopj.blinktopus.api.sv.AqpSV;
import dbse.fopj.blinktopus.api.sv.ColSV;
import dbse.fopj.blinktopus.api.sv.RowSV;

public class Evaluation {
	private static void loadData() {
		// load data
		String baseDir = "";
		try {
			baseDir = new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LogManager.getLogManager().loadData(baseDir + "/src/main/resources/dataset/O_0.1.tbl",
				baseDir + "/src/main/resources/dataset/LI_0.1.tbl");
	}

	private static void createSV(String table, String attr, double lower, double higher) {
		SVManager.getSVManager().maintain("", "Row", table, attr, lower, higher, true);
		SVManager.getSVManager().maintain("", "Col", table, attr, lower, higher, true);
	}

	private static long[] queryLog(int n, String table, String attr, double lower, double higher) {
		Result res = null;
		long[] resTime = new long[n];
		for (int i = 0; i < n; ++i) {
			res = LogManager.getLogManager().scan(table, attr, lower, higher, "TEST");
			resTime[i] = res.getTimeLog();
		}
		res = null;
		return resTime;
	}

	private static long[] queryRow(int n, String table, String attr, double lower, double higher) {
		Result res = null;
		long[] resTime = new long[n];
		for (int i = 0; i < n; ++i) {
			res = SVManager.getSVManager().maintain("Row1", "Row", table, attr, lower, higher, false);
			resTime[i] = res.getTimeSV();
		}
		res = null;
		return resTime;
	}

	private static long[] queryCol(int n, String table, String attr, double lower, double higher) {
		Result res = null;
		long[] resTime = new long[n];
		for (int i = 0; i < n; ++i) {
			res = SVManager.getSVManager().maintain("Col2", "Col", table, attr, lower, higher, false);
			resTime[i] = res.getTimeSV();
		}
		res = null;
		return resTime;
	}

	private static final String HEADER = "log, row, col";
	private static final String LINEBREAK = "\n";
	private static final String COMMA = ",";

	private static void exportData(long[] log, long[] row, long[] col) {
		FileWriter fileWriter = null;

		String baseDir = "";
		try {
			baseDir = new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String filepath = baseDir + "/evaluation.csv";

		try {
			fileWriter = new FileWriter(filepath);

			fileWriter.append(HEADER);
			fileWriter.append(LINEBREAK);

			for (int i = 0; i < log.length; ++i) {
				fileWriter.append(String.valueOf(log[i]));
				fileWriter.append(COMMA);
				fileWriter.append(String.valueOf(row[i]));
				fileWriter.append(COMMA);
				fileWriter.append(String.valueOf(col[i]));
				fileWriter.append(LINEBREAK);
			}

			System.out.println("CSV was created successfully");
		} catch (Exception e) {
			System.out.println("Error in CSV");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		String table = "orders";
		String attr = "totalPrice";
		double lower = 50000.0;
		double higher = 200000.0;
		int nQuery = 100;

		// !!! LOG-SV TEST !!!
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//		System.out.println(sdf.format(cal.getTime()));
//
		loadData();
//		createSV(table, attr, lower, higher);
//		long[] resLog = queryLog(nQuery, table, attr, lower, higher);
//		long[] resRow = queryRow(nQuery, table, attr, lower, higher);
//		long[] resCol = queryCol(nQuery, table, attr, lower, higher);
//		exportData(resLog, resRow, resCol);
//		System.out.println(sdf.format(cal.getTime()));

		// !!! COUNT TEST !!!
		 RowSV row = new RowSV("Row1", table, attr, lower, higher);
		 ColSV col = new ColSV("Col2", table, attr, lower, higher);
		 AqpSV aqp = new AqpSV();
		 double countLog = LogManager.getLogManager().getCount(table, attr,
		 lower, higher, false, "count");
		 double distCountLog = LogManager.getLogManager().getCount(table,
		 attr, lower, higher, true, "count");
		 double countRow = row.getCount(table, attr, lower, higher, false,
		 "count");
		 double distCountRow = row.getCount(table, attr, lower, higher, true,
		 "count");
		 double countCol = col.getCount(table, attr, lower, higher, false,
		 "count");
		 double distCountCol = col.getCount(table, attr, lower, higher, true,
		 "count");
		 double countAqp = aqp.query(table, attr, lower, higher);
		 
		
		 System.out.println("SV\tNormal\tDistinct");
		 System.out.println("Log\t"+countLog+"\t"+distCountLog);
		 System.out.println("Row\t"+countRow+"\t"+distCountRow);
		 System.out.println("Col\t"+countCol+"\t"+distCountCol);
		 System.out.println("AQP\t"+countAqp+"\t"+0);
	}
}
