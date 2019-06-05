package intern.Java;

import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.opencsv.CSVWriter;

/**
 * Create java console application that generates monthly performance reports.
 *
 * @author Penka Yochkova
 * @since 2019-06-05
 */

public class PerformanceReport {
	// A list of all 'Performer'objects. 
	private static List<Performer> allPerf = new ArrayList<Performer>();

	public static void main(String[] args) {
		try {
			JSONParser parser = new JSONParser();

			readJSONData(parser, args);

			ReportDefinition reportDef = readJSONReport(parser, args);
			
			String csvPath = inputPath();
			
			csvWriter(reportDef, csvPath);

			System.out.print("Ready!");

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	//reading of JSON formatted file filled with data
	private static void readJSONData(JSONParser parser, String[] args) throws IOException, ParseException {
		Object obj = parser.parse(new FileReader(args[0]));
		JSONArray array = (JSONArray) obj;

		for (Object object : array) {
			JSONObject person = (JSONObject) object;

			Performer currentPerf = new Performer((String) person.get("name"), (long) person.get("totalSales"),
					(long) person.get("salesPeriod"), (double) person.get("experienceMultiplier"));
			allPerf.add(currentPerf);
		}
	}

	//reading of JSON formatted file that contains the report definition
	private static ReportDefinition readJSONReport(JSONParser parser, String[] args)
			throws IOException, ParseException {
		Object obj = parser.parse(new FileReader(args[1]));
		JSONObject person = (JSONObject) obj;

		ReportDefinition reportDef = new ReportDefinition((long) person.get("topPerformersThreshold"),
				(boolean) person.get("useExprienceMultiplier"), (long) person.get("periodLimit"));
		return reportDef;
	}

	//input and validation of the CSV file path
	private static String inputPath(){
		Scanner sc = new Scanner(System.in);
		String path;
		do {
			System.out.println("Where do you want to be save your CSV report? Please, write a path:");
			path = sc.nextLine();
		} while (path.isEmpty() || !path.contains(".csv") ||!isValidPath(path));
		sc.close();
		return path;
	}
	
	//validation of the CSV file path
	private static boolean isValidPath(String path){
		try {
			Paths.get(path);
		} catch (InvalidPathException | NullPointerException e) {
			System.out.println("Invalid path!");
			return false;
		}
		return true;
	}

	//writing of the CSV document
	private static void csvWriter(ReportDefinition reportDef, String path) throws IOException {
		Writer writer = Files.newBufferedWriter(Paths.get(path));
		try (CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {

			String head = "Name\t\t,Score\n";
			writer.write(head);
			writer.flush();

			Performer perf = new Performer();
			double score = 0.0;

			for (int i = 0; i < allPerf.size(); i++) {
				perf = allPerf.get(i);
				score = calculateScore(reportDef, perf);
				if ((reportDef.getPeriodLimit() >= perf.getSalesPeriod())
						&& (score <= reportDef.getTopPerformersThreshold())) {
					csvWriter.writeNext(new String[] { perf.getName(), Double.toString(score) });
					csvWriter.flush();
				}
			}
		} finally {
			writer.close();
		}
	}

	//calculation of the score
	private static double calculateScore(ReportDefinition reportDef, Performer currentPerformer) {
		double score = currentPerformer.getTotalSales() / currentPerformer.getSalesPeriod();
		if (reportDef.isUseExprienceMultiplier())
			score = score * currentPerformer.getExperienceMultiplier();
		return score;
	}
}
