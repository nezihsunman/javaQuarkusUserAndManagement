package services;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.runtime.PanacheQueryImpl;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import io.vertx.ext.auth.User;
import org.acme.dto.UserDTO;
import org.acme.entity.UserEntry;
import org.acme.kafkaSendDataToManegamentMicSer.KafkaUserCreation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
class RegistrationTest {

    @InjectMock
    EntityManager entityManager;

    @InjectMock
    KafkaUserCreation kafkaUserCreation;

    @BeforeEach
    public void setup() {
        PanacheMock.mock(UserEntry.class);
    }


    @Test
    void add() {
        UserDTO userDTO = new UserDTO();
        userDTO.email = "mock";
        userDTO.lastName = "mock";
        userDTO.password = "123456";
        Mockito.when(UserEntry.list(Mockito.anyString(),Mockito.anyString())).thenReturn(Collections.emptyList());
        Mockito.when(kafkaUserCreation.generate(any())).thenReturn(null);
        Assertions.assertEquals(UserEntry.list(Mockito.anyString(), Mockito.anyString()).size(), 0);

        given().contentType(ContentType.JSON).body(userDTO)
                .when()
                .post("/registration")
                .then().statusCode(200);
    }
    @Test
    void notAdd() {
        UserDTO userDTO = new UserDTO();
        userDTO.email = "mock";
        userDTO.lastName = "mock";
        userDTO.password = "123456";

        List<PanacheEntityBase> entityBases = new ArrayList<>(5);
        entityBases.add(new UserEntry());
        entityBases.add(new UserEntry());
        Mockito.when(UserEntry.list(Mockito.anyString(), Mockito.anyString())).thenReturn(entityBases);
        Mockito.when(kafkaUserCreation.generate(any())).thenReturn(null);
        Assertions.assertEquals(2, UserEntry.list(Mockito.anyString(), Mockito.anyString()).size());

        given().contentType(ContentType.JSON).body(userDTO)
                .when()
                .post("/registration")
                .then().statusCode(409);
    }
}