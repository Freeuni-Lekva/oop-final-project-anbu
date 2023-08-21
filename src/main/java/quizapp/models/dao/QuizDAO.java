package quizapp.models.dao;

import quizapp.managers.FriendManager;
import quizapp.models.domain.QuizActivity;
import quizapp.models.questions.Question;
import quizapp.models.questions.Quiz;
import utils.DatabaseConnectionSource;
import utils.MyLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class QuizDAO implements DAO<Quiz> {
    private final DatabaseConnectionSource _source;
    private QuestionDAO questionDAO;

    public QuizDAO() {
        this._source = DatabaseConnectionSource.getInstance();
        questionDAO = new QuestionDAO();
    }

    @Override
    public Optional<Quiz> get(int id) {
        String query = "select * from quizzes where quiz_id = ? order by creation_date desc";
        List<Question> list = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Quiz quiz;
            if (rs.next()) {
                quiz = getQuizFromResultSet(rs);
                return Optional.of(quiz);
            }

        } catch (SQLException e) {
            MyLogger.error(e.getMessage());
        } catch (Exception e) {
            MyLogger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Quiz> getAll() {
        String query = "select * from quizzes";
        List<Quiz> list = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {
                list.add(getQuizFromResultSet(rs));
            }

        } catch (SQLException e) {
            MyLogger.error(e.getMessage());
        } catch (Exception e) {
            MyLogger.error(e.getMessage(), e);
        }
        return list;
    }

    /* returns quizzes created by friends of user identified with [username] */
    public List<QuizActivity> getAllByFriends(String username) {
        StringBuilder queryBuidler = new StringBuilder("select * from quizzes q join users u on q.creator_id = u.user_id where");

        FriendManager friendManager = new FriendManager();

        List<String> friends = friendManager.getFriends(username);

        for (String friend : friends) {
                queryBuidler.append(String.format(" (username = '%s') or", friend));
        }

        queryBuidler.append(" (1 = 0) order by creation_date desc;");

        String sql = queryBuidler.toString();

        List<QuizActivity> activities = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String creator = rs.getString("username");
                int quiz_id = rs.getInt("quiz_id");
                String quiz_name = rs.getString("quiz_name");
                Date creation_date =rs.getDate("creation_date");

                QuizActivity activity = new QuizActivity(creator, quiz_id, quiz_name, creation_date);

                activities.add(activity);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return activities;
    }

    public List<Quiz> getAllByCreator(int creatorId) {
        String query = "select * from quizzes where creator_id = ? order by creation_date desc";
        List<Quiz> list = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {

            ps.setInt(1, creatorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(getQuizFromResultSet(rs));
            }

        } catch (SQLException e) {
            MyLogger.error(e.getMessage());
        } catch (Exception e) {
            MyLogger.error(e.getMessage(), e);
        }
        return list;
    }

    private Quiz getQuizFromResultSet(ResultSet rs) throws SQLException, Exception {
        int quizId = rs.getInt("quiz_id");
        int creatorId = rs.getInt("creator_id");
        String quizName = rs.getString("quiz_name");
        String description = rs.getString("description");
        Date creationDate = rs.getDate("creation_date");
        List<Question> questions = questionDAO.getByQuizId(quizId);
        boolean randomizedOrder = rs.getBoolean("randomized_order");
        boolean singlePageQuestions = rs.getBoolean("single_page_questions");
        boolean immediateCorrection = rs.getBoolean("immediate_correction");
        int timeLimitMinutes = rs.getInt("time_limit_minutes");
        int timesTaken = rs.getInt("times_taken");
        Quiz quiz = new Quiz(quizId, creatorId, quizName, description, creationDate,randomizedOrder,singlePageQuestions,immediateCorrection,timeLimitMinutes);
        quiz.addAllQuestions(questions);
        quiz.setTimesTaken(timesTaken);
        return quiz;
    }

    @Override
    public boolean save(Quiz quiz) {
        String query = "INSERT INTO quizzes (quiz_name, description, creator_id,randomized_order,single_page_questions,immediate_correction,time_limit_minutes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, quiz.getQuizName());
            ps.setString(2, quiz.getDescription());
            ps.setInt(3, quiz.getCreatorId());
            ps.setBoolean(4,quiz.getRandomizedOrder());
            ps.setBoolean(5,quiz.getSinglePageQuestions());
            ps.setBoolean(6,quiz.getImmediateCorrection());
            ps.setInt(7, quiz.getTimeLimitMinutes());

            int rowsInserted = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int quizId = -1;
            if (rs.next()) {
                quizId = rs.getInt(1);
            } else {
                throw new SQLException("Failed to get generated quiz_id.");
            }
            quiz.setQuizId(quizId);
            for (Question question : quiz.getQuestions()) {
                question.setQuizId(quizId);
                questionDAO.save(question);
            }

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* returns the most popular quizzes (ordered by times taken) */
    public List<Quiz> getPopularQuizzes() {
        String sql = "select * from quizzes order by times_taken desc";

        List<Quiz> quizzes = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Quiz quiz = getQuizFromResultSet(rs);
                quizzes.add(quiz);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return quizzes;
    }

    public void incrementTimesTaken(Quiz quiz) {
        String query = "UPDATE quizzes SET times_taken = times_taken + 1 WHERE quiz_id = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, quiz.getQuizId());
            ps.executeUpdate();
            quiz.setTimesTaken(quiz.getTimesTaken()+1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Quiz quiz) {
        throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public boolean delete(Quiz quiz) {
        for( Question q : quiz.getQuestions()){
            questionDAO.delete(q);
        }

        String query = "DELETE FROM quizzes WHERE quiz_id = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, quiz.getQuizId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }
}
