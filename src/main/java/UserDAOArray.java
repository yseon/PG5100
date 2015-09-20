import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ingar Thorsen on 17.09.15.
 */
@Alternative
public class UserDAOArray implements UserDAO {
    List<User> userList = new ArrayList<User>();

    @Override
    public boolean createUser(User user) {
        userList.add(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        User updatedUser = getUser(user.getId());
        userList.remove(user);
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setType(user.getType());
        userList.add(updatedUser);
        return true;
    }

    @Override
    public User getUser(int id) {
        for(User u : userList) {
            if(u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUser() {
        return userList;
    }

    @Override
    public boolean deleteUser(int id) {
        for(User u : userList) {
            if(u.getId() == id) {
                userList.remove(u);
                return true;
            }
        }
        return false;
    }
}
