package h2017_squiz_hry1989;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;

public class Geography extends Category implements ICategory {
	
	@Override
	public int categoryQuiz() throws JsonGenerationException, IOException {
		switch (randomInt(1, 2)) {
		case 1:
			// the number of results return by the query
			setSparqlLimit(10);
			// random number that adds offset to the resultset
			setRand(randomInt(0, 82 - getSparqlLimit()));

			String countryQuestion = "SELECT ?name ?popDensity ?popEst WHERE {"
					+ "?country dct:subject dbc:Countries_in_Europe ." + "?country rdfs:label ?name ."
					+ "?country dbo:populationDensity ?popDensity ." + "?country dbo:populationTotal ?popEst ."
					+ "FILTER (lang(?name) = 'en')}" + "OFFSET " + getRand() + "LIMIT 20";

			// returns a JSON-object with the results from the SPARQL-query
			List<Map> countryList = sparqlList(countryQuestion, getDbPediaService(), getPrefix());

			return generateQuestion(countryList, "country", randomInt(1, 2));

		case 2:
			// the number of results returned from the query
			setSparqlLimit(10);
			// random number to add random offset to the resultset
			setRand(randomInt(0, 675 - getSparqlLimit()));

			String cityQ = "SELECT ?name ?population WHERE { " + "?city dbo:country dbr:Norway ."
					+ "?city rdfs:label ?name ." + "?city dbo:populationTotal ?population ."
					+ "FILTER (lang(?name) = 'en')." + "FILTER (?population > 0) .} OFFSET " + getRand() + " LIMIT "
					+ getSparqlLimit();

			List<Map> cityList = sparqlList(cityQ, getDbPediaService(), getPrefix());

			if (generateQuestion(cityList, "city", 1) == 1) {
				return 1;
			} else {
				return 0;
			}

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
	@Override
	public int generateQuestion(List<Map> dataList, String question, int chosenQ) {
		// checks if the datalist has more than one element
		if (dataList.size() < 2) {
			System.out.println("Not enough data to generate question.\nTry something else!");
			return -1;
		}

		String userA = "";
		String correctA = "";
		String correctNum = "";

		// variables to store different alternatives
		String alt0 = "";
		String alt1 = "";
		String alt2 = "";
		String alt3 = "";

		int alt0Int = 0;
		int alt1Int = 0;
		int alt2Int = 0;
		int alt3Int = 0;

		int alt0Population = 0;
		int alt1Population = 0;

		int max = dataList.size() - 1;
		int[] randNums = distinctNums(4, max);

		// generates questions for country-queries
		if (question.equals("country")) {
			switch (chosenQ) {
			case 1:
				// prepares the question variables
				alt0 = cleanUp(dataList.get(randNums[0]).get("name").toString());
				alt1 = cleanUp(dataList.get(randNums[1]).get("name").toString());

				// asks the question
				System.out.println("Which country has the greatest population?\n\n1. " + alt0 + "\n2. " + alt1);

				// receives an answer from the user
				userA = scanner.nextLine();

				// converts strings to ints, for comparison
				alt0Population = cleanUpInt(dataList.get(randNums[0]).get("popEst").toString());
				alt1Population = cleanUpInt(dataList.get(randNums[1]).get("popEst").toString());

				// compares to find the correct answer
				if (alt0Population > alt1Population) {
					correctA = alt0;
					correctNum = "1";
				} else if (alt0Population == alt1Population) {
					correctA = "0";
					correctNum = "0";
				} else {
					correctA = alt1;
					correctNum = "2";
				}

				// checks if the user answered correctly
				if (userA.toLowerCase().equals(correctA.toLowerCase()) || userA.equals(correctNum)) {
					System.out.println(generateFeedback("correct"));
					return 1;
				} else if (correctNum == "0") {
					System.out.println("Turns out there's no correct answer");
					return -1;
				} else {
					System.out.println(generateFeedback("wrong"));
					return 0;
				}

			case 2:
				// prepares the question variables
				if (cleanUpDouble(dataList.get(randNums[0]).get("popDensity").toString()) <= 0
						|| cleanUpDouble(dataList.get(randNums[1]).get("popDensity").toString()) <= 0) {
					generateQuestion(dataList, "country", 3);
					break;
				}
				alt0 = cleanUp(dataList.get(randNums[0]).get("name").toString());
				alt1 = cleanUp(dataList.get(randNums[1]).get("name").toString());

				// asks the question
				System.out.println(
						"Which european country has the highest population density? \n\n1. " + alt0 + "\n2. " + alt1);

				// recieves an answer
				userA = scanner.nextLine();

				// converts strings to ints, for comparison
				double alt0Comp = cleanUpDouble(dataList.get(randNums[0]).get("popDensity").toString());
				double alt1Comp = cleanUpDouble(dataList.get(randNums[1]).get("popDensity").toString());

				// compares to find the correct answer
				if (alt0Comp > alt1Comp) {
					correctA = alt0;
					correctNum = "1";
				} else {
					correctA = alt1;
					correctNum = "2";
				}

				// check if the user answered correctly
				if (userA.toLowerCase().equals(correctA.toLowerCase()) || userA.equals(correctNum)) {
					System.out.println(generateFeedback("correct"));
					System.out.println(alt0 + " has a population density of " + alt0Comp + "\nWhile " + alt1
							+ " has a population density of " + alt1Comp + "\n\n");
					return 1;
				} else {
					System.out.println(generateFeedback("wrong"));
					return 0;
				}

			default:
				System.out.println("Couldn't generate a question for you");
				break;
			}
		} else if (question.equals("city")) {
			switch (chosenQ) {
			case 1:
				boolean hint = false;
				alt0 = cleanUp(dataList.get(randNums[0]).get("name").toString());
				alt1 = cleanUp(dataList.get(randNums[1]).get("name").toString());
				alt2 = cleanUp(dataList.get(randNums[2]).get("name").toString());
				alt3 = cleanUp(dataList.get(randNums[3]).get("name").toString());

				System.out.println("Which village or area has the highest population?");

				System.out.println("\n1. " + alt0 + "\n2. " + alt1 + "\n3. " + alt2 + "\n4. " + alt3 + "\n\n");
				System.out.println("You can also press 9 for a hint");

				userA = scanner.nextLine();

				alt0Int = cleanUpInt(dataList.get(randNums[0]).get("population").toString());
				alt1Int = cleanUpInt(dataList.get(randNums[1]).get("population").toString());
				alt2Int = cleanUpInt(dataList.get(randNums[2]).get("population").toString());
				alt3Int = cleanUpInt(dataList.get(randNums[3]).get("population").toString());

				int highestPop = alt0Int;
				if (alt1Int > highestPop) {
					highestPop = alt1Int;
				}
				if (alt2Int > highestPop) {
					highestPop = alt2Int;
				}
				if (alt3Int > highestPop) {
					highestPop = alt3Int;
				}

				int userChoice = 0;
				if (userA.equals("1") || userA.equals(alt0)) {
					userChoice = alt0Int;
				} else if (userA.equals("2") || userA.equals(alt1)) {
					userChoice = alt1Int;
				} else if (userA.equals("3") || userA.equals(alt2)) {
					userChoice = alt2Int;
				} else if (userA.equals("4") || userA.equals(alt3)) {
					userChoice = alt3Int;
				} else if (userA.equals("9")) {
					hint = true;
					System.out.println("The population in ascending order: ");
					int[] orderByPopulation = new int[] {
							cleanUpInt(dataList.get(randNums[0]).get("population").toString()),
							cleanUpInt(dataList.get(randNums[1]).get("population").toString()),
							cleanUpInt(dataList.get(randNums[2]).get("population").toString()),
							cleanUpInt(dataList.get(randNums[3]).get("population").toString()) };
					Arrays.sort(orderByPopulation);
					for (int i = 0; i < orderByPopulation.length; i++) {
						System.out.println(orderByPopulation[i]);
					}

					userA = scanner.nextLine();
					if (userA.equals("1") || userA.equals(alt0)) {
						userChoice = alt0Int;
					} else if (userA.equals("2") || userA.equals(alt1)) {
						userChoice = alt1Int;
					} else if (userA.equals("3") || userA.equals(alt2)) {
						userChoice = alt2Int;
					} else if (userA.equals("4") || userA.equals(alt3)) {
						userChoice = alt3Int;
					}
				}

				if (userChoice == highestPop) {
					System.out.println(generateFeedback("correct"));
					System.out.println("The answer was: ");
					System.out.println(alt0 + ": " + alt0Int);
					System.out.println(alt1 + ": " + alt1Int);
					System.out.println(alt2 + ": " + alt2Int);
					System.out.println(alt3 + ": " + alt3Int);
					if (hint) {
						return 2;
					}
					return 1;
				} else {
					System.out.println(generateFeedback("wrong"));
					System.out.println("The actual answer is: ");
					System.out.println(alt0 + ": " + alt0Int);
					System.out.println(alt1 + ": " + alt1Int);
					System.out.println(alt2 + ": " + alt2Int);
					System.out.println(alt3 + ": " + alt3Int + "\n");

					return 0;
				}

			default:
				System.out.println("Something went wrong");
				break;
			}

		}
		return 0;
	}

}
