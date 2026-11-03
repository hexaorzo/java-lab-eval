package lab06.university.model;




public class Student {

    private String studentId;
    private String studentName;
    private String program;
    private String email;

    public Student(String studentId, String studentName, String program, String email) {
        
        this.studentId = studentId.trim().toUpperCase();
        this.studentName = studentName.trim().replaceAll("\\s+", " ");
        this.program = program.trim().toUpperCase();
        this.email = email.trim().toLowerCase();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName.trim().replaceAll("\\s+", " ");
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program.trim().toUpperCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim().toLowerCase();
    }

    public boolean hasValidEmail() {
        int atPosition = email.indexOf('@');
        return atPosition > 0 && email.lastIndexOf('.') > atPosition + 1 && !email.endsWith(".");
    }

    public void displayStudent() {
        System.out.printf("%-8s | %-20s | %-6s | %s%s%n", studentId, studentName, program, email,
                hasValidEmail() ? "" : "  (invalid email)");
    }
}
