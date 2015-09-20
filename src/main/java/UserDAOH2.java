import javax.enterprise.inject.Alternative;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ingar Thorsen on 17.09.15.
 */
@Alternative
public class UserDAOH2 implements UserDAO {
    @Override
    public boolean createUser(User user) {
        int id = user.getId();
        String email = user.getEmail();
        String password = user.getPassword();
        UserType type = user.getType();

        try (Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/egentrening", "egen", "trening")) {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO user VALUES(" + id + ", '" + email + "', '" + password + "', '" + type.toString() + "')";
            statement.execute(sql);
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        int id = user.getId();
        String email = user.getEmail();
        String password = user.getPassword();
        UserType type = user.getType();

        try (Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/egentrening", "egen", "trening")) {
            Statement statement = connection.createStatement();
            //statement.execute("UPDATE user SET email = '" + email + "', SET password = '" + password + "', SET type = '" + type.toString() + "' WHERE id = " + id);
            statement.execute("UPDATE user SET email = '" + email + "' WHERE id = " + id);
            statement.execute("UPDATE user SET password = '" + password + "' WHERE id = " + id);
            statement.execute("UPDATE user SET type = '" + type.toString() + "' WHERE id = " + id);
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(int id) {
        User user;
        UserType type;

        try (Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/egentrening", "egen", "trening")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE id = " + id);

            resultSet.next();

            if (resultSet.getString(4).equals("TEACHER")) {
                type = UserType.TEACHER;
            } else {
                type = UserType.STUDENT;
            }

            user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), type);

            return user;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAllUser() {
        List<User> userList = new ArrayList<User>();

        try (Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/egentrening", "egen", "trening")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

            while (resultSet.next()) {
                User user;
                UserType type;

                if (resultSet.getString(4).equals("TEACHER")) {
                    type = UserType.TEACHER;
                } else {
                    type = UserType.STUDENT;
                }

                user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), type);

                userList.add(user);
            }
            return userList;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/egentrening", "egen", "trening")) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM user WHERE id = " + id);

            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }
}
