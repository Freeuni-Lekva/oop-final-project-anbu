package quizapp.quizservlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.models.questions.Question;
import quizapp.models.questions.Quiz;
import quizapp.settings.Endpoints;
import quizapp.settings.JSP;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

@WebServlet(name = "takeQuizServlet", value = Endpoints.TAKE_QUIZ)
public class TakeQuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        boolean takingQuiz = (boolean) session.getAttribute("takingQuiz");
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        if(!takingQuiz){
            session.setAttribute("answers", new HashMap<Question, String>()); //answers
            session.setAttribute("startTimeMillis",System.currentTimeMillis()); //start time
        }
        session.setAttribute("takingQuiz",true);

        if (quiz.getRandomizedOrder()) Collections.shuffle(quiz.getQuestions());

        if (quiz.getSinglePageQuestions() && quiz.getQuestions().size() > 1) {
            request.getRequestDispatcher(JSP.SINGLE_PAGE_QUIZ).forward(request, response);
        } else {
            request.getSession().setAttribute("correctCounter", 0);
            request.setAttribute("questionIndex", 0);
            request.getRequestDispatcher(JSP.MULTI_PAGE_QUIZ).forward(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSP.QUIZ_WELCOME_PAGE).forward(request, response);
    }
}

