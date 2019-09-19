import com.example.models.Role;
import com.example.models.User;
import com.example.service.base.RoleService;
import com.example.service.base.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserService userService;

    @Test
    public void loadUserByUsername() {
        Mockito.when(userService.loadUserByUsername("username1")).thenReturn(new User(1, "full name", "username1", "password1", "email1@email.com"));

        User role = userService.loadUserByUsername("username1");

        Assert.assertEquals("username1", role.getUsername());
        Assert.assertEquals("password1", role.getPassword());
    }

}
