import java.sql.Connection;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseManager.getConnection();
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Welcome to the Feedback System");

            // Step 2: Roll No
            System.out.print("Enter Roll No: ");
            String rollNo = sc.nextLine();
            Student student = Student.findByRollNo(conn, rollNo);
            if (student == null) {
                System.out.println("You are not enrolled.");
                return;
            }

            // Step 3: Program
            System.out.print("Enter Program Name: ");
            String progName = sc.nextLine();
            TrainingProgram program = TrainingProgram.findByNameAndStudent(conn, progName, rollNo);
            if (program == null) {
                System.out.println("You are not enrolled for this program.");
                return;
            }

            // Step 4: Phase
            System.out.print("Enter Phase Name: ");
            String phaseName = sc.nextLine();
            FeedbackPhase phase = FeedbackPhase.findByNameAndProgram(conn, phaseName, program.getId());
            if (phase == null) {
                System.out.println("You are not enrolled in this phase.");
                return;
            }

            // Step 5: Questions + Feedback
            phase.takeFeedback(student, conn);

            System.out.println("Thank you! Your feedback has been submitted successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}