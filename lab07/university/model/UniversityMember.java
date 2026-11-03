package lab07.university.model;





public abstract class UniversityMember {

    private final String memberId;
    private String name;
    private String email;

    protected UniversityMember(String memberId, String name, String email) {
        this.memberId = memberId.trim().toUpperCase();
        this.name = name.trim().replaceAll("\\s+", " ");
        this.email = email.trim().toLowerCase();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim().replaceAll("\\s+", " ");
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

    
    public boolean matchesName(String searchName) {
        String searchText = searchName.trim().toLowerCase();
        return !searchText.isEmpty() && name.toLowerCase().contains(searchText);
    }

    
    public abstract String getRole();

    
    public abstract void displayProfile();

    
    public void displaySummary() {
        System.out.printf("%-8s | %-8s | %-20s | %s%s%n", memberId, getRole(), name, email,
                hasValidEmail() ? "" : "  (invalid email)");
    }

    
    protected void displayCommonProfile() {
        System.out.println("+---------------- " + getRole() + " Profile ----------------");
        System.out.println("| ID     : " + memberId);
        System.out.println("| Name   : " + name);
        System.out.println("| Email  : " + email + (hasValidEmail() ? "" : " (invalid)"));
    }
}
