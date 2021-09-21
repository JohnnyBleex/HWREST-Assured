package breakingbadapi;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;

@Execution(ExecutionMode.CONCURRENT)
public class BreakingBadApiBrokenTest {
    private final String URL = "https://www.breakingbadapi.com/api/characters";

    @ParameterizedTest
    @MethodSource("dataFromGetCharactersNameAndChekName")
    public void getCharactersNameAndChekName(String name, String expectedName) {
        RestAssured
                .given()
                .log().uri()
                .when()
                .get(URL+"?name="+name)
                .then()
                .log().status()
                .log().body()
                .spec(
                        new ResponseSpecBuilder()
                                .expectStatusCode(400) //ожидаемый код 400, актуальный 200
                                .expectBody("[0].name",equalTo(expectedName))
                                .build()
                );
    }

    private static Stream<Arguments> dataFromGetCharactersNameAndChekName(){
        return Stream.of(
                Arguments.of("Walter White","walter White"), //имя со строчной буквы
                Arguments.of("Jesse Pinkman","Jesse Pinkman"),
                Arguments.of("Skyler White","Skyler White"),
                Arguments.of("Walter White Jr.","Walter White Jr."),
                Arguments.of("","Henry Schrader"), // нет имени для url запроса
                Arguments.of("Marie Schrader","Marie Schrader"),
                Arguments.of("Mike Ehrmantraut","Mike Ehrmantraut"),
                Arguments.of("Saul Goodman","Saul Goodman")
        );
    }
}
