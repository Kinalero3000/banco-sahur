package main.java.com.jgunzalesindustries.banco.sahur.model;
 
public class User {

    private String userID;

    private String name;

    private String lastName;

    private String email;

    private String passwordHash;

    private String rolID;
 
    public User(String userID, String name, String lastName, String email, String passwordHash, String rolID) {

        this.userID = userID;

        this.name = name;

        this.lastName = lastName;

        this.email = email;

        this.passwordHash = passwordHash;

        this.rolID = rolID;

    }
 
    public String getUserID() {

        return userID;

    }
 
    public void setUserID(String userID) {

        this.userID = userID;

    }
 
    public String getName() {

        return name;

    }
 
    public void setName(String name) {

        this.name = name;

    }
 
    public String getLastName() {

        return lastName;

    }
 
    public void setLastName(String lastName) {

        this.lastName = lastName;

    }
 
    public String getEmail() {

        return email;

    }
 
    public void setEmail(String email) {

        this.email = email;

    }
 
    public String getPasswordHash() {

        return passwordHash;

    }
 
    public void setPasswordHash(String passwordHash) {

        this.passwordHash = passwordHash;

    }
 
    public String getRolID() {

        return rolID;

    }
 
    public void setRolID(String rolID) {

        this.rolID = rolID;

    }


}
 