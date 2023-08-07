package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.models.dao.QuizDAO;
import quizapp.models.questions.Question;
import quizapp.models.questions.Quiz;
import utils.MyLogger;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "gradeQuizServlet", value = "/gradeQuizServlet")
public class GradeQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Quiz quiz =(Quiz) request.getSession().getAttribute("quiz");
        int correct = 0;
        if(quiz.getSinglePageQuestions()){
            for (Question q : quiz.getQuestions()) {
                if(q.isAnswerCorrect(request.getParameter("" + q.getQuestionId()))){
                    correct++;
                }
            }
        }else{
            int index = (Integer) Integer.parseInt(request.getParameter("questionIndex"));
            Question question = quiz.getQuestions().get(index);
            String answer = request.getParameter("" + question.getQuestionId());
            correct = (Integer) request.getSession().getAttribute("correct");
            if(question.isAnswerCorrect(answer)){
                correct++;
                request.getSession().setAttribute("correct",correct);
            }
            index++;
            if(index < quiz.getQuestions().size()){
                response.sendRedirect("Quiz/multiPageQuiz.jsp?questionIndex="+index);
                return;
            }
        }
        MyLogger.info(correct + " out of " + quiz.getQuestions().size() + " questions were correct");
        response.sendRedirect("Quiz/resultsPage.jsp");
    }
}
