package com.volkov.quizofsynonyms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MainQuizOfSynonyms {
    private static final int NUMBER_OF_QUESTIONS = 5;

    public static void main(String[] args) {
        //Structure: List of questions.
        //  Each question is a list of words.
        //      1st word is a key, others - synonyms to compare.
        LinkedList<LinkedList<String>> synonyms = new LinkedList<>();
        int score = 0;
        try {
            synonyms = readQuizFromFile();
        } catch (FileNotFoundException e) {
            System.out.println("dictionary.txt not found\nPlease put the file in program directory");
        }
        Scanner consoleInput = new Scanner(System.in);
        for (LinkedList<String> element : synonyms) {
            // For each question, we remove the first key element from the list and print it
            System.out.println("Please enter a single word synonym for " + element.pollFirst());
            //  Compare answer with remaining elements in the list
            if (element.contains(consoleInput.nextLine().toLowerCase())) {
                ++score;
                System.out.println("Correct. Your score is " + score);
            } else {
                System.out.println("Wrong. Possible answers:\n" + element);
            }
        }
        System.out.println("Result: " + score);
        consoleInput.close();

    }

    private static LinkedList<LinkedList<String>> readQuizFromFile() throws FileNotFoundException {
        //Read from dictionary.txt
        //Implement "Reservoir sampling" for choosing questions
        LinkedList<LinkedList<String>> result = new LinkedList<>();
        String temp;
        String tempResult = null;
        Random random = new Random();
        for (int i = 1; i <= NUMBER_OF_QUESTIONS; i++) {
            int limitOfRandom = 0;
            for (Scanner scanner = new Scanner(new File("dictionary.txt")); scanner.hasNext(); ) {
                ++limitOfRandom;
                temp = scanner.nextLine();
                if (random.nextInt(limitOfRandom) == 0) {
                    tempResult = temp;
                }
            }
            if (tempResult != null) { // escaping NullPointerException for empty strings
                result.add(new LinkedList<>(Arrays.asList(tempResult.split(" "))));
            }
        }
        return result;
    }
}