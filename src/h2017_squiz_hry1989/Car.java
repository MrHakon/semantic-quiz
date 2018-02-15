package h2017_squiz_hry1989;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;

public class Car extends Category implements ICategory {

	@Override
	public int categoryQuiz() throws JsonGenerationException, IOException {
		setSparqlLimit(500);
		setRand(randomInt(0, 500 - getSparqlLimit()));

		String carQuery = "SELECT ?brand ?name ?productionStart WHERE {" + "?car rdf:type dbo:Automobile ."
				+ "?car rdfs:label ?name ." + "?car dbo:manufacturer ?brand ."
				+ "?car dbo:productionStartYear ?productionStart ." + "FILTER (lang(?name) = 'en') ."
				+ "FILTER (?productionStart > 0) . }" + "OFFSET " + getRand() + "LIMIT " + getSparqlLimit();

		List<Map> carList = sparqlList(carQuery, getDbPediaService(), getPrefix());

		if (generateQuestion(carList, "car", 1) == 1) {
			return 1;
		} else {
			return 0;
		}

	}

	@Override
	public int generateQuestion(List<Map> dataList, String question, int chosenQ) {
		if (dataList.size() < 2) {
			System.out.println("Not enough data to generate question.\nTry again");
			return 0;
		}

		String alt0 = "";

		int alt0Int = 0;
		int alt1Int = 0;
		int alt2Int = 0;

		int max = dataList.size() - 1;
		int[] randNums = distinctNums(4, max);

		if (question.equals("car")) {
			switch (chosenQ) {
			case 1:
				alt0 = cleanUp(dataList.get(randNums[0]).get("name").toString());
				System.out.println("Which year was the " + alt0 + " first manufactured?");

				int correctInt = cleanUpInt(dataList.get(randNums[0]).get("productionStart").toString());

				List<Integer> randomList = new ArrayList<>();
				randomList.add(randNums[0]);
				randomList.add(randNums[1]);
				randomList.add(randNums[2]);
				Collections.shuffle(randomList);

				alt0Int = cleanUpInt(dataList.get(randomList.get(0)).get("productionStart").toString());
				alt1Int = cleanUpInt(dataList.get(randomList.get(1)).get("productionStart").toString());
				alt2Int = cleanUpInt(dataList.get(randomList.get(2)).get("productionStart").toString());

				System.out.println("\n1. " + alt0Int + "\n2. " + alt1Int + "\n3. " + alt2Int);

				int userChoice = scanner.nextInt();
				int userA = 0;

				if (userChoice == 1) {
					userA = alt0Int;
				} else if (userChoice == 2) {
					userA = alt1Int;
				} else if (userChoice == 3) {
					userA = alt2Int;
				}

				if (userA == correctInt) {
					System.out.println("It was indeed manufactured in " + correctInt);
					System.out.println(generateFeedback("correct"));
					return 1;
				} else {
					System.out.println("It was manufactured in " + correctInt);
					System.out.println(generateFeedback("wrong"));
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
