package services;
import io.quarkus.logging.Log;
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
        var userId = UserEntry.add(userDTO.email, userDTO.password, userDTO.firstName, userDTO.lastName);
        var res = kafkaUserCreation.generate(userId);
        Log.info("res from kafka" + res.toString());
        return Response.ok().build();
    }
}