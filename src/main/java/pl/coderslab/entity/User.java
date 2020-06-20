package pl.coderslab.entity;

import pl.coderslab.workshop2.BCrypt;

public class User {
    private int id = 0;
    private String email;
    private String userName;
    private String password;


    public User( String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public User(int id, String email, String userName, String password) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static String hashPass(String pass){
        return BCrypt.hashpw(pass,BCrypt.gensalt());

    }


    @Override
    public String toString() {
        return "ID: "+this.id+" emial: "+this.email+" Name: "+this.userName+"\n";
    }
}
