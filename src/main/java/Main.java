import com.sun.org.apache.xpath.internal.SourceTree;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Ingar Thorsen on 17.09.15.
 */
public class Main {
    private UserDAO userDAO;

    @Inject
    public Main(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void doStuff() {
        System.out.print("create: ");
        User user = new User(1, "ingar", "password", UserType.STUDENT);
        System.out.println(userDAO.createUser(user));

        System.out.print("get1: ");
        User get1 = userDAO.getUser(1);
        System.out.println(get1.toString());

        System.out.print("update: ");
        User update = new User(1, "ingar", "password", UserType.TEACHER);
        System.out.println(userDAO.updateUser(update));

        System.out.print("get2: ");
        User get2 = userDAO.getUser(1);
        System.out.println(get2.toString());

        System.out.print("size: ");
        List<User> users = userDAO.getAllUser();
        System.out.println(users.size());

        System.out.println();
        for(User u : users) {
            System.out.println(u.toString());
        }

        System.out.println();

        System.out.print("delete: ");
        System.out.println(userDAO.deleteUser(1));

        System.out.print("size: ");
        users = userDAO.getAllUser();
        System.out.println(users.size());

    }

    public static void main(String[] args) {
        WeldContainer weldContainer = new Weld().initialize();
        Instance<Main> main = weldContainer.instance().select(Main.class);
        main.get().doStuff();
    }
}
