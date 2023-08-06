package quizapp.models.dao;

import quizapp.models.domain.User;
import quizapp.models.questions.Answer;
import utils.DatabaseConnectionSource;
import utils.MyLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnswerDAO implements DAO<Answer>{
    private final DatabaseConnectionSource _source;
    public AnswerDAO() {
        this._source = DatabaseConnectionSource.getInstance();
    }
    @Override
    public Optional<Answer> get(int id) {
        String query = "select * from answers where answer_id = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Answer answer = new Answer(rs.getString("answer_text"),rs.getBoolean("is_correct"));
                answer.setId(rs.getInt("answer_id"));
                answer.setQuestionId(rs.getInt("question_id"));

                return Optional.of(answer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public List<Answer> getByQuestionId(int questionId)throws Exception {
        String query = "select * from answers where question_id = ?";

        List<Answer> list = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ) {

            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer(rs.getString("answer_text"),rs.getBoolean("is_correct"));
                answer.setId(rs.getInt("answer_id"));
                answer.setQuestionId(rs.getInt("question_id"));

                list.add(answer);
            }

        } catch (SQLException e) {
            MyLogger.error(e.getMessage());
        }
        if(list.size() == 0)  {
            throw new Exception("answers for question: " + questionId + " does not exist");
        }
        return list;
    }
    @Override
    public List<Answer> getAll() {
        throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public boolean save(Answer answer) {
        String query = "INSERT INTO answers (question_id, answer_text, is_correct) VALUES (?, ?, ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);){

            ps.setInt(1, answer.getQuestionId());
            ps.setString(2, answer.getAnswerText());
            ps.setBoolean(3, answer.isCorrect());

            int rowsInserted = ps.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Answer answer) {
        throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public boolean delete(Answer answer) {
        String query = "delete from answers where answer_id = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, answer.getId());

            ps.execute();

        } catch (SQLException e) {
            MyLogger.error(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteByQuestionID(int questionId) {
        String query = "delete from answers where question_id = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, questionId);

            ps.execute();

        } catch (SQLException e) {
            MyLogger.error(e.getMessage());
            return false;
        }

        return true;
    }
}
