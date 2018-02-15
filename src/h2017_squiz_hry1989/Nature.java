package h2017_squiz_hry1989;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonGenerationException;

public class Nature extends Category implements ICategory {
	private String animalT = "";

	private void setAnimalTypes(String input) {
		this.animalTypes.add(input);
	}

	private ArrayList<String> getAnimalTypes() {
		return animalTypes;
	}

	ArrayList<String> animalTypes = new ArrayList<>();

	@Override
	public int categoryQuiz() throws JsonGenerationException, IOException {
		switch (randomInt(1, 1)) {
		case 1:
			setSparqlLimit(4);
			setRand(randomInt(0, 6115-getSparqlLimit()));
			//randString = Integer.toString(rand);

			// array of types of animals and limits
			ArrayList<Integer> animalLimits = new ArrayList<>();
			setAnimalTypes("Amphibian");
			animalLimits.add(3587);
			setAnimalTypes("Arachnid");
			animalLimits.add(4152);
			setAnimalTypes("Bird");
			animalLimits.add(12941);
			setAnimalTypes("Fish");
			animalLimits.add(18752);
			setAnimalTypes("Insect");
			animalLimits.add(133586);
			setAnimalTypes("Mammal");
			animalLimits.add(12178);
			setAnimalTypes("Reptile");
			animalLimits.add(5545);

			int aType = randomInt(0, getAnimalTypes().size()-2);
			setAnimalT(animalTypes.get(aType));

			String animalTypeQuery = "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
					"\n" +
					"SELECT ?name ?type WHERE {\n" +
					"\t?name rdf:type dbo:" + getAnimalT() + " .\n" +
					"}\n" +
					"OFFSET " + randomInt(0, animalLimits.get(aType))+ "\n" +
					"LIMIT " + getSparqlLimit();

			List<Map> animalTypeList = sparqlList(animalTypeQuery, getDbPediaService(), getPrefix());
			return generateQuestion(animalTypeList, "animal type", 1);

		case 2: 
			System.out.println("Not yet implemented");
			break;

		default:
			System.out.println("Something went wrong");
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
	@Override
	public int generateQuestion(List<Map> dataList, String question, int chosenQ) {
		if (dataList.size() < 2){
			System.out.println("Not enough data to generate question. \nTry something else!");
			return -1;
		}

		String userA;
		String correctNum;

		String alt1;
		int altNum1;

		// generates a question for animals: Which type of animal is this?
		// get animaltype by using the getAnimalT()-method.
		if (question.equals("animal type")){
			// the correct answer
			alt1 = cleanUp(dataList.get(0).get("name").toString());

			// asks the question
			System.out.println("Which type of animal is a " + alt1 + "?\n");

			ArrayList<String> stringAlts = new ArrayList<>();
			stringAlts.add(getAnimalT());

			// removes the correct answer from the remaining alternatives
			if (animalTypes.contains(getAnimalT())){
				animalTypes.remove(getAnimalT());
			}

			altNum1 = randomInt(0, animalTypes.size()-1);

			stringAlts.add(animalTypes.get(altNum1));

			// shuffles the alternatives to create randomness
			Collections.shuffle(stringAlts);

			// prints out the different alternatives
			System.out.println("1. " + stringAlts.get(0) + "\n2. " + stringAlts.get(1));

			// receives and answer
			userA = scanner.nextLine();

			if (getAnimalT().toLowerCase().equals(stringAlts.get(0).toLowerCase())){
				correctNum = "1";
			}
			else {
				correctNum = "2";
			}

			// checks to see if the answer is correct
			if (userA.toLowerCase().equals(getAnimalT().toLowerCase()) || userA.equals(correctNum)){
				System.out.println(generateFeedback("correct"));
				return 1;
			}
			else {
				System.out.println(generateFeedback("wrong"));
				return -1;
			}
		}
		return 0;
	}

	private void setAnimalT(String animalT) {
		this.animalT = animalT;
	}

	private String getAnimalT() {
		return animalT;
	}
}