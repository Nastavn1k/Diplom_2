package models;

public class AuthorizationModel {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public AuthorizationModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthorizationModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
