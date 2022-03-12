package services;
import io.quarkus.logging.Log;
import io.vertx.ext.auth.User;
import org.acme.dto.CreatedUserDto;
import org.acme.dto.UserDTO;
import org.acme.entity.UserEntry;
import org.acme.kafkaSendDataToManegamentMicSer.KafkaUserCreation;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/registration")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Registration {
    public Registration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Inject
    EntityManager entityManager;

    @Inject
    KafkaUserCreation kafkaUserCreation;

    @POST
    @Transactional
    public Response add(UserDTO userDTO) {
        if (UserEntry.list("username", userDTO.email).size() == 0) {
            var userId = UserEntry.add(userDTO.email, userDTO.password, userDTO.firstName, userDTO.lastName);
            var res = kafkaUserCreation.generate(new CreatedUserDto(userId, userDTO.email));
            Response.ok().build();
        }
        return Response.status(409).build();
    }
}