package org.acme.kafkaSendDataToManegamentMicSer;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.acme.dto.UserListDto;
import org.acme.entity.UserEntry;
import org.acme.enums.Approved;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class KafkaApprovedUserByAdmin {
    @Incoming("take-user-name-in")
    @Blocking
    @Transactional
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public void approved(UserListDto userListDto) {
        try {
            UserEntry entry = UserEntry.findById(userListDto.userId);
            entry.setApproved(userListDto.approved);
            entry.setDisabledOrEnabled(userListDto.disabledOrEnabled);
            entry.persist();
        }
        catch (Exception e) {
            Log.error(e.getMessage());
        }
    }
}
