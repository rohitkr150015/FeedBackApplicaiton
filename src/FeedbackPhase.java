import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedbackPhase {


    private int id;
    private String phase;
    private Date sessiondDate;
    private int programId;

    public FeedbackPhase(int id, String phase, Date sessionDate, int programId) {
        this.id = id;
        this.phase = phase;
        this.sessiondDate = sessionDate;
        this.programId = programId;
    }


    public int getId() { return id; }
    public String getPhase() { return phase; }
    public Date getSessionDate() { return sessiondDate; }
    public int getProgramId() { return programId; }

    public static FeedbackPhase findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM FeedbackPhase WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new FeedbackPhase(
                        rs.getInt("id"),
                        rs.getString("phase"),
                        rs.getDate("sessionDate"),
                        rs.getInt("programId")
                );
            }
        }
        return null;
    }

    public static FeedbackPhase findByNameAndProgram(Connection conn, String phaseName, int programId) throws SQLException {
        String sql = "SELECT * FROM FeedbackPhase WHERE phase=? AND programId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phaseName);
            ps.setInt(2, programId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new FeedbackPhase(
                        rs.getInt("id"),
                        rs.getString("phase"),
                        rs.getDate("sessionDate"),
                        rs.getInt("programId")
                );
            }
        }
        return null;
    }

    public List<Question> loadQuestions(Connection conn) throws SQLException {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT q.id, q.text FROM Question q " +
                "JOIN FeedbackPhase_Question fpq ON q.id=fpq.questionId " +
                "WHERE fpq.phaseId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Question(rs.getString("id"), rs.getString("text")));
            }
        }
        return list;
    }

    public void takeFeedback(Student student, Connection conn) throws SQLException {
        List<Question> questions = loadQuestions(conn);
        java.util.Scanner sc = new java.util.Scanner(System.in);
        for (Question q : questions) {
            System.out.println(q.getText());
            String response = sc.nextLine();
            Feedback fb = new Feedback(id, student.getRollNo(), q.getId(), response);
            fb.save(conn);
        }
    }
}
