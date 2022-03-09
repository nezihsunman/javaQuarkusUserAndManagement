package services;

import org.acme.Utils.Utils;
import org.acme.dto.UserDTO;
import org.acme.entity.UserEntry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/update")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UpdateProfile {

    public UpdateProfile(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Inject
    EntityManager entityManager;

    @POST
    @Transactional
    public Response update(UserDTO userDTO) {
        UserEntry entry = Utils.getEntityByUserName(this.entityManager, userDTO.email);
        if (entry != null) {
            serializeDTO(userDTO, entry);
            entry.persist();
            return Response.ok(entry).build();
        }
        return Response.ok("Email not found").build();
    }

    private void serializeDTO(UserDTO userDTO, UserEntry entry) {
        entry.setPassword(userDTO.password);
        entry.setUsername(userDTO.email);
        entry.setFirstName(userDTO.firstName);
        entry.setLastName(userDTO.lastName);
    }
}
