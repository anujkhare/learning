package in.jaaga.learning.android.problems;

import java.util.*;

import in.jaaga.learning.Problem;
import in.jaaga.learning.problems.SimpleProblem;
import in.jaaga.learning.R;
import in.jaaga.learning.android.S;

public class AndroidAddition extends SimpleProblem {
    int max;

    public AndroidAddition(int max) {
        this.max = max;
        int a, b;
        if (max > 0) {
			a = new Random().nextInt(max);
			b = new Random().nextInt(max);
        } else {  // max is negative
            a = new Random().nextInt(max * -2) - (-max);
            b = new Random().nextInt(max * -2) - (-max);
        }
        prompt = a + " + " + b + " = ?";
        answer = Integer.toString(a + b);
    }

    public Problem next() {
        return new AndroidAddition(max);
    }

    public String getTitle() {
        int m = Math.abs(max);
        if (max > 0)
            return S.RESOURCES.getString(R.string.addition_title, max);
        else
            return S.RESOURCES.getString(R.string.addition_negative_title, max);
    }
}

