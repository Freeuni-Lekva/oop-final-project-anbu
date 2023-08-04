package quizapp.models.dao;

import quizapp.models.questions.*;
import utils.DatabaseConnectionSource;
import utils.MyLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "select * from quizzes where quiz_id = ?";
        List<Question> list = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(query);
            Quiz quiz;
            if (rs.next()) {
                quiz = getQuizFromResultSet(rs);
                return Optional.of(quiz);
            }

        } catch (SQLException e) {
            MyLogger.error("Error occurred while connecting to database: UserDAO::getAll");
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
            MyLogger.error("Error occurred while connecting to database: UserDAO::getAll");
        } catch (Exception e) {
            MyLogger.error(e.getMessage(), e);
        }
        return list;
    }

    public List<Quiz> getAllByCreator(int creatorId) {
        String query = "select * from quizzes where creator_id = ?";
        List<Quiz> list = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {

            ps.setInt(1, creatorId);
            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {
                list.add(getQuizFromResultSet(rs));
            }

        } catch (SQLException e) {
            MyLogger.error("Error occurred while connecting to database: UserDAO::getAll");
        } catch (Exception e) {
            MyLogger.error(e.getMessage(), e);
        }
        return list;
    }

    private Quiz getQuizFromResultSet(ResultSet rs) throws SQLException, Exception {
        int quizId = rs.getInt("quiz_id");
        int creatorId = rs.getInt("creator_id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        Date createDate = rs.getDate("create_date");
        List<Question> questions = questionDAO.getByQuizId(quizId);
        Quiz quiz = new Quiz(quizId, creatorId, title, description, createDate);
        quiz.addAllQuestions(questions);
        return quiz;
    }

    @Override
    public boolean save(Quiz quiz) {
        String query = "INSERT INTO questions (quiz_name, description, creator_id) VALUES (?, ?, ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, quiz.getId());
            ps.setString(2, quiz.getDescription());
            ps.setInt(3, quiz.getCreatorId());

            int rowsInserted = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int quizId = -1;
            if (rs.next()) {
                quizId = rs.getInt("quiz_id");
            } else {
                throw new SQLException("Failed to get generated quiz_id.");
            }

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

    @Override
    public boolean update(Quiz quiz) {
        throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public boolean delete(Quiz quiz) {
        throw new UnsupportedOperationException("This method is not implemented.");
    }
}
