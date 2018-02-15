package h2017_squiz_hry1989;

import java.util.Random;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerationException;

public class QuizInitializer {
	private int score = 0;
	private int pointsystem = 0;
	private int numOfQuestions;
	
	public void startQuiz(int counter) throws JsonGenerationException, IOException {
		boolean donePlaying = false;
		if (counter <= 0) {
			System.out.println("You need at least one question to create a quiz!");
			donePlaying = true;
		}
		
		int userChoice = 0;
		int totalQuestions = counter;
		Random random = new Random();
		
		while (!donePlaying) {
			userChoice = random.nextInt(2)+1;
			
			switch (userChoice) {
			case 1:
				Geography geoObj = new Geography();
				pointSystem(geoObj.categoryQuiz());
				numOfQuestions++;
				System.out.println(printScore(numOfQuestions));
				counter--;
				break;
				
			case 2:
				Nature natureObj = new Nature();
				pointSystem(natureObj.categoryQuiz());
				numOfQuestions++;
				System.out.println(printScore(numOfQuestions));
				counter--;
				break;
			
			case 3:
				Car carObj = new Car();
				pointSystem(carObj.categoryQuiz());
				numOfQuestions++;
				System.out.println(printScore(numOfQuestions));
				counter--;
				break;

			default:
				System.out.println("Something went wrong!");
				break;
			}
			
			if (counter == 0) {
				donePlaying = true;
				
				System.out.println("The quiz is over!\nYou got a total of " 
				+ this.getPointsystem() + " points out of " + numOfQuestions*10 + " possible points!");
				
				if (this.getScore() > (totalQuestions/2)) {
					System.out.println("Well done!");
				}
				else {
					System.out.println("You should try again!");
				}
			}
			
		}
	}
	
	public void pointSystem(int quizResult) {
		if (quizResult == 1) {
			this.setScore(this.getScore() + 1);
			this.setPointsystem(this.getPointsystem() + 10);
		} else if (quizResult == 2) {
			this.setScore(this.getScore() + 1);
			this.setPointsystem(this.getPointsystem() + 5);
		}
		else if (quizResult == -1) {
			numOfQuestions--;
			// don't decrement counter
		}
	}
	
	public String printScore(int numQuestions) {
		return "You have answered " + this.getScore() + " out of " + numQuestions + " questions correctly\n_______________\n";
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPointsystem() {
		return pointsystem;
	}

	public void setPointsystem(int pointsystem) {
		this.pointsystem = pointsystem;
	}
	
}
