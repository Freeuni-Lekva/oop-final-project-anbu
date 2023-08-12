package quizapp.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import quizapp.models.dao.QuizDAO;
import quizapp.models.domain.User;
import quizapp.models.questions.*;
import utils.MyLogger;

import java.io.IOException;

@WebServlet(name = "makeQuiz", value = "/secured/makeQuiz")
public class MakeQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User creator = (User) session.getAttribute("user");

        int creatorId = creator.getId();
        String quizName = request.getParameter("quizName");
        String description = request.getParameter("description");
        int timeLimitMinutes = Integer.parseInt(request.getParameter("timeLimit"));

        boolean randomizedOrder = request.getParameter("randomizedQuestionsOption") != null;
        boolean singlePageQuestions = request.getParameter("multiplePageQuestionsOption") == null;
        boolean immediateCorrection = request.getParameter("immediateCorrectionOption") != null;

        Quiz quiz = new Quiz(creatorId, quizName, description, randomizedOrder, singlePageQuestions, immediateCorrection, timeLimitMinutes);

        int questionIndex = 0;
        while (true) {
            String questionType = request.getParameter("questionType:q=" + questionIndex);
            if (questionType == null) {
                break;
            }
            switch (questionType) {
                case "Question-Response":
                    quiz.addQuestion(getQuestionResponseFromRequest(request, questionIndex));
                    break;
                case "Fill in the Blank":
                    quiz.addQuestion(getFillInTheBlankFromRequest(request, questionIndex));
                    break;
                case "Multiple Choice":
                    quiz.addQuestion(getMultipleChoiceFromRequest(request, questionIndex));
                    break;
                case "Picture-Response":
                    quiz.addQuestion(getPictureResponseFromRequest(request, questionIndex));
                    break;
                default:
                    MyLogger.error("Unknown question type");
            }
            questionIndex++;
        }
        QuizDAO quizDAO = new QuizDAO();
        quizDAO.save(quiz);

        request.getRequestDispatcher("/Quiz/quizWelcomePage.jsp?quizId=" + quiz.getQuizId()).forward(request, response);
    }


    private Question getQuestionResponseFromRequest(HttpServletRequest request, int questionIndex) {
        String questionText = request.getParameter("questionText:q=" + questionIndex);
        Question question = new QuestionResponse(questionText);
        int answerIndex = 0;
        while (true) {
            String answer = request.getParameter("answer:q=" + questionIndex + ",a=" + answerIndex);
            if (answer == null) break;
            question.addAnswer(answer, true);
            answerIndex++;
        }

        return question;
    }

    private Question getFillInTheBlankFromRequest(HttpServletRequest request, int questionIndex) {
        String questionText = request.getParameter("questionText:q=" + questionIndex).replace("\\?", "_______");
        Question question = new FillInTheBlank(questionText);
        int answerIndex = 0;
        while (true) {
            String answer = request.getParameter("answer:q=" + questionIndex + ",a=" + answerIndex);
            if (answer == null) break;
            question.addAnswer(answer, true);
            answerIndex++;
        }

        return question;
    }

    private Question getMultipleChoiceFromRequest(HttpServletRequest request, int questionIndex) {
        String questionText = request.getParameter("questionText:q=" + questionIndex);

        Question question = new MultipleChoice(questionText);

        int correctIndex = Integer.parseInt(request.getParameter("correct:q=" + questionIndex));
        int answerIndex = 0;
        while (true) {
            String answer = request.getParameter("answer:q=" + questionIndex + ",a=" + answerIndex);
            if (answer == null) break;
            question.addAnswer(answer, answerIndex == correctIndex);
            answerIndex++;
        }

        return question;
    }

    private Question getPictureResponseFromRequest(HttpServletRequest request, int questionIndex) {
        String questionText = request.getParameter("questionText:q=" + questionIndex);
        String pictureUrl = request.getParameter("pictureUrl:q=" + questionIndex);

        Question question = new PictureResponse(pictureUrl, questionText);
        int answerIndex = 0;
        while (true) {
            String answer = request.getParameter("answer:q=" + questionIndex + ",a=" + answerIndex);
            if (answer == null) break;
            question.addAnswer(answer, true);
            answerIndex++;
        }

        return question;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/Quiz/makeQuizPage.jsp").forward(request, response);
    }

}
