package h2017_squiz_hry1989;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;

public class Nature extends Category {
	
	public int quizNature() throws JsonGenerationException, IOException {
		switch (randomInt(1, 1)) {
		case 1:
			sparqlLimit = 10;
			rand = randomInt(0, 6115-sparqlLimit);
			randString = Integer.toString(rand);

			// array of types of animals
			ArrayList<String> animalTypes = new ArrayList<>();
			animalTypes.add("Amphibian");
			animalTypes.add("Arachnid");
			animalTypes.add("Bird");
			animalTypes.add("Fish");
			animalTypes.add("Insect");
			animalTypes.add("Mammal");
			animalTypes.add("Reptile");

			ArrayList<Integer> animalLimits = new ArrayList<>();
			animalLimits.add(3587);
			animalLimits.add(4152);
			animalLimits.add(12941);
			animalLimits.add(18752);
			animalLimits.add(133586);
			animalLimits.add(12178);
			animalLimits.add(5545);

			int aType = randomInt(0, animalTypes.size()-1);
			String chosenAnimalType = animalTypes.get(aType);

			System.out.println(chosenAnimalType);

			String animalTypeQuery = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
					"\n" +
					"SELECT ?name ?type WHERE {\n" +
					"\t?name rdf:type dbo:" + chosenAnimalType + " .\n" +
					"}\n" +
					"OFFSET " + randomInt(0, animalLimits.get(aType))+ "\n" +
					"LIMIT 4";

			List<Map> animalTypeList = sparqlList(animalTypeQuery, dbPediaService, prefix);
			System.out.println(animalTypeList.toString());
			/*
			// returns a list of mammals
			String mammalQuery = "SELECT ?name WHERE {" + 
					"?name rdf:type dbo:Mammal . "
					/*"?name rdf:type umbel-rc:Mammal ."*/ //+
					/*"}" +
					"OFFSET " + randString + 
					" LIMIT " + sparqlLimit;
			List<Map> mammalList = sparqlList(mammalQuery, dbPediaService, prefix);

			System.out.println(mammalList.toString());*/
			break;
		case 2: 
			System.out.println("Not yet implemented");
			break;

		default:
			System.out.println("");
			break;
		}
		return 0;
	}

	/**
	 * Method that generates a question and receives the user's answer
	 *
	 * @param dataList
	 *            The list of data, from which the question is generated
	 * @param question
	 *            A variable that is used to choose the right category for the
	 *            question
	 * @param chosenQ
	 *            An integer that chooses the question to generate (can be fixed or
	 *            random)
	 *
	 * @return An int, depending on the user's answer: correct = 1, wrong = 0, error =-1, if
	 * hints are used and the answer is correct: 2
	 */
	public static int generateQuestion(List<Map> dataList, String question, int chosenQ) {
		if (dataList.size() < 2){
			System.out.println("Not enough data to generate question. \nTry something else!");
			return -1;
		}

		String userA = "";
		String correctA = "";
		String correctNum = "";

		int max = dataList.size()-1;

		// generates a question for animals: Which type of animal is this?


		return 0;
	}
}