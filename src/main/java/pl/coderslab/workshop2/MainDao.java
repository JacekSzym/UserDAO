package pl.coderslab.workshop2;

import org.apache.commons.lang3.ArrayUtils;
import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class MainDao {

    public static void main(String[] args) {

        User user1 = new User("jac@o2.pl", "Jaca Placa","123");
        User user2 = new User("paeel@dupa.pl", "Dawid Srawid","12322");
        User user3 = new User("123@gmail.com", "Ania Bania","12322");
        User user4 = new User("elleleja@uu.pl", "Dupa Dupa","12223");


        UserDao.create(user1);
        UserDao.create(user2);
        UserDao.create(user3);
        UserDao.create(user4);

        user1 = UserDao.read(10);
    //    UserDao.update(user1);

        UserDao.read(10);


        UserDao.delete(61);

        //UserDao.findAll();

        System.out.println(ArrayUtils.toString( UserDao.findAll() ) );


    }

}
