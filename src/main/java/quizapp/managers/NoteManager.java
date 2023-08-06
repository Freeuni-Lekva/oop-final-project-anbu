package quizapp.managers;

import quizapp.models.domain.message.NoteMessage;
import utils.DatabaseConnectionSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteManager {

    private final DatabaseConnectionSource _source;

    public NoteManager() {
        this._source = DatabaseConnectionSource.getInstance();
    }

    public void sendNote(String sender, String receiver, String note) {
        String sql = "insert into notes(sender, receiver, note) values (?, ?, ?)";

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, note);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NoteMessage> getNotesByUsername(String username) {
        String sql = "select * from notes where receiver = ? order by send_date";

        List<NoteMessage> messages = new ArrayList<>();

        try (Connection conn = _source.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String note = rs.getString("note");

                NoteMessage noteMessage = new NoteMessage(sender, receiver, note);
                messages.add(noteMessage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

}
