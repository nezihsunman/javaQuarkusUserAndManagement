package org.acme.kafkaSendDataToManegamentMicSer;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class KafkaUserCreation {

    @Inject
    @Channel("user-out")
    Emitter<Long> emitter;

    public CompletionStage<Void> generate(long userId) {
        return emitter.send(userId);
    }
}
