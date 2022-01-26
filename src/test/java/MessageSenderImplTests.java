import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageSenderImplTests {

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
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete:" + (System.nanoTime() - testStartTime));
    }

//тесты для проверки языка отправляемого сообщения (класс MessageSender) с использованием Mockito

    @ParameterizedTest
    @MethodSource("source")

    public  void test_to_check_the_Russian_language_of_the_message_being_sent(String ip, Location location,
                                                                              Country country, String message) {
        // when:
        Map<String, String> headers = new HashMap<String, String>();

        GeoServiceImpl geoServiceIml = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoServiceIml.byIp(ip)).thenReturn(location);

        LocalizationServiceImpl localizationServiceIml = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationServiceIml.locale(country))
                .thenReturn(message);

        MessageSenderImpl messageSenderImpl = new MessageSenderImpl(geoServiceIml, localizationServiceIml);

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String preferences  = messageSenderImpl.send(new HashMap<>(headers));
        String expected  = message;

        // then:
        Assertions.assertEquals(expected, preferences);
    }


    private static Stream<Arguments> source() {

        return Stream.of(Arguments.of("172.123.12.19", new Location("Moscow", Country.RUSSIA, "Lenina", 15),
                        Country.RUSSIA, "Добро пожаловать"),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32),
                        Country.USA, "Welcome"));


    }


}

