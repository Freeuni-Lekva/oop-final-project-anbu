package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.models.questions.Question;
import quizapp.models.questions.Quiz;
import utils.MyLogger;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "gradeQuizServlet", value = "/secured/gradeQuizServlet")
public class GradeQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        Quiz quiz = (Quiz) session.getAttribute("quiz");
        long timeSpentSeconds = 0;
        long timeLimitSeconds = 0;
        if((boolean) session.getAttribute("takingQuiz")){
            long startTimeMillis = (long) session.getAttribute("startTimeMillis");
            long endTimeMillis = System.currentTimeMillis();

            long timeSpentMillis = endTimeMillis - startTimeMillis;
            timeSpentSeconds = TimeUnit.MILLISECONDS.toSeconds(timeSpentMillis);
            timeLimitSeconds = TimeUnit.MINUTES.toSeconds(quiz.getTimeLimitMinutes());
            if (timeSpentSeconds > timeLimitSeconds + 30) {
                //if user somehow cracked frontend
                MyLogger.info("oops");
                session.setAttribute("correctCounter", 0);
                request.getRequestDispatcher("/Quiz/resultsPage.jsp").forward(request, response);
                return;
            }
            timeSpentSeconds = Math.min(quiz.getTimeLimitMinutes()*60,timeSpentSeconds); //ignore couple seconds errors
            long minutes = timeSpentSeconds / 60;
            long seconds = timeSpentSeconds % 60;
            session.setAttribute("timeTookMinutes", minutes);
            session.setAttribute("timeTookSeconds", seconds);
        }

        int correctCounter = 0;
        HashMap<Question, String> answers = (HashMap<Question, String>) session.getAttribute("answers");
        if (quiz.getSinglePageQuestions()) {

            for (Question q : quiz.getQuestions()) {
                String answer = request.getParameter("" + q.getQuestionId());
                if (q.isAnswerCorrect(answer)) {
                    correctCounter++;
                }
                if (answer == null || answer.equals("")) answer = "not answered";
                answers.put(q, answer);

            }

        } else {
            int index = (Integer) Integer.parseInt(request.getParameter("questionIndex"));
            Question question = quiz.getQuestions().get(index);
            String answer = request.getParameter("" + question.getQuestionId());
            correctCounter = (Integer) session.getAttribute("correctCounter");
            if (!answers.containsKey(question)) {
                if (question.isAnswerCorrect(answer)) {
                    correctCounter++;
                    session.setAttribute("correctCounter", correctCounter);
                }
                if (answer == null || answer.equals("")) answer = "not answered";
                answers.put(question, answer);
            }
            index++;
            if (index < quiz.getQuestions().size() && timeSpentSeconds < timeLimitSeconds) {
                request.setAttribute("questionIndex", index);
                request.getRequestDispatcher("/Quiz/multiPageQuiz.jsp").forward(request, response);
                return;
            }
        }
        session.setAttribute("takingQuiz",false);


        //if we want to save results/answers submitted answers are saved in HashMap answers
        session.setAttribute("answers", answers);
        session.setAttribute("correctCounter", correctCounter);

        request.getRequestDispatcher("/Quiz/resultsPage.jsp").forward(request, response);

    }
}
