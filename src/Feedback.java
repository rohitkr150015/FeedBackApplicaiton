import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Feedback {

    private int phaseId;
    private int rollNo;
    private String questionId;
    private String response;

    public Feedback(int phaseId, int  rollNo, String questionId, String response) {
        this.phaseId = phaseId;
        this.rollNo = rollNo;
        this.questionId = questionId;
        this.response = response;
    }

    public void save(Connection conn) throws SQLException {
        String sql = "INSERT INTO Feedback (phaseId, rollNo, questionId, response) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, phaseId);
            ps.setInt(2, rollNo);
            ps.setString(3, questionId);
            ps.setString(4, response);
            ps.executeUpdate();
        }
    }
}
