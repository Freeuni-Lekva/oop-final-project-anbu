package quizapp.models.domain;

import java.util.Date;

public class User {
    public int id;
    public String username;
    public String passwordHash;
    public boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void makeAdmin() {
        isAdmin = true;
    }

    public Date registrationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
