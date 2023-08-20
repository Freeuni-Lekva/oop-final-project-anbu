package quizapp.models.dao;

import quizapp.models.questions.*;
import utils.DatabaseConnectionSource;
import utils.MyLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionDAO implements DAO<Question> {
    private final DatabaseConnectionSource _source;
    private AnswerDAO answerDAO;

    public QuestionDAO() {
        this._source = DatabaseConnectionSource.getInstance();
        answerDAO = new AnswerDAO();
    }

    @Override
    public Optional<Question> get(int id) {
        String query = "select * from questions where question_id = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Question question = getQuestionFromResultSet(rs);
                return Optional.of(question);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            MyLogger.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    public List<Question> getByQuizId(int quizId) throws Exception {
        String query = "select * from questions where quiz_id = ?";
        List<Question> list = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {

            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(getQuestionFromResultSet(rs));
            }

        } catch (SQLException e) {
            MyLogger.error(e.getMessage());
        }
        if (list.size() == 0) {
            throw new Exception("questions for quiz: " + quizId + " does not exist");
        }
        return list;
    }

    private Question getQuestionFromResultSet(ResultSet rs) throws Exception {
        int questionId = rs.getInt("question_id");
        int quizId = rs.getInt("quiz_id");
        String questionTypeStr = rs.getString("question_type");
        String questionText = rs.getString("question_text");
        String pictureUrl = rs.getString("picture_url");
        List<Answer> answerList = answerDAO.getByQuestionId(questionId);

        QuestionType questionType = QuestionType.fromValue(questionTypeStr);
        Question question;
        switch (questionType) {
            case QUESTION_RESPONSE:
                question = new QuestionResponse(questionId, quizId, questionText);
                question.addAllAnswers(answerList);
                break;
            case FILL_IN_THE_BLANK:
                question = new FillInTheBlank(questionId, quizId, questionText);
                question.addAllAnswers(answerList);
                break;
            case MULTIPLE_CHOICE:
                question = new MultipleChoice(questionId, quizId, questionText);
                question.addAllAnswers(answerList);
                break;
            case PICTURE_RESPONSE:
                question = new PictureResponse(questionId, quizId, pictureUrl, questionText);
                question.addAllAnswers(answerList);
                break;
            default:
                throw new IllegalArgumentException("Invalid question type: " + questionTypeStr);
        }
        return question;
    }

    @Override
    public List<Question> getAll() {
        String query = "select * from questions";
        List<Question> list = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
        ) {
            ResultSet rs = ps.executeQuery(query);

            while (rs.next()) {
                list.add(getQuestionFromResultSet(rs));
            }

        } catch (SQLException e) {
            MyLogger.error(e.getMessage());
        } catch (Exception e) {
            MyLogger.error(e.getMessage(), e);
        }
        return list;
    }


    @Override
    public boolean save(Question question) {
        String query = "INSERT INTO questions (quiz_id, question_type, question_text, picture_url) VALUES (?, ?, ?, ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, question.getQuizId());
            ps.setString(2, question.getQuestionType().getValue());
            ps.setString(3, question.getQuestionText());

            // Set the picture_url only if the question type is Picture-Response
            if (question.getQuestionType() == QuestionType.PICTURE_RESPONSE) {
                ps.setString(4, ((PictureResponse) question).getPictureUrl());
            } else {
                ps.setNull(4, java.sql.Types.VARCHAR);
            }

            int rowsInserted = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int questionId = -1;
            if (rs.next()) {
                questionId = rs.getInt(1);
            } else {
                throw new SQLException("Failed to get generated question_id.");
            }
            question.setQuestionId(questionId);

            for (Answer answer : question.getAnswerList()) {
                answer.setQuestionId(questionId);
                answerDAO.save(answer);
            }

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Question question) {
        throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public boolean delete(Question question) {
        answerDAO.deleteByQuestionID(question.getQuestionId());
        String query = "DELETE FROM questions WHERE question_id = ?";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, question.getQuestionId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }
}
