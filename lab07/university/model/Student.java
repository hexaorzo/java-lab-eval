package lab07.university.model;




public class Student extends UniversityMember {

    private String program;

    public Student(String studentId, String name, String program, String email) {
        super(studentId, name, email);
        this.program = program.trim().toUpperCase();
    }

    public String getStudentId() {
        return getMemberId();
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program.trim().toUpperCase();
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public void displayProfile() {
        displayCommonProfile();
        System.out.println("| Program: " + program);
        System.out.println("+--------------------------------------------------");
    }

    
    public void displayStudent() {
        System.out.printf("%-8s | %-20s | %-6s | %s%s%n", getMemberId(), getName(), program, getEmail(),
                hasValidEmail() ? "" : "  (invalid email)");
    }
}
