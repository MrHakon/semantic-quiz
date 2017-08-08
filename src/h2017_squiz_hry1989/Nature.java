package h2017_squiz_hry1989;

import java.io.IOException;
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
			
			String mammalQuery = "SELECT ?name WHERE {" + 
					"?name rdf:type umbel-rc:Mammal ." + 
					"}" + 
					"OFFSET " + randString + 
					" LIMIT " + sparqlLimit;
			List<Map> mammalList = sparqlList(mammalQuery, dbPediaService, prefix);
			
			System.out.println(mammalList.toString());
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
}