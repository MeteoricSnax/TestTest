package datamapper;

import datasource.DataSource1;
import entity.User;

public class DataMapperTest
{
    public static void main(String[] args)
    {
        DataMapper dm = new DataMapper(new DataSource1().getDataSource());

        System.out.println("getUsers: " + dm.getUsers());
        System.out.println("getUserByName: " + dm.getUser("Anders And"));
        System.out.println("getUserById: " + dm.getUser(1));
        System.out.println("createUser: " + dm.createUser(new User("Chip", "1234", true)));
        System.out.println("updateUser: " + dm.updateUser(new User(9, "Chap", "1234", false)));
        System.out.println("deleteUser: " + dm.deleteUser(1));
        System.out.println("deleteUser: " + dm.validateUser("Chap", "1234"));
    }
}