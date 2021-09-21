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
public class BreakingBadApiTest {
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
                        .expectStatusCode(200)
                        .expectBody("[0].name",equalTo(expectedName))
                        .build()
                );
    }

    private static Stream<Arguments> dataFromGetCharactersNameAndChekName(){
        return Stream.of(
                Arguments.of("Walter White","Walter White"),
                Arguments.of("Jesse Pinkman","Jesse Pinkman"),
                Arguments.of("Skyler White","Skyler White"),
                Arguments.of("Walter White Jr.","Walter White Jr."),
                Arguments.of("Henry Schrader","Henry Schrader"),
                Arguments.of("Marie Schrader","Marie Schrader"),
                Arguments.of("Mike Ehrmantraut","Mike Ehrmantraut"),
                Arguments.of("Saul Goodman","Saul Goodman")
        );
    }

    @ParameterizedTest
    @MethodSource("dataFromGetCharactersNickNameAndChekNickName")
    public void getCharactersNickNameAndChekNickName(String name, String expectedNickName) {
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
                                .expectStatusCode(200)
                                .expectBody("[0].nickname",equalTo(expectedNickName))
                                .build()
                );
    }

    private static Stream<Arguments> dataFromGetCharactersNickNameAndChekNickName(){
        return Stream.of(
                Arguments.of("Walter White","Heisenberg"),
                Arguments.of("Jesse Pinkman","Cap n' Cook"),
                Arguments.of("Skyler White","Sky"),
                Arguments.of("Walter White Jr.","Flynn"),
                Arguments.of("Henry Schrader","Hank"),
                Arguments.of("Marie Schrader","Marie"),
                Arguments.of("Mike Ehrmantraut","Mike"),
                Arguments.of("Saul Goodman","Jimmy McGill")
        );
    }

    @ParameterizedTest
    @MethodSource("dataFromGetCharactersStatusAndChekStatus")
    public void getCharactersStatusAndChekStatus(String name, String expectedStatus){
        RestAssured
                .given()
                .log().uri()
                .when()
                .get(URL +"?name="+name)
                .then()
                .log().status()
                .log().body()
                .spec(
                        new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectBody("[0].status", equalTo(expectedStatus))
                        .build()
                );
    }

    private static Stream<Arguments> dataFromGetCharactersStatusAndChekStatus(){
        return Stream.of(
                Arguments.of("Walter White","Presumed dead"),
                Arguments.of("Jesse Pinkman","Alive"),
                Arguments.of("Skyler White","Alive"),
                Arguments.of("Walter White Jr.","Alive"),
                Arguments.of("Henry Schrader","Deceased"),
                Arguments.of("Marie Schrader","Alive"),
                Arguments.of("Mike Ehrmantraut","Deceased"),
                Arguments.of("Saul Goodman","Alive")
        );
    }
}
