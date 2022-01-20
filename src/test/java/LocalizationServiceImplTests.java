import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceImplTests {
    LocalizationServiceImpl sut = new LocalizationServiceImpl();
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


    @ParameterizedTest
    @MethodSource("source")

    public void checking_the_returned_tex(Country country, String expected) {
        // when:
        String result = sut.locale(country);


        // then:
        Assertions.assertEquals(expected, result);

    }


    private static Stream<Arguments> source() {

        return Stream.of(Arguments.of(Country.RUSSIA, "Добро пожаловать"), Arguments.of(Country.USA, "Welcome"));


    }
}

