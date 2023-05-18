package pl.patrykdepka.iteventsapp.appuser.model;

public enum Role {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
