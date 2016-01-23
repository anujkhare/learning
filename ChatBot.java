import java.util.Random;

public class ChatBot {

	public static final String[] HELLO = { 
		"Hola", 
		"Namaste", 
		"Buenos Dias",
		"Hello"
	};

	public static final String[] BYE = {
		"Bye",
		"Later",
		"Audios",
		"Arrivederci"
	};

	public static final String[] CORRECT = {
		"Yes",
		"You got it",
		"Thats right",
		"Good Job",
		"Correct"
	};

	public static final String[] SORRY = {
		"Nope",
		"Incorrect",
		"That's not the answer I'm looking for",
		"Try Again",
		"Sorry"
	};

	public static final String[] ENCOURAGE = {
		"you're doing great",
		"keep going",
		"you're getting there",
		"keep it up"
	};

	static Random random = new Random();

	Session session;

	public ChatBot(Session session) {
		this.session = session;
	}

	public String comment() {
		Skill skill = session.getSkill();
		if (skill != null && skill.getRemaining() == 1)
			return "just " + skill.getRemaining() + " left in this skill";
		return encourage();
	}

	public String encourage() {
		return ENCOURAGE[random.nextInt(LE.ENCOURAGE.length)];
	}

	public String bye() {
        return BYE[random.nextInt(BYE.length)];
	}

	public String correct() {
		return CORRECT[random.nextInt(LE.CORRECT.length)];
	}

	public String sorry() {
		return SORRY[random.nextInt(LE.SORRY.length)];	
	}



}