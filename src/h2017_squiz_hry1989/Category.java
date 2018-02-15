package h2017_squiz_hry1989;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import com.fasterxml.jackson.core.JsonGenerationException;

abstract public class Category {
	static Scanner scanner = new Scanner(System.in);
	
	// sparql-endpoints to run queries against
	private String dbPediaService = "http://dbpedia.org/sparql";
	
	// specifically prefix for dbpedia
	private String prefix = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
			+ "PREFIX : <http://dbpedia.org/resource/>" + "PREFIX dbpedia2: <http://dbpedia.org/property/>"
			+ "PREFIX dbpedia: <http://dbpedia.org/>" + "PREFIX dbo: <http://dbpedia.org/ontology/>"
			+ "PREFIX dbp: <http://dbpedia.org/property/>" + "PREFIX dbc: <http://dbpedia.org/resource/Category:>" 
			+ "PREFIX dct: <http://purl.org/dc/terms/>" + "PREFIX dbr: <http://dbpedia.org/resource/>"
			+ "PREFIX umbel-rc: <http://umbel.org/umbel/rc/>";
	
	boolean answer = false;
	int actualAnswer, userAnswer;
	static Random randomGen = new Random();
	
	private int sparqlLimit = 10;
	
	public int getSparqlLimit() {
		return sparqlLimit;
	}

	public void setSparqlLimit(int sparqlLimit) {
		this.sparqlLimit = sparqlLimit;
	}

	public int getRand() {
		return rand;
	}

	public void setRand(int rand) {
		this.rand = rand;
	}

	private int rand = 0;
	//String sparqlLimString = "0";
	//String randString = "0";
	
	/**
	 * @return Generates a random number with inclusive min and max values
	 * @param min
	 *            The lowest value that is included
	 * @param max
	 *            The greatest value that is included
	 */
	public static int randomInt(int min, int max) {
		max = max + 1;
		int randomInt = randomGen.nextInt(max - min) + min;
		return randomInt;
	}
	
	/** Takes a SPARQL-query in the form of a String, and returns the resultset in a map.
	 * 	The data can be reached by writing *nameOfListVariable*.get(index of the resource).get("propertyToGet").toString();
	 * 
	 * @param queryString A sparql-query in form of a String
	 * @param service The service/endpoint to run the query against
	 * @param prefix The prefix required by the SPARQL-query to run properly
	 * 
	 * @return jsonList A map filled with the resultset from the query
	 * */
	public static List<Map> sparqlList(String queryString, String service, String prefix)
			throws JsonGenerationException, IOException {
		Query query = QueryFactory.create(prefix + queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service, query);

		List<Map> jsonList = new Vector<Map>();
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				Iterator<String> varNames = soln.varNames();
				Map<String, Object> jsonMap = new HashMap<String, Object>();

				while (varNames.hasNext()) {
					String varName = varNames.next();
					jsonMap.put(varName, soln.get(varName).toString());
				}
				jsonList.add(jsonMap);
			}
		} finally {
			qexec.close();
		}
		return jsonList;
	}
	
	/** Cleans up a string returned from sparql-results
	 * */
	public static String cleanUp(String toClean){
		String firstStep = toClean.replace("http://dbpedia.org/resource/", "");
		String secondStep = firstStep.replace("_", " ");
		String cleanString = secondStep.replace("@en", "");
		return cleanString;
	}
	
	/** Clean up an int from sparql-results */
	public static int cleanUpInt(String toClean){
		String firstAttempt = "";
		int closeToClean = 0;
		
		if (toClean.contains("www.w3.org/2001/XMLSchema#integer")){
			firstAttempt = toClean.replace("^^http://www.w3.org/2001/XMLSchema#integer", "");
			closeToClean = Integer.parseInt(firstAttempt.replaceAll("[\\D]", ""));
		}
		else if (toClean.contains("http://www.w3.org/2001/XMLSchema#nonNegativeInteger")){
			firstAttempt = toClean.replace("^^http://www.w3.org/2001/XMLSchema#nonNegativeInteger", "");
			closeToClean = Integer.parseInt(firstAttempt.replaceAll("[\\D]", ""));
		}
		else if (toClean.contains("http://www.w3.org/2001/XMLSchema#gYear")){
			firstAttempt = toClean.replace("^^http://www.w3.org/2001/XMLSchema#gYear", "");
			closeToClean = Integer.parseInt(firstAttempt.replaceAll("[\\D]", ""));
		}
		return closeToClean;
	}
	
	public static double cleanUpDouble(String toClean){
		String firstAttempt = "";
		double closeToClean = 0;
		
		if (toClean.contains("http://www.w3.org/2001/XMLSchema#double")){
			firstAttempt = toClean.replace("^^http://www.w3.org/2001/XMLSchema#double", "");
			closeToClean = Double.parseDouble(firstAttempt);
		}
		return closeToClean;
	}
	
	/** Method that creates random, but distinct, numbers
	 * 
	 * @param size The number of integers in the returned array
	 * @param max The Maximum number of possible values 
	 * 
	 * @return randNums Array of four unique numbers*/
	public static int[] distinctNums(int size, int max){
		int[] randNums = new int[size];
		List<Integer> randNumList = new ArrayList<>();
		
		for (int i = 0; i < max; i++){
			randNumList.add(i);
		}
		
		Collections.shuffle(randNumList);
		
		for (int j = 0; j < 4; j++){
			randNums[j] = randNumList.get(j);
		}
		return randNums;
	}
	
	/** Gives random feedback to the user for variation, based on answer (correct/wrong)
	 * @param state If the user answer right or wrong
	 *  
	 * @return feedback A string with a message to the user */
	public static String generateFeedback(String state){
		String feedback = "";
		int rand = randomInt(0, 5);
		
		// sjekk om den returnerer riktig
		if (state.equals("correct")){
			switch (rand) {
			case 1:
				feedback = "That's right. Good job.";
				break;
			
			case 2:
				feedback = "Correct.";
				break;
				
			case 3:
				feedback = "Well done. Almost too easy";
				break;
				
			case 4:
				feedback = "Great!";
				break;
				
			case 5:
				feedback = "Fantastic. Keep it up";
				break;
				
			default:
				feedback = "Excellent!";
				break;
			}
		}
		else if (state.equals("wrong")){
			switch (rand) {
			case 1:
				feedback = "That's just not right.";
				break;
				
			case 2:
				feedback = "Sorry, but that's incorrect.";
				break;
				
			case 3:
				feedback = "Not quite right, but don't give up.";
				break;
				
			case 4:
				feedback = "Close, but wrong.";
				break;
				
			case 5:
				feedback = "Not even close.";
				break;
				
			default:
				feedback = "Not great. Try again please.";
				break;
					
			}
		}
		return feedback + "\n";
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDbPediaService() {
		return dbPediaService;
	}

	public void setDbPediaService(String dbPediaService) {
		this.dbPediaService = dbPediaService;
	}
}
