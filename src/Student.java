import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {

    private String name;
    private int  rollNo;


    public Student(String name , int rollNo){
        this.name=name;
        this.rollNo=rollNo;
    }

    public String getName() {
        return name;
    }

    public int getRollNo() {
        return rollNo;
    }

    public static Student findByRollNo(Connection conn, String rollNo) throws SQLException{
        String query="SELECT rollNo , name FROM student WHERE rollNo=?";
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,rollNo);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return new Student(rs.getString("name"), rs.getInt("rollNo"));
            }
        }
        return null;
    }

    public boolean isEnrolledInProgram(Connection conn, int programId) throws SQLException {
        String query = "SELECT * FROM Student_Program WHERE rollNo=? AND programId=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, rollNo);
            ps.setInt(2, programId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
}
