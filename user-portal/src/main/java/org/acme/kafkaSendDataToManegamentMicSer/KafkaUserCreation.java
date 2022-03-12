package org.acme.kafkaSendDataToManegamentMicSer;

import org.acme.dto.CreatedUserDto;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


@ApplicationScoped
public class KafkaUserCreation {

    @Inject
    @Channel("user-out")
    Emitter<CreatedUserDto> emitter;

    public CompletionStage<Void> generate(CreatedUserDto createdUserDto) {
        return emitter.send(createdUserDto);
    }
}
