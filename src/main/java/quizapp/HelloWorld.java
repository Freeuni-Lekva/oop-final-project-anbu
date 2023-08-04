package quizapp;

import quizapp.models.dao.AnswerDAO;
import quizapp.models.questions.Answer;
import utils.MyLogger;

public class HelloWorld {
    public static void main(String[] args){
        Answer answer = new Answer("Hello World", true);
        answer.setQuestionId(1);
        AnswerDAO answerDAO = new AnswerDAO();
        answerDAO.save(answer);
    }
}
