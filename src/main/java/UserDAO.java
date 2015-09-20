import java.util.List;

/**
 * Created by Ingar Thorsen on 17.09.15.
 */
public interface UserDAO {
    public boolean createUser(User user);
    public boolean updateUser(User user);
    public User getUser(int id);
    public List<User> getAllUser();
    public boolean deleteUser(int id);
}
