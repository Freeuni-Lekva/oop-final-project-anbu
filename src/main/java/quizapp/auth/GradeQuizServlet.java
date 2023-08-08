package quizapp.auth;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import quizapp.models.questions.Question;
import quizapp.models.questions.Quiz;
import utils.MyLogger;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "gradeQuizServlet", value = "/gradeQuizServlet")
public class GradeQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
        int correctCounter = 0;
        HashMap<Question, String> answers = (HashMap<Question, String>) request.getSession().getAttribute("answers");
        if (quiz.getSinglePageQuestions()) {
            for (Question q : quiz.getQuestions()) {
                String answer = request.getParameter("" + q.getQuestionId());
                answers.put(q, answer);
                if (q.isAnswerCorrect(answer)) {
                    correctCounter++;
                }
            }
        } else {
            int index = (Integer) Integer.parseInt(request.getParameter("questionIndex"));
            Question question = quiz.getQuestions().get(index);
            String answer = request.getParameter("" + question.getQuestionId());
            correctCounter = (Integer) request.getSession().getAttribute("correctCounter");
            if (!answers.containsKey(question)) {
                answers.put(question, answer);
                if (question.isAnswerCorrect(answer)) {
                    correctCounter++;
                    request.getSession().setAttribute("correctCounter", correctCounter);
                }
            }
            index++;
            if (index < quiz.getQuestions().size()) {
                response.sendRedirect("Quiz/multiPageQuiz.jsp?questionIndex=" + index);
                return;
            }
        }
        //if we want to save results/answers submitted answers are saved in HashMap answers
        MyLogger.info(correctCounter + " out of " + quiz.getQuestions().size() + " questions were correct");
        response.sendRedirect("Quiz/resultsPage.jsp");
    }
}
