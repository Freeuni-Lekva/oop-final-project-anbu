package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizapp.models.dao.QuestionDAO;
import quizapp.models.questions.Question;
import utils.MyLogger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "answerServlet", value = "/answerServlet")
public class AnswerServlet extends HttpServlet {

    private HashMap<String, String> answered = new HashMap<String, String>();
    private HashMap<String, Integer> scores = new HashMap<String, Integer>();
    private QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyLogger.info("imIN");
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        String userAnswer = request.getParameter("answer");
        MyLogger.info(questionId + " " + userAnswer);
        if (answered.containsKey(request.getSession().getId() + questionId)) {
            PrintWriter out = response.getWriter();
            out.print("already_answered");
            out.flush();
            return;
        } else {
            Question question = questionDAO.get(questionId).orElse(null);
            if (question == null) {
                throw new IOException("question with id:" + questionId + "not found in database");
            }
            answered.put(request.getSession().getId() + questionId, userAnswer);
            if (!scores.containsKey(request.getSession().getId())) {
                scores.put(request.getSession().getId(), 0);
            }
            if (question.isAnswerCorrect(userAnswer)) {
                scores.put(request.getSession().getId(), scores.get(request.getSession().getId()));
            }
            PrintWriter out = response.getWriter();
            out.print(question.isAnswerCorrect(userAnswer) ? "correct" : "incorrect");
            out.flush();
        }
    }

}
