package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.models.dao.QuizDAO;
import quizapp.models.questions.Quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@WebServlet(name = "takeQuizServlet", value = "/takeQuizServlet")
public class TakeQuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int quizIndex = Integer.parseInt(request.getParameter("quizIndex"));
            QuizDAO quizDao = new QuizDAO();
            Quiz quiz = quizDao.get(quizIndex).orElse(null);
            if (quiz != null) {
                HttpSession session = request.getSession();
                session.setAttribute("quiz", quiz);
                if(quiz.getRandomizedOrder()) Collections.shuffle(quiz.getQuestions());
                if(quiz.getSinglePageQuestions()){
                    response.sendRedirect("Quiz/singlePageQuiz.jsp");
                }
                else{
                    int correct = 0;
                    request.getSession().setAttribute("correct", correct);
                    response.sendRedirect("Quiz/multiPageQuiz.jsp?questionIndex=0");
                }
            } else {
                response.sendRedirect("/secured/homepage");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/secured/homepage");
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//    }
}
