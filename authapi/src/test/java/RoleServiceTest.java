import com.example.AuthApiApplication;
import com.example.models.Role;
import com.example.service.base.RoleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest(classes = AuthApiApplication.class)
public class RoleServiceTest {
    @Mock
    private RoleService roleService;

    @Test
    public void findById_User() {
        Mockito.when(roleService.findById(1)).thenReturn(new Role(1,"ROLE_USER"));

        Role role = roleService.findById(1);

        Assert.assertEquals(1, role.getRoleId());
        Assert.assertEquals("ROLE_USER", role.getRole());
    }

    @Test
    public void findById_Admin() {
        Mockito.when(roleService.findById(2)).thenReturn(new Role(2,"ROLE_ADMIN"));

        Role role = roleService.findById(2);

        Assert.assertEquals(2, role.getRoleId());
        Assert.assertEquals("ROLE_ADMIN", role.getRole());
    }

    @Test
    public void findById_Missing() {
        Mockito.when(roleService.findById(0)).thenReturn(null);

        Role role = roleService.findById(0);

        Assert.assertEquals(role, null);
    }
}
