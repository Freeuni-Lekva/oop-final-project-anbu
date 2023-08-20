import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import quizapp.models.dao.AnswerDAO;
import quizapp.models.dao.QuestionDAO;
import quizapp.models.dao.QuizDAO;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;
import quizapp.models.questions.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizQuestionAnswerDaoTest {
    private static UserDAO userDAO;
    private static QuizDAO quizDAO;
    private static QuestionDAO questionDAO;
    private static AnswerDAO answerDAO;
    private static User user1;
    private static User user2;
    @BeforeAll
    public static void setUp(){
        userDAO = new UserDAO();
        quizDAO = new QuizDAO();
        questionDAO = new QuestionDAO();
        answerDAO = new AnswerDAO();

        User user1 = new User();
        User user2 = new User();

        user1.setUsername("alex");
        user1.setPasswordHash("asdfghjkl");

        user2.setUsername("mari");
        user2.setPasswordHash("qwertyuiop");

        userDAO.save(user1);
        userDAO.save(user2);
    }
    @Test
    public void FullQuizDaoTest() {
        user1 = userDAO.getByUsername("alex").orElse(null);
        user2 = userDAO.getByUsername("mari").orElse(null);

        assertNotNull(user1);
        assertNotNull(user2);

        Quiz quiz1 = getQuizWithQuestionsAndAnswers(user1,"quiz1", "test quiz");
        Quiz quiz2 = getQuizWithQuestionsAndAnswers(user2,"quiz2", "test quiz 2");

        //save test
        quizDAO.save(quiz1);
        quizDAO.save(quiz2);

        //get test
        Quiz quiz1FromDAO =  quizDAO.get(quiz1.getQuizId()).orElse(null);
        Quiz quiz2FromDAO =   quizDAO.get(quiz2.getQuizId()).orElse(null);

        assertNotNull(quiz1FromDAO);
        assertNotNull(quiz2FromDAO);

        //quizdao uses question dao which uses answer dao to save list of questions with answers
        //so if quizDAO.save(...) works then the answerDAO.save(...) works and so does questionDAO.save(...)
        assertEquals(quiz1, quiz1FromDAO);
        assertEquals(quiz2, quiz2FromDAO);
        assertNotEquals(quiz1, quiz2);
        assertNotEquals(quiz1FromDAO, quiz2FromDAO);
        assertNotEquals(quiz1, quiz2FromDAO);
        assertNotEquals(quiz1FromDAO, quiz2);

        List<Question> allQuestions = questionDAO.getAll();
        List<Answer> allAnswers = answerDAO.getAll();

        for(Question q : quiz1.getQuestions()){
           assertTrue(allQuestions.contains(q));
           for(Answer answer : q.getAnswerList()){
               assertTrue(allAnswers.contains(answer));
           }
        }

        Quiz quiz3 = getQuizWithQuestionsAndAnswers(user1,"quiz3", "test quiz 3");

        quizDAO.save(quiz3);
        Quiz quiz3FromDAO =  quizDAO.get(quiz3.getQuizId()).orElse(null);

        assertNotNull(quiz3FromDAO);

        assertEquals(quiz3,quiz3FromDAO);
        assertNotEquals(quiz1FromDAO, quiz3FromDAO);

        //getAllByCreator test
        List<Quiz> user1Quizzes = quizDAO.getAllByCreator(user1.getId());
        List<Quiz> user2Quizzes = quizDAO.getAllByCreator(user2.getId());

        assertTrue(user1Quizzes.contains(quiz1FromDAO));
        assertTrue(user1Quizzes.contains(quiz3FromDAO));

        assertTrue(user2Quizzes.contains(quiz2FromDAO));

        //getAll test
        List<Quiz> allQuizzes = quizDAO.getAll();

        assertTrue(allQuizzes.contains(quiz1FromDAO));
        assertTrue(allQuizzes.contains(quiz3FromDAO));
        assertTrue(allQuizzes.contains(quiz2FromDAO));

        // times taken increment test
        assertEquals(0 ,quiz1FromDAO.getTimesTaken());
        assertEquals(0, quiz2FromDAO.getTimesTaken());
        assertEquals(0 ,quiz3FromDAO.getTimesTaken());

        for (int i = 0; i < 10; i++) {
            quizDAO.incrementTimesTaken(quiz1);
        }
        for (int i = 0; i < 7; i++) {
            quizDAO.incrementTimesTaken(quiz2);
        }
        for (int i = 0; i < 5; i++) {
            quizDAO.incrementTimesTaken(quiz3);
        }

        quiz1FromDAO =  quizDAO.get(quiz1.getQuizId()).orElse(null);
        quiz2FromDAO =   quizDAO.get(quiz2.getQuizId()).orElse(null);
        quiz3FromDAO =   quizDAO.get(quiz3.getQuizId()).orElse(null);

        assertNotNull(quiz1FromDAO);
        assertNotNull(quiz2FromDAO);
        assertNotNull(quiz3FromDAO);

        assertEquals(10, quiz1FromDAO.getTimesTaken());
        assertEquals(7, quiz2FromDAO.getTimesTaken());
        assertEquals(5, quiz3FromDAO.getTimesTaken());

        assertEquals(10, quiz1.getTimesTaken());
        assertEquals(7, quiz2.getTimesTaken());
        assertEquals(5, quiz3.getTimesTaken());

        //get popular quizzes test
        List<Quiz> popularQuizzes = quizDAO.getPopularQuizzes();

        for(int i = 1; i <popularQuizzes.size(); i++){
            assertTrue(popularQuizzes.get(i).getTimesTaken() <= popularQuizzes.get(i-1).getTimesTaken());
        }

        //delete test
        quizDAO.delete(quiz1FromDAO);
        quizDAO.delete(quiz2FromDAO);
        quizDAO.delete(quiz3FromDAO);

        allQuestions = questionDAO.getAll();
        allAnswers = answerDAO.getAll();

        for(Question q : quiz1.getQuestions()){
            assertFalse(allQuestions.contains(q));
            for(Answer answer : q.getAnswerList()){
                assertFalse(allAnswers.contains(answer));
            }
        }
    }

    private static Quiz getQuizWithQuestionsAndAnswers(User creator, String name, String description){
        Quiz quiz = new Quiz(creator.getId(),name,description,false ,true, false, 60);
        QuestionResponse questionResponse= new QuestionResponse("question 1?");
        FillInTheBlank fillInTheBlank = new FillInTheBlank("question ___?");
        PictureResponse pictureResponse = new PictureResponse("https://www.w3schools.com/css/img_5terre.jpg", "question 3?");
        MultipleChoice multipleChoice = new MultipleChoice("question 4?");
        questionResponse.addAnswer("yes", true);
        fillInTheBlank.addAnswer("2",true);
        pictureResponse.addAnswer("picture", true);
        multipleChoice.addAnswer("yes", true);
        multipleChoice.addAnswer("no", false);
        quiz.addQuestion(questionResponse);
        quiz.addQuestion(fillInTheBlank);
        quiz.addQuestion(pictureResponse);
        quiz.addQuestion(multipleChoice);
        return quiz;
    }
}
