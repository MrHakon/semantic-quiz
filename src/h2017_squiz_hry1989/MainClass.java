package h2017_squiz_hry1989;

import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonGenerationException;

public class MainClass {

	public static void main(String[] args) throws JsonGenerationException, IOException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Welcome to quiz!\nHow many questions do you want?");
		
		QuizInitializer quiz = new QuizInitializer();
		quiz.startQuiz(scanner.nextInt());
	}
}