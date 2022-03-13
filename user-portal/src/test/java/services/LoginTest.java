package services;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.runtime.session.ForwardingSession;
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
import org.apache.kafka.common.utils.Java;
import org.hibernate.Session;
import org.hibernate.query.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.*;

@QuarkusTest
class LoginTest {

    @InjectMock
    Session session;

    @InjectMock
    EntityManager entityManager;

    @InjectMock
    Utils utils;

    @BeforeEach
    public void setup() {
    }
    @Test
    void login() {
        UserEntry entry = new UserEntry();
        entry.setId(0L);
        entry.setApproved(Approved.APPROVED);
        entry.setLastName("mock");
        entry.setPassword(BcryptUtil.bcryptHash("123456"));
        entry.setDisabledOrEnabled(DisabledOrEnabled.ENABLED);
        entry.setUsername("mock");

        Query mockQuery = Mockito.mock(Query.class);
        Mockito.doNothing().when(session).persist(Mockito.any());
        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(0l);

        Mockito.when(entityManager.createQuery(anyString(), any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(entry);
        //Setup data
        LoginDto loginDto = new LoginDto("mock", "123456");
        given().contentType(ContentType.JSON).body(loginDto)
                .when()
                .post("/login")
                .then().statusCode(200);
    }
    @Test
    void notLogin() {
        UserEntry entry = new UserEntry();
        entry.setId(0L);
        entry.setApproved(Approved.NOT_APPROVED);
        entry.setLastName("mock");
        entry.setPassword(BcryptUtil.bcryptHash("123456"));
        entry.setDisabledOrEnabled(DisabledOrEnabled.ENABLED);
        entry.setUsername("mock");

        Query mockQuery = Mockito.mock(Query.class);
        Mockito.doNothing().when(session).persist(Mockito.any());
        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(0l);

        try (MockedStatic<Utils> utilsMockedStatic = Mockito.mockStatic(Utils.class)) {
            utilsMockedStatic.when(
                    (MockedStatic.Verification) Utils.getEntityByUserName(null, "mock")
            ).thenReturn(entry);
            /*.when(() -> Utils.getEntityByUserName(entityManager,"mock")).thenReturn(entry);*/

        };
        //Query query = Mockito.mock(Query.class);
        Mockito.when(entityManager.createQuery(anyString(), any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(entry);
        //Setup data
        LoginDto loginDto = new LoginDto("mock", "123456");
        given().contentType(ContentType.JSON).body(loginDto)
                .when()
                .post("/login")
                .then().statusCode(401);
    }
    @Test
    void notLogin2() {
        UserEntry entry = new UserEntry();
        entry.setId(0L);
        entry.setApproved(Approved.APPROVED);
        entry.setLastName("mock");
        entry.setPassword(BcryptUtil.bcryptHash("123456"));
        entry.setDisabledOrEnabled(DisabledOrEnabled.DISABLED);
        entry.setUsername("mock");

        Query mockQuery = Mockito.mock(Query.class);
        Mockito.doNothing().when(session).persist(Mockito.any());
        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(0l);

        try (MockedStatic<Utils> utilsMockedStatic = Mockito.mockStatic(Utils.class)) {
            utilsMockedStatic.when(
                    (MockedStatic.Verification) Utils.getEntityByUserName(null, "mock")
            ).thenReturn(entry);
            /*.when(() -> Utils.getEntityByUserName(entityManager,"mock")).thenReturn(entry);*/

        };
        //Query query = Mockito.mock(Query.class);
        Mockito.when(entityManager.createQuery(anyString(), any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(entry);
        //Setup data
        LoginDto loginDto = new LoginDto("mock", "123456");
        given().contentType(ContentType.JSON).body(loginDto)
                .when()
                .post("/login")
                .then().statusCode(401);
    }
    @Test
    void notLogin3() {
        UserEntry entry = new UserEntry();
        entry.setId(0L);
        entry.setApproved(Approved.APPROVED);
        entry.setLastName("mock");
        entry.setPassword(BcryptUtil.bcryptHash("1234256"));
        entry.setDisabledOrEnabled(DisabledOrEnabled.ENABLED);
        entry.setUsername("mock");

        Query mockQuery = Mockito.mock(Query.class);
        Mockito.doNothing().when(session).persist(Mockito.any());
        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(0l);

        try (MockedStatic<Utils> utilsMockedStatic = Mockito.mockStatic(Utils.class)) {
            utilsMockedStatic.when(
                    (MockedStatic.Verification) Utils.getEntityByUserName(null, "mock")
            ).thenReturn(entry);
            /*.when(() -> Utils.getEntityByUserName(entityManager,"mock")).thenReturn(entry);*/

        };
        //Query query = Mockito.mock(Query.class);
        Mockito.when(entityManager.createQuery(anyString(), any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(entry);
        //Setup data
        LoginDto loginDto = new LoginDto("mock", "123456");
        given().contentType(ContentType.JSON).body(loginDto)
                .when()
                .post("/login")
                .then().statusCode(401);
    }
}