package h2017_squiz_hry1989;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonGenerationException;

public class MainClass {

	public static void main(String[] args) throws JsonGenerationException, IOException {
		// variables to keep track of the user's choice and score
		int userChoice = 0; 
		int numOfQuestions = 0;
		//int totalQuestions = 0;
		int counter = 0;
		int sum = 0;
		int score = 0;
		int pointsystem = 0;
		
		// random number generator to mix up the questions
		Random random = new Random();
		
		// variable to keep the quiz going
		boolean donePlaying = false;
		
		// creating a scanner for user input
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Welcome to quiz!\nHow many questions do you want?");
		counter = scanner.nextInt();
		sum = counter / 2;
		
		// picks a random category and runs as long as needed
		while (!donePlaying) {
			userChoice = random.nextInt(3) + 1;
			int result = 0;
			
			switch (2) {
			case 1:
				Geography geographyObj = new Geography();
				result = geographyObj.quizGeography();
				
				if (result == 1) {
					score = score + 1;
					pointsystem = pointsystem + 10;
				}
				else if (result == 2) {
					score = score + 1;
					pointsystem = pointsystem + 5;
				}
				numOfQuestions = numOfQuestions + 1;
				System.out.println("You have answered " + score + " out of " + numOfQuestions + " questions correctly");
				counter = counter - 1 ;
				break;
				
			case 2:
				System.out.println("Category two: Nature");
				Nature natureObj = new Nature();
				result = natureObj.quizNature();
				
				// insert obj.quiz-method here
				if (result == 1) {
					score = score + 1;
					pointsystem = pointsystem + 10;
				}
				else if (result == 2) {
					score = score + 1;
					pointsystem = pointsystem + 5;
				}
				numOfQuestions = numOfQuestions + 1;
				System.out.println("You have answered " + score + " out of " + numOfQuestions + " questions correctly");
				counter = counter - 1 ;
				break;
				
			case 3:
				System.out.println("Category three");
				result = 0;
				// insert obj.quiz-method here
				if (result == 1) {
					score = score + 1;
				}
				numOfQuestions = numOfQuestions + 1;
				System.out.println("You have answered " + score + " out of " + numOfQuestions + " questions correctly");
				counter = counter - 1 ;
				break;

			default:
				System.out.println("Something went horribly wrong!");
				break;
			}
			
			if (counter == 0) {
				donePlaying = true;
				System.out.println("\nThe quiz is over!");
				System.out.println("You got a total of " + pointsystem + " points out of " + numOfQuestions*10 + " possible points!");
				if (score > sum) {
					System.out.println("Well done!");
				}
				else {
					System.out.println("You should try again!");
				}
			}
		}
		scanner.close();
	}

}
