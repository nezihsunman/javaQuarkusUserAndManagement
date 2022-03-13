package services;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import org.acme.Utils.Utils;
import org.acme.dto.LoginDto;
import org.acme.dto.UserDTO;
import org.acme.entity.UserEntry;
import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Login {
    public Login(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Inject
    EntityManager entityManager;

    @POST
    @Transactional
    public Response login(LoginDto loginDto) {
        UserEntry entity = Utils.getEntityByUserName(this.entityManager, loginDto.email);
        if (BcryptUtil.matches(loginDto.password,entity.password) && entity.getDisabledOrEnabled().equals(DisabledOrEnabled.ENABLED) && entity.getApproved().equals(Approved.APPROVED)  ){
            return Response.status(200,"Successfully login").build();
        }
        else if (!(BcryptUtil.matches(loginDto.password,entity.password))) {
            //successfully login
            return Response.status(401,"Password not correct").build();
        }
        else if (entity.getDisabledOrEnabled().equals(DisabledOrEnabled.DISABLED)) {
            return Response.status(401,"Your user is disabled").build();
        }
        return Response.status(401,"Your user is not approved by admin").build();


    }
}
