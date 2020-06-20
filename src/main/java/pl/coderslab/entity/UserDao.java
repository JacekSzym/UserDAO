package pl.coderslab.entity;

import org.apache.commons.lang3.ArrayUtils;
import pl.coderslab.workshop2.ConsoleColors;
import pl.coderslab.workshop2.DBUtil;

import java.io.Console;
import java.sql.*;
import java.util.Scanner;

public class UserDao {
    private static final String CREATE_USER = "INSERT INTO users(email, username, password) VALUES (?,?,?)";
    private static final String SELECT_USER = "SELECT id,email,username,password FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT id,email,username,password FROM users";
    private static final String UPDATE_USER = "UPDATE users SET  email = ?, username = ?, password = ?  WHERE id = ? ";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ? ";
    private static final String DB_NAME = "workshop2";


    public static User create(User user) {
        int count = 0;

        try (Connection conn = DBUtil.getConnection(DB_NAME)) {

            PreparedStatement stat = conn.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);

            stat.setString(1, user.getEmail());
            stat.setString(2, user.getUserName());
            stat.setString(3, user.hashPass(user.getPassword()));


            try {
                count = stat.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("There already exists user with email: " + user.getEmail());
            }

            ResultSet resultSet = stat.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }

            if (!(user.getId() == 0)) {
                getLog(user, "Inserting user success!");
            } else {
                getLog(user, "Inserting user faild!");
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User read(int userId) {

        try (Connection conn = DBUtil.getConnection(DB_NAME)) {

            PreparedStatement stat = conn.prepareStatement(SELECT_USER);
            stat.setInt(1, userId);

            try {
                ResultSet rs = stat.executeQuery();

                rs.next();

                User user = new User(rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"));

                getLog(user, "Reading user success !");
                return user;

            } catch (SQLException e){
                getLog(null,"Reading user faild !") ;
                System.out.println("There's no user with id "+userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void update (User user){

        String newEmail;
        String newName;
        String newPassword;

        Scanner scan = new Scanner(System.in);

        System.out.println(ConsoleColors.BLUE+"Type new email: ");
        newEmail = scan.nextLine();
        System.out.println("Type new username: ");
        newName = scan.nextLine();
        System.out.println("Type new password: "+ ConsoleColors.RESET);
        newPassword = scan.nextLine();


        try (Connection conn = DBUtil.getConnection(DB_NAME)){
            PreparedStatement stat = conn.prepareStatement(UPDATE_USER);

        if ( !(newEmail.equals("")) ){
            stat.setString(1,newEmail);
            user.setEmail(newEmail);
        } else{
            stat.setString(1,user.getEmail());
        }

        if ( !(newName.equals("")) ){
            stat.setString(2,newName);
            user.setUserName(newName);
        } else {
            stat.setString(2,user.getUserName());
        }

        if ( !(newPassword.equals("")) ){
            String hashPass = user.hashPass(newPassword);
            stat.setString(3,hashPass);
            user.setPassword(hashPass);
        } else {
            stat.setString(3,user.getPassword());
        }

            stat.setInt(4,user.getId());

            getLog(user,"Updating user: "+user.getId() );
            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void delete(int userID){
        int count = 0;


        try  ( Connection conn = DBUtil.getConnection(DB_NAME) ) {

            PreparedStatement stat = conn.prepareStatement(DELETE_USER);

            stat.setInt(1,userID);

           count = stat.executeUpdate();

           if (count == 0){
               System.out.println("There is no user with ID "+userID);
           } else{
               System.out.println("User "+userID+" successfully deleted !");
           }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static User[] findAll(){
        User[] users = new User[0];

        try ( Connection conn = DBUtil.getConnection(DB_NAME) ) {

            PreparedStatement stat = conn.prepareStatement(SELECT_ALL_USERS);

            ResultSet rs =  stat.executeQuery();

            while ( rs.next() ) {

                // id,email,username,password

                User newUser = new User(rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"));

                users = ArrayUtils.add(users, newUser);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;

    }





    public static void getLog(User user, String operation) {

       try{
           System.out.println(operation+" "+user.toString());
       } catch (NullPointerException e){
           System.out.println(operation);
       }

    }


}
