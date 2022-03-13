package services;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.vertx.http.runtime.devmode.Json;
import io.restassured.http.ContentType;
import io.vertx.ext.auth.User;
import org.acme.Utils.Utils;
import org.acme.dto.LoginDto;
import org.acme.entity.UserEntry;
import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testcontainers.shaded.okhttp3.internal.Util;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.*;

@QuarkusTest
class LoginTest {

    @InjectMock
    EntityManager entityManager;
    @InjectMock
    Utils utils;

    @BeforeEach
    public void setup() {
        UserEntry entry = new UserEntry();
        entry.setId(0L);
        entry.setApproved(Approved.APPROVED);
        entry.setLastName("mock");
        entry.setPassword("123456");
        entry.setDisabledOrEnabled(DisabledOrEnabled.ENABLED);
        entry.setUsername("mock");

        try (MockedStatic<Utils> utilsMockedStatic = Mockito.mockStatic(Utils.class)) {
            utilsMockedStatic.when(
                    (MockedStatic.Verification) Utils.getEntityByUserName(null, "mock")
            ).thenReturn(entry);
                /*.when(() -> Utils.getEntityByUserName(entityManager,"mock")).thenReturn(entry);*/

        };
        TypedQuery<UserEntry> query = Mockito.mock(TypedQuery.class);
        //Query query = Mockito.mock(Query.class);
        Mockito.when(entityManager.createQuery(anyString())).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(entry);
    }
    @Test
    void login() {
        //Setup data
        /*LoginDto loginDto = new LoginDto("mock", "123456");
        given().contentType(ContentType.JSON).body(loginDto)
                .when()
                .post("/login")
                .then().statusCode(200);*/
    }
}