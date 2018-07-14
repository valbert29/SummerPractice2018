package mm.memeonare.DataC;

import java.util.ArrayList;

public class Question {
    private String mem;
    private String question;
    private ArrayList<String> answers;
    private int cIndex;

    public Question(String mem, String question, ArrayList<String> answers, int cIndex) {
        this.mem = mem;
        this.question = question;
        this.answers = answers;
        this.cIndex = cIndex;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public int getcIndex() {
        return cIndex;
    }

    public String getMem() {
        return mem;
    }
}

