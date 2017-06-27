package dbse.fopj.blinktopus.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import dbse.fopj.blinktopus.api.*;
import dbse.fopj.blinktopus.api.datamodel.LineItem;
import dbse.fopj.blinktopus.api.datamodel.Order;
import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.api.managers.SVManager;
import dbse.fopj.blinktopus.api.resultmodel.Result;
import dbse.fopj.blinktopus.api.sv.AqpSV;
import dbse.fopj.blinktopus.api.sv.ColSV;
import dbse.fopj.blinktopus.api.sv.RowSV;

public class Evaluation {
	
	private static void loadData(String O, String LI) {
		LogManager.getLogManager().loadData("/home/urmikl18/Рабочий стол/dataset/"+O,
				"/home/urmikl18/Рабочий стол/dataset/"+LI);
	}
	
	private static double[] experiment1(ColSV col, RowSV row, String table, String attr, double lower, double higher, int nQuery)
	{
		ArrayList<Long> logTimes = new ArrayList<>();
		ArrayList<Long> colTimes = new ArrayList<>();
		ArrayList<Long> rowTimes = new ArrayList<>();
		
		for (int i=0;i<nQuery;++i)
		{
			logTimes.add(LogManager.getLogManager().getTime(table, attr, lower, higher, "Exp1"));
		}
		
		for (int i=0;i<nQuery;++i)
		{
			colTimes.add(col.query(table, attr, lower, higher).getTimeSV());
		}
		
		for (int i=0;i<nQuery;++i)
		{
			rowTimes.add(col.query(table, attr, lower, higher).getTimeSV());
		}
		
		double averageLog = logTimes.stream().mapToLong(Long::longValue).sum() / nQuery;
		double averageCol = colTimes.stream().mapToLong(Long::longValue).sum() / nQuery;
		double averageRow = rowTimes.stream().mapToLong(Long::longValue).sum() / nQuery;
		
		double[] res = {averageLog, averageCol, averageRow, 0};
		return res;
	}

	private static final String HEADER = "log, row, col";
	private static final String LINEBREAK = "\n";
	private static final String COMMA = ",";

	private static void exportData(ArrayList<double[]> res) {
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

			for (int i = 0; i < res.size(); ++i) {
				fileWriter.append(String.valueOf(res.get(i)[0]));
				fileWriter.append(COMMA);
				fileWriter.append(String.valueOf(res.get(i)[1]));
				fileWriter.append(COMMA);
				fileWriter.append(String.valueOf(res.get(i)[2]));
				fileWriter.append(COMMA);
				fileWriter.append(String.valueOf(res.get(i)[3]));
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
		String O_1 = "O_0.1.tbl";
		String LI_1 = "LI_0.1.tbl";
		
		String O_2 = "O_0.5.tbl";
		String LI_2 = "LI_0.5.tbl";
		
		String O_3 = "O_1.tbl";
		String LI_3 = "LI_1.tbl";
		
		String O_4 = "O_10.tbl";
		String LI_4 = "LI_10.tbl";
		
		String table = "orders";
		String attr = "totalprice";
		
		double lower1_10 = 800;
		double higher1_10 = 36611;
		
		double lower1_90 = 800;
		double higher1_90 = 257744;
		
		double lower2_10 = 800;
		double higher2_10 = 37512;
		
		double lower2_90 = 800;
		double higher2_90 = 264458;
		
		double lower3_10 = 800;
		double higher3_10 = 38769;
		
		double lower3_90 = 800;
		double higher3_90 = 273702;
		
		double lower4_10 = 800;
		double higher4_10 = 40000;
		
		double lower4_90 = 800;
		double higher4_90 = 280000;
		
		int nQuery = 100;
		
		//Experiment 1
		System.out.println("Start Experiment 1");
		System.out.println("Start SF1");
		loadData(O_1,LI_1);
		ColSV col = new ColSV("Col1", table, attr, lower1_10, higher1_10);
		RowSV row = new RowSV("Row2", table, attr, lower1_10, higher1_10);
		double[] res1 = experiment1(col, row, table, attr, lower1_10, higher1_10, nQuery);
		
		col = new ColSV("Col1", table, attr, lower1_90, higher1_90);
		row = new RowSV("Row2", table, attr, lower1_90, higher1_90);
		double[] res2 = experiment1(col, row, table, attr, lower1_90, higher1_90, nQuery);
		
		System.out.println("Start SF2");
		loadData(O_2,LI_2);
		col = new ColSV("Col1", table, attr, lower2_10, higher2_10);
		row = new RowSV("Row2", table, attr, lower2_10, higher2_10);
		double[] res3 = experiment1(col, row, table, attr, lower2_10, higher2_10, nQuery);
		col = new ColSV("Col1", table, attr, lower2_90, higher2_90);
		row = new RowSV("Row2", table, attr, lower2_90, higher2_90);
		double[] res4 = experiment1(col, row, table, attr, lower2_90, higher2_90, nQuery);
		
		ArrayList<double[]> res =new ArrayList<>();
		res.add(res1);
		res.add(res2);
		res.add(res3);
		res.add(res4);
		
		exportData(res);
		
		//Experiment 2
		
		//Experiment 3
	}
}
