package h2017_squiz_hry1989;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;

public class Nature extends Category {
	private String animalT = "";

	public void setAnimalTypes(String input) {
		this.animalTypes.add(input);
	}

	public ArrayList<String> getAnimalTypes() {
		return animalTypes;
	}

	ArrayList<String> animalTypes = new ArrayList<>();

	public int quizNature() throws JsonGenerationException, IOException {
		switch (randomInt(1, 1)) {
		case 1:
			sparqlLimit = 10;
			rand = randomInt(0, 6115-sparqlLimit);
			randString = Integer.toString(rand);

			// array of types of animals
			setAnimalTypes("Amphibian");
			setAnimalTypes("Arachnid");
			setAnimalTypes("Bird");
			setAnimalTypes("Bird");
			setAnimalTypes("Fish");
			setAnimalTypes("Insect");
			setAnimalTypes("Mammal");
			setAnimalTypes("Reptile");

			ArrayList<Integer> animalLimits = new ArrayList<>();
			animalLimits.add(3587);
			animalLimits.add(4152);
			animalLimits.add(12941);
			animalLimits.add(18752);
			animalLimits.add(133586);
			animalLimits.add(12178);
			animalLimits.add(5545);



			//int aType = randomInt(0, animalTypes.size()-1);
			int aType = randomInt(0, getAnimalTypes().size()-2);
			setAnimalT(animalTypes.get(aType));

			//System.out.println("Test: " + getAnimalT());
			//String chosenAnimalType = animalTypes.get(aType);

			//System.out.println(chosenAnimalType);

			String animalTypeQuery = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
					"\n" +
					"SELECT ?name ?type WHERE {\n" +
					"\t?name rdf:type dbo:" + getAnimalT() + " .\n" +
					"}\n" +
					"OFFSET " + randomInt(0, animalLimits.get(aType))+ "\n" +
					"LIMIT 4";

			List<Map> animalTypeList = sparqlList(animalTypeQuery, dbPediaService, prefix);
			generateQuestion(animalTypeList, "animal type", 1);
			//System.out.println(animalTypeList.toString());
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

		String alt1 = "";

		int max = dataList.size()-1;

		// generates a question for animals: Which type of animal is this?
		// get animaltype by using the getAnimalT()-method.
		//System.out.println("DataList-test: " + cleanUp(dataList.get(0).get("name").toString()));
		if (question.equals("animal type")){
			alt1 = cleanUp(dataList.get(0).get("name").toString());

			// asks the question
			System.out.println("Which type of animal is a " + alt1 + "?\n");
		}


		return 0;
	}

	public void setAnimalT(String animalT) {
		this.animalT = animalT;
	}

	public String getAnimalT() {
		return animalT;
	}

	/*
	/** Prints one of the supported lists. Can be used for hints.
	 * @param requestedList
	 * 				The list that should be printed
	 * @param start
	 * 				The starting index of the list
	 * @param end
	 * 				The ending index of the list
	 * */
	/*public static void printList(String requestedList, int start, int end) {
		if (requestedList == "animal types" && start >= 0 && end < animalTypes.size()){
			for (int i = start; i < end; i++){
				System.out.println(cleanUp(animalTypes.get(i).toString()));

			}
			System.out.println("intertupt");
		}
	}*/
}