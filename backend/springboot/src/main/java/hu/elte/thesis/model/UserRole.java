package hu.elte.thesis.model;

public enum UserRole {

    ADMINISTRATOR("administrator"),

    STUDENT("student"),

    SUPERVISOR("supervisor");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

}
