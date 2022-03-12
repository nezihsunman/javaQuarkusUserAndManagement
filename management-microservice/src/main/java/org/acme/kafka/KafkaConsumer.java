package org.acme.kafka;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.acme.DTOs.CreatedUserDto;
import org.acme.DTOs.UserListDto;
import org.acme.entity.Dashboard;
import org.eclipse.microprofile.reactive.messaging.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class KafkaConsumer {

    @Inject
    @Channel("take-user-name-out")
    Emitter<UserListDto> emitter;

    @Incoming("user-in")
    @Blocking
    @Transactional
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public void store(CreatedUserDto createdUserDto) {
        Dashboard dashboard = new Dashboard();
        dashboard.setUserId(createdUserDto.userId);
        dashboard.setUsername(createdUserDto.username);
        dashboard.persist();
        Log.info("Created");
    }

    public void approveUsers(UserListDto userIdList) {
        emitter.send(userIdList);
    }
}

