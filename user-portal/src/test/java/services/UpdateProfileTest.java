package services;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.runtime.Application;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.acme.dto.UserDTO;
import org.acme.entity.UserEntry;
import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
class UpdateProfileTest {

    @InjectMock
    Session session;

    @InjectMock
    EntityManager entityManager;

    @Test
    void update() {
        PanacheMock.mock(UserEntry.class);
        UserDTO userDTO = new UserDTO();
        userDTO.email = "mock";
        userDTO.lastName = "mo234ck";
        userDTO.password = "123456";

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

        Mockito.when(entityManager.createQuery(anyString(), any())).thenReturn(mockQuery);
        Mockito.when(mockQuery.getSingleResult()).thenReturn(entry);

        given().contentType(ContentType.JSON).body(userDTO).when().post("/update").then().statusCode(200);
    }
}