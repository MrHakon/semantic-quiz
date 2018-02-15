package h2017_squiz_hry1989;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;

public interface ICategory {
	
	
	/**
	 * Sets up the required settings to initialize a quiz. Can choose between
	 * several different question types, if they are implemented.
	 * */
	public int categoryQuiz() throws JsonGenerationException, IOException;
	
	/**
	 * Generates a question and receives the user's answer
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
	public int generateQuestion(List<Map> dataList, String question, int chosenQ);
}
