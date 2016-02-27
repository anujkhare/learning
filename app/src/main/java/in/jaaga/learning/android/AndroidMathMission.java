package in.jaaga.learning.android;

import java.util.ArrayList;

import in.jaaga.learning.Skill;
import in.jaaga.learning.missions.MathMission;
import in.jaaga.learning.Mission;

/**
 * Created by admin on 24/02/2016.
 */
public class AndroidMathMission implements Mission {

    public ArrayList<Skill> getList() {
        return new MathMission().getList();
    }
    /*
        ArrayList<Skill> mission = new ArrayList<Skill>();

        mission.add(new Skill(new Addition(10), 5, 100));
        mission.add(new Skill(new Addition(100), 5, 150));
        mission.add(new Skill(new Subtraction(10), 5, 100));
        mission.add(new Skill(new Subtraction(100), 5, 150));

        mission.add(new Skill(new Multiplication(5, 5), 10, 200));
        mission.add(new Skill(new Multiplication(10, 10), 10, 200));
        mission.add(new Skill(new Addition(1000), 5, 150));

        //mission.add(new Skill(new Division(30, 10), 5, 250));
        mission.add(new Skill(new Division(100, 10), 5, 250));
        mission.add(new Skill(new DivisionRemainders(30, 10), 5, 300));
        mission.add(new Skill(new DecimalDivision(100,10),5,100));
        mission.add(new Skill(new Subtraction(1000), 5, 100));
        mission.add(new Skill(new Multiplication(100, 10), 10, 200));

        mission.add(new Skill(new Addition(-10), 5, 100));
        mission.add(new Skill(new Subtraction(-10), 5, 150));
        mission.add(new Skill(new Multiplication(-12, 12), 5, 200));

        mission.add(new Skill(new DecimalAddition(0, 9, 1), 5, 100));
        mission.add(new Skill(new VariableDivision(100, 10), 8, 100));
        mission.add(new Skill(new VariableMultiplication(10, 10), 8, 100));
        mission.add(new Skill(new VariableSubtraction(10), 8, 100));
        mission.add(new Skill(new VariableAddition(10), 8, 100));
        mission.add(new Skill(new Addition(10), 2, 100));
        mission.add(new Skill(new Subtraction(10), 2, 150));
        mission.add(new Skill(new Multiplication(12, 12), 3, 200));
        mission.add(new Skill(new Division(100, 10), 3, 250));
        mission.add(new Skill(new DivisionRemainders(100, 10), 4, 300));

        return mission;
        */


    public String getTitle() {
        return "general mission covering everything from simple addition upto variable division";
    }
}