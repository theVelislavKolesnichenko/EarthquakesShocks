import com.example.mapapi.MapApiAppication;
import com.example.mapapi.models.Location;
import com.example.mapapi.models.Shape;
import com.example.mapapi.models.enumerations.ShapeType;
import com.example.mapapi.service.ShapeServiceImpl;
import com.example.mapapi.service.base.ShapeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = MapApiAppication.class)
public class ShapeServiceImplTest {

    @Mock
    private ShapeServiceImpl shapeService;

    @Test
    public void findByLatLng_notFound() {
        Mockito.when(shapeService.findByLatLng(new Location(0.0, 0.0), ShapeType.Soil)).thenReturn(new Shape());

        Shape shape = shapeService.findByLatLng(new Location(0.0, 0.0), ShapeType.Soil);

        Assert.assertEquals(shape, new Shape());
        Mockito.verify(shapeService).findByLatLng(new Location(0.0, 0.0), ShapeType.Soil);
    }
}
