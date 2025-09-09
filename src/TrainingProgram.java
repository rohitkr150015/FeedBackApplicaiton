import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TrainingProgram {

    private int id ;
    private String name;
    private Date startDate;
    private Date endDate;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TrainingProgram(int id, String name, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public static TrainingProgram findById(Connection conn  , int id) throws SQLException{
        String query="SELECT * FROM TrainingProgram WHERE id=?";

        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1,id);
            ResultSet rs= ps.executeQuery();
            if(rs.next()){
                return new TrainingProgram(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate")
                );
            }
        }
        return null;
    }

    public static TrainingProgram findByNameAndStudent(Connection conn, String name, String rollNo) throws SQLException {
        String sql = "SELECT tp.* FROM TrainingProgram tp " +
                "JOIN Student_Program sp ON tp.id=sp.programId " +
                "WHERE tp.name=? AND sp.rollNo=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, rollNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TrainingProgram(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate")
                );
            }
        }
        return null;
    }
}
