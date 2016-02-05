package in.jaaga.learning.problems;

/**
 * Created by Matteo on 04/02/2016.
 */

import java.util.ArrayList;
import java.util.Random;

import in.jaaga.learning.Problem;

public class NumbersSequence extends Problem {

    int difficulty;
    String ans = "";

    public NumbersSequence(int difficulty) {
        this.difficulty = difficulty;
        int start = new Random().nextInt(difficulty * 5) + 1;
        int multiplicator = new Random().nextInt(3) + difficulty;
        ArrayList<Integer> sequence = createSequence(start, multiplicator);

        String prompt = "";

        int hiddenNum = new Random().nextInt(sequence.size());

        for (int i = 0; i < sequence.size(); i++) {
            if (i == hiddenNum) {
                ans = Integer.toString(sequence.get(i));
                if (i != sequence.size() - 1) {
                    prompt = prompt + "?, ";
                } else {
                    prompt = prompt + "?";
                }

            } else {
                if (i != sequence.size() - 1) {
                    prompt = prompt + sequence.get(i) + ", ";
                } else {
                    prompt = prompt + sequence.get(i);
                }
            }
        }

        //test
        setPrompt(prompt);
    }

    public static ArrayList<Integer> createSequence(int start, int multiplicator){
        ArrayList<Integer> mySequence = new ArrayList<>();
        mySequence.add(start);
        int size = new Random().nextInt(3) + 4;
        for (int i = 1; i < size; i++){
            mySequence.add(mySequence.get(i-1) + multiplicator);
        }
        return mySequence;
    }

    public String getHint() {
        return ("Answer is " + ans);
    }

    public boolean checkAnswer(String input) {
        return ans.equals(input);
    }


    public Problem next() {
        return new NumbersSequence(difficulty);
    }

    public String getTitle() {
        return "Numbers sequence problem with difficulty level " + difficulty;
    }
}