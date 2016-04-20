package in.jaaga.learning.bots.srini;

import java.util.Random;

import in.jaaga.learning.R;
import in.jaaga.learning.api.Bot;
import in.jaaga.learning.api.ChatItem;
import in.jaaga.learning.bots.srini.problems.Addition;
import in.jaaga.learning.bots.srini.problems.DecimalDivision;
import in.jaaga.learning.bots.srini.problems.DecimalMultiplication;
import in.jaaga.learning.bots.srini.problems.Division;
import in.jaaga.learning.bots.srini.problems.Multiplication;
import in.jaaga.learning.bots.srini.problems.Subtraction;
import in.jaaga.learning.bots.srini.problems.VariableAddition;
import in.jaaga.learning.bots.srini.problems.VariableDivision;
import in.jaaga.learning.bots.srini.problems.VariableMultiplication;
import in.jaaga.learning.bots.srini.problems.VariableSubtraction;

/**
 * Created by freeman on 19/4/16.
 */
public class Srini extends Bot {
    Mission mission;

    public void init() {
        SriniStrings.setResources(getResources());
        mission = new Mission(sender);

        mission.add(new ProblemSkill(this, title(R.string.addition_title, 10),  new Addition(10), 5, 100));
        mission.add(new ProblemSkill(this, title(R.string.addition_title, 100), new Addition(100), 5, 100));

        mission.add(new ProblemSkill(this, title(R.string.subtraction_title, 10), new Subtraction(10), 5, 100));
        mission.add(new ProblemSkill(this, title(R.string.subtraction_title, 100), new Subtraction(100), 5, 150));

        mission.add(new ProblemSkill(this, title(R.string.multiplication_title, 5), new Multiplication(5, 5), 10, 200));
        mission.add(new ProblemSkill(this, title(R.string.multiplication_title, 10), new Multiplication(10, 10), 10, 200));

        mission.add(new ProblemSkill(this, title(R.string.addition_title, 1000), new Addition(1000), 5, 150));

        mission.add(new ProblemSkill(this, title(R.string.division_title, 30, 10), new Division(30, 10), 5, 250));
        mission.add(new ProblemSkill(this, title(R.string.division_title, 100, 10), new Division(100, 10), 5, 250));

        mission.add(new ProblemSkill(this, title(R.string.addition_negative_title, 10), new Addition(-10), 5, 100));
        mission.add(new ProblemSkill(this, title(R.string.subtraction_negative_title, 10), new Subtraction(-10), 5, 150));
        mission.add(new ProblemSkill(this, title(R.string.multiplication_negative_title, 30), new Multiplication(-12, 12), 5, 200));

        mission.add(new ProblemSkill(this, title(R.string.decimal_multiplication_title, 100), new DecimalMultiplication(100,10),5,100));
        mission.add(new ProblemSkill(this, title(R.string.decimal_division_title, 100, 10), new DecimalDivision(100,10),5,100));

        mission.add(new ProblemSkill(this, title(R.string.var_addition_title, 100), new VariableAddition(100), 8, 100));
        mission.add(new ProblemSkill(this, title(R.string.var_subtraction_title, 100), new VariableSubtraction(100), 8, 100));
        mission.add(new ProblemSkill(this, title(R.string.var_multiplication_title, 10), new VariableMultiplication(10, 10), 8, 100));
        mission.add(new ProblemSkill(this, title(R.string.var_division_title, 100, 10), new VariableDivision(100, 10), 8, 100));
    }

    public void onStart() {
        super.onStart();
        init();
//        String[] hello = getResources().getStringArray(R.array.hello);
//        sender.send(new ChatItem(hello[new Random().nextInt(hello.length)]));
        sender.send(new ChatItem(SriniStrings.hello()));
        sender.send(mission.getPrompt());
    }

    public void onMessageReceived(String text) {
        mission.processResponse(text);
        sender.send(mission.getPrompt());
    }

    private String title(int resourceId) {
        return getResources().getString(resourceId);
    }

    private String title(int resourceId, int arg0) {
        return getResources().getString(resourceId, Integer.valueOf(arg0));
    }

    private String title(int resourceId, int arg0, int arg1) {
        return getResources().getString(resourceId, Integer.valueOf(arg0), Integer.valueOf(arg1));
    }
}
