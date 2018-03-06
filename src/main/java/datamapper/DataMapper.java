package datamapper;

import entity.User;
import java.util.ArrayList;
import dbconnector.DBConnector;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataMapper implements DataMapperInterface
{
    private DBConnector dbc = new DBConnector();

    public DataMapper(DataSource ds)
    {
        dbc.setDataSource(ds);
    }



    @Override
    public ArrayList<User> getUsers()
    {
        ArrayList<User> users = new ArrayList();

        try
        {
            dbc.open();

            String sql = "select * from user";
            ResultSet resultset = dbc.executeQuery(sql);

            while (resultset.next())
            {
                int userid = resultset.getInt("user.user_id");
                String username = resultset.getString("username");
                String userpassword = resultset.getString("password");
                boolean admin = resultset.getBoolean("admin");
                int userbalance = resultset.getInt("balance");

                User u = new User(userid, username, userpassword, admin);

                users.add(u);
            }

            dbc.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public ArrayList<User> getUsers(String un)
    {
        ArrayList<User> users = new ArrayList();

        try
        {
            dbc.open();

            String sql = "select * from user where username like '%" + un + "%'";
            ResultSet resultset = dbc.executeQuery(sql);

            while (resultset.next())
            {
                int userid = resultset.getInt("user.user_id");
                String username = resultset.getString("username");
                String userpassword = resultset.getString("password");
                boolean admin = resultset.getBoolean("admin");
                int userbalance = resultset.getInt("balance");

                User u = new User(userid, username, userpassword, admin);

                users.add(u);
            }

            dbc.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User getUser(int id)
    {
        User u = null;
        
        try
        {
            dbc.open();

            String sql = "select * from user where user_id = " + id;
            ResultSet resultset = dbc.executeQuery(sql);

            while (resultset.next())
            {
                int userid = resultset.getInt("user_id");
                String username = resultset.getString("username");
                String userpassword = resultset.getString("password");
                boolean admin = resultset.getBoolean("admin");
                int userbalance = resultset.getInt("balance");

                u = new User(userid, username, userpassword, admin);
            }

            dbc.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return u;
    }

    @Override
    public User getUser(String name)
    {
        try
        {
            dbc.open();

            String sql = "select * from user where username = '" + name + "'";
            ResultSet resultset = dbc.executeQuery(sql);

            while (resultset.next())
            {
                int userid = resultset.getInt("user.user_id");
                String username = resultset.getString("username");
                String userpassword = resultset.getString("password");
                boolean admin = resultset.getBoolean("admin");
                int userbalance = resultset.getInt("balance");

                return new User(userid, username, userpassword, admin);
            }

            dbc.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteUser(int id)
    {
        try
        {
            dbc.open();

            String sql = "delete from team_user where user_id = " + id + ";";
            dbc.executeUpdate(sql);

            sql = "delete from user where user_id = " + id + ";";
            dbc.executeUpdate(sql);

            dbc.close();

            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateUser(User u)
    {
        try
        {
            dbc.open();

            String sql = "update user set "
                    + "username = '" + u.getUsername() + "', "
                    + "password = '" + u.getPassword() + "', "
                    + "admin = " + u.isAdmin() + " "
                    + "where user_id = " + u.getId();

            dbc.executeUpdate(sql);

            dbc.close();

            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean createUser(User u)
    {
        try
        {
            dbc.open();

            String sql = "insert into user values(null, "
                    + "'" + u.getUsername() + "', "
                    + "'" + u.getPassword() + "', "
                    + u.isAdmin() + ", "
                    + u.getBalance() + ")";
//                    + "'" + u.getEmail() + "')";

            dbc.executeUpdate(sql);

            dbc.close();

            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    
    public User validateUser(String username, String password)
    {
        User user = null;
        
        try
        {
            dbc.open();
            
            /*
            String sql = "select * from user where username = '" + username + "' and password = '" + password + "'";
            System.out.println("SQL: " + sql);
            ResultSet resultSet = dbc.executeQuery(sql);
            */
            
            //PreparedStatement
            String sql = "select * from user where username = ? and password = ?";
            PreparedStatement preparedStatement = dbc.preparedStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next())
            {
                int id = resultSet.getInt("user_id");
                boolean admin = resultSet.getInt("admin") > 0;
                int userbalance = resultSet.getInt("balance");
                
                user = new User(id, username, password, admin);
            }

            dbc.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return user;
    }
}