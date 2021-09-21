package gameofthronesapi;

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
public class GameOfThronesApiTest {
    private final String URL = "https://anapioficeandfire.com/api/characters";

    @ParameterizedTest
    @MethodSource("dataFromGetCharactersNameAndChekName")
    public void getCharactersNameAndChekName(String name, String expectedName) {
        RestAssured
                .given()
                .log().uri()
                .when()
                .get(URL + "?name=" + name)
                .then()
                .log().status()
                .log().body()
                .spec(
                        new ResponseSpecBuilder()
                                .expectStatusCode(200)
                                .expectBody("[0].name", equalTo(expectedName))
                                .build()
                );
    }

    private static Stream<Arguments> dataFromGetCharactersNameAndChekName() {
        return Stream.of(
                Arguments.of("Jon Snow", "Jon Snow"),
                Arguments.of("Daenerys Targaryen", "Daenerys Targaryen"),
                Arguments.of("Sansa Stark", "Sansa Stark"),
                Arguments.of("Arya Stark", "Arya Stark"),
                Arguments.of("Robb Stark", "Robb Stark"),
                Arguments.of("Eddard Stark", "Eddard Stark"),
                Arguments.of("Catelyn Stark", "Catelyn Stark"),
                Arguments.of("Aegon Targaryen", "Aegon Targaryen")
        );
    }

    @ParameterizedTest
    @MethodSource("dataFromGetCharactersTitleAndChekTitle")
    public void getCharactersTitleAndChekTitle(String name, String expectedTitle) {
        RestAssured
                .given()
                .log().uri()
                .when()
                .get(URL + "?name=" + name)
                .then()
                .log().status()
                .log().body()
                .spec(
                        new ResponseSpecBuilder()
                                .expectStatusCode(200)
                                .expectBody("[0].titles[0]", equalTo(expectedTitle))
                                .build()
                );
    }

    private static Stream<Arguments> dataFromGetCharactersTitleAndChekTitle() {
        return Stream.of(
                Arguments.of("Jon Snow", "Lord Commander of the Night's Watch"),
                Arguments.of("Daenerys Targaryen", "Princess"),
                Arguments.of("Sansa Stark", "Princess"),
                Arguments.of("Arya Stark", "Princess"),
                Arguments.of("Robb Stark", "King in the North"),
                Arguments.of("Eddard Stark", "Lord of Winterfell"),
                Arguments.of("Catelyn Stark", "Lady of Winterfell"),
                Arguments.of("Aegon Targaryen", "")
        );
    }
}
