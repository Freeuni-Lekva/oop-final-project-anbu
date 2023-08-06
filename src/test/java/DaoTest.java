import org.junit.BeforeClass;
import org.junit.Test;
import quizapp.models.dao.QuizDAO;
import quizapp.models.dao.UserDAO;
import quizapp.models.domain.User;
import quizapp.models.questions.*;

import static org.junit.Assert.*;

public class DaoTest {
    private static UserDAO userDAO;
    private static QuizDAO quizDAO;
    @BeforeClass
    public static void setUp(){
        userDAO = new UserDAO();
        quizDAO = new QuizDAO();
    }
    @Test
    public void daoTest() {
        User user1 = new User();
        User user2 = new User();

        user1.setUsername("alex");
        user1.setPasswordHash("asdfghjkl");

        user2.setUsername("mari");
        user2.setPasswordHash("qwertyuiop");

        userDAO.save(user1);
        userDAO.save(user2);

        user1 = userDAO.getByUsername("alex").orElse(null);
        user2 = userDAO.getByUsername("mari").orElse(null);

        assertNotNull(user1);
        assertNotNull(user2);

        assertEquals("alex",user1.getUsername());
        assertEquals("asdfghjkl",user1.getPasswordHash());

        assertEquals("mari", user2.getUsername());
        assertEquals("qwertyuiop", user2.getPasswordHash());

        Quiz quiz1 = getQuizWithQuestions(user1,"quiz1", "test quiz");
        Quiz quiz2 = getQuizWithQuestions(user2,"quiz2", "test quiz 2");

        quizDAO.save(quiz1);
        quizDAO.save(quiz2);

        Quiz quiz1FromDAO =  quizDAO.get(quiz1.getId()).orElse(null);
        Quiz quiz2FromDAO =   quizDAO.get(quiz2.getId()).orElse(null);

        assertEquals(quiz1.getQuizName(), quiz1FromDAO.getQuizName());
        assertEquals(quiz2.getQuizName(), quiz2FromDAO.getQuizName());
    }
    private static Quiz getQuizWithQuestions(User creator, String name, String description){
        Quiz quiz = new Quiz(creator.getId(),name,description);
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
