package dbse.fopj.blinktopus.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.api.managers.SVManager;

public class Evaluation {

	private static void loadData() {
		// load data
		String baseDir = "";
		try {
			baseDir = new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogManager.getLogManager().loadData(baseDir + "/src/main/resources/dataset/orders1.csv");
		LogManager.getLogManager().loadData(baseDir + "/src/main/resources/dataset/lineitems1.csv");
	}

	private static void createSV(String table, String attr, double lower, double higher) {
		SVManager.getSVManager().maintain("", "Row", table, attr, lower, higher, true);
		SVManager.getSVManager().maintain("", "Col", table, attr, lower, higher, true);
	}

	private static long[] queryLog(int n, String table, String attr, double lower, double higher) {
		long[] resTime = new long[n];
		for (int i = 0; i < n; ++i) {
			resTime[i] = LogManager.getLogManager().getTime(table, attr, lower, higher, "TEST");
		}
		return resTime;
	}

	private static long[] queryRow(int n, String table, String attr, double lower, double higher) {
		long[] resTime = new long[n];
		for (int i = 0; i < n; ++i) {
			resTime[i] = SVManager.getSVManager().maintain("Row1", "Row", table, attr, lower, higher, false)
					.getTimeSV();
		}
		return resTime;
	}

	private static long[] queryCol(int n, String table, String attr, double lower, double higher) {
		long[] resTime = new long[n];
		for (int i = 0; i < n; ++i) {
			resTime[i] = SVManager.getSVManager().maintain("Col2", "Col", table, attr, lower, higher, false)
					.getTimeSV();
		}
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
		
		String filepath = baseDir+"/evaluation.csv";
		
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
		double lower = 100;
		double higher = 5000;

		loadData();
		createSV(table, attr, lower, higher);
		long[] resLog = queryLog(100, table, attr, lower, higher);
		long[] resRow = queryRow(100, table, attr, lower, higher);
		long[] resCol = queryCol(100, table, attr, lower, higher);
		exportData(resLog, resRow, resCol);
	}
}
