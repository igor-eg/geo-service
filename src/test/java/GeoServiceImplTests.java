import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import java.util.stream.Stream;

public class GeoServiceImplTests {

    GeoServiceImpl sut;
    private static long suiteStartTime;
    private long testStartTime;


    @BeforeAll
    public static void initSuite() {
        System.out.println("Running StringTest");
        suiteStartTime = System.nanoTime();

    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("StringTest complete: " + (System.nanoTime() - suiteStartTime));
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Starting new nest");
        testStartTime = System.nanoTime();
        sut = new GeoServiceImpl();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete:" + (System.nanoTime() - testStartTime));
    }


    @ParameterizedTest
    @MethodSource("source")

    public void checking_location_detection_by_ip(String ip, Location expected) {
        // when:
        Location result = sut.byIp(ip);


        // then:
        Assertions.assertEquals(expected.getCity(), result.getCity());
        Assertions.assertEquals(expected.getCountry(), result.getCountry());
        Assertions.assertEquals(expected.getStreet(), result.getStreet());
        Assertions.assertEquals(expected.getBuiling(), result.getBuiling());
    }


    private static Stream<Arguments> source() {

        return Stream.of(Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)));


    }

    @ParameterizedTest
    @MethodSource("source1")

    public void checking_the_method_that_determines_the_location_by_coordinates(double latitude, double longitude) {

        var expected = RuntimeException.class;

        // then:
        Assertions.assertThrows(expected,
                // when:
                () -> sut.byCoordinates(latitude, longitude));


    }

    private static Stream<Arguments> source1() {

        return Stream.of(Arguments.of(55.45, 99.08), Arguments.of(34.45, 34.45));
    }
}

