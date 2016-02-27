package in.jaaga.learning;

import java.util.*;

import in.jaaga.learning.android.S;
import in.jaaga.learning.missions.MathMission;
import in.jaaga.learning.missions.Mission;
import in.jaaga.learning.missions.NegativeNumbers;
import static java.lang.Package.getPackage;
//import in.jaaga.learning.problems.NumbersSequence;


public class Learning {
    public static final int NO_RESPONSE = ChatItem.NO_RESPONSE;
    public static final int NUMBER_RESPONSE = ChatItem.NUMBER_RESPONSE;
    public static final int TEXT_RESPONSE = ChatItem.TEXT_RESPONSE;

    Random random = new Random();

	private int points = 0;
	private Session session;
	private ChatBot chatBot;
    private Mission mission;
    private Iterator<Skill> path;
    private Skill skill;
	private Problem problem;

	InteractionInterface interactionInterface;
    DB db;

    public Learning(InteractionInterface minteractionInterface, Session session, ChatBot chatBot, DB db) {
		interactionInterface = minteractionInterface;
        this.db = db;
        this.session = session;
        this.chatBot = chatBot;
        this.chatBot.setSession(session);
        setMission(new MathMission());
    }

    public void setMission(Mission mission) {
        this.mission = mission;
        path = mission.getList().iterator();
        skill = path.next();
        session.setSkill(skill);
        problem = skill.getProblem();
    }

    public Mission getMission() {
        return mission;
    }

    public void start() {
        sendMessage(chatBot.hello(), NO_RESPONSE);
        sendMessage(chatBot.askName(),TEXT_RESPONSE);
        //sendMessage(problem.getPrompt(), NUMBER_RESPONSE);
    }

	public void onResponse(String response) {
        if(session.getName() == null) {
            session.setName(response);
            if (db.containsName(response) == true) {
                sendMessage("Welcome Back " + session.getName(), NO_RESPONSE);
            } else {
                sendMessage("It seems like you are new.\n Nice to meet you ", NO_RESPONSE);
                db.addName(response);
            }
            sendMessage(problem.getPromptChatItem());
        } else if (".".equals(response)) {
            sendMessage(chatBot.adminPrompt(), TEXT_RESPONSE);
            return;
        } else if (response.equals("whoami"))  {
            sendMessage(session.getName(),NO_RESPONSE);
            sendMessage(problem.getPromptChatItem());
        } else if (response.contains("help")) {
            sendMessage("you can say 'hint' for help with the current problem \n"+
                        "'skip will move to the next skill in the current mission\n"+
                        "mission will list the current mission and mission options", TEXT_RESPONSE);
        } else if ("hint".equals(response)) {
                sendMessage(skill.takeHint(), NO_RESPONSE);
                sendMessage(problem.getPromptChatItem());
                return;
        } else if ("skip".equals(response)) {
            skill = path.next();
            session.setSkill(skill);
            problem = skill.getProblem();
            sendMessage(problem.getPromptChatItem());
            return;
        } else if (response.startsWith("mission")) {
            if (response.contains("general")) {
                setMission(new MathMission());
                sendMessage(problem.getPromptChatItem());
            } else if (response.contains("negative")) {
                setMission(new NegativeNumbers());
                sendMessage(problem.getPromptChatItem());
            } else if (response.contains("easy")) {
                setMission(new NegativeNumbers());
                sendMessage(problem.getPromptChatItem());
            } else {
                sendMessage("the current mission is: " + mission.getTitle()+
                            "\n available missions are: general and negative." +
                            " type - mission negative - to switch.", TEXT_RESPONSE);
            }
        } else if (session.getName() != null) {
            checkAnswer(response);
            sendMessage(problem.getPromptChatItem());
        }
    }

    void checkAnswer(String response) {
        if (problem.checkAnswer(response)) {  // correct
            sendMessage(chatBot.correct(), NO_RESPONSE);
            int remains = skill.solvedOne();
            if (remains <= 0) {
                Skill last = skill;
                skill = path.next();
                session.setSkill(skill);
                problem = skill.getProblem();
                session.addPoints(last.getPoints());
                sendMessage(chatBot.levelUp(last, skill), NUMBER_RESPONSE);
            } else {
                sendMessage(chatBot.comment(), NO_RESPONSE);
                problem = problem.next();
				skill.setProblem(problem);
//                sendMessage(problem.getPrompt(), NUMBER_RESPONSE);
            }
        } else {
            sendMessage(chatBot.sorry(), NO_RESPONSE);
        }
    }

    private void sendMessage(ChatItem item) {
        item.setSender("bot");
        interactionInterface.send(item);
    }

    private void sendMessage(String text, int responseType) {
        ChatItem item = new ChatItem(text, responseType);
        item.setResponseType(responseType);
        sendMessage(item);
    }
}
