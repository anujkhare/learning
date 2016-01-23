import java.util.*;

class Subtraction extends Problem {

    public Subtraction(int max) {
    	super(max);
    	int a = new Random().nextInt(max);
		int b = new Random().nextInt(a + 1);
		setPrompt(new String(a + " - " + b + " = ?"));
		setAnswer(new String(Integer.toString(a - b)));
    }

    public Problem next() {
        return new Subtraction(max);
    }

    public String getTitle() {
        return "Subtraction to " + max;
    }


}
