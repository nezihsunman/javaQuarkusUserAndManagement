package org.acme.kafka;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import org.acme.entity.Dashboard;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class kafkaConsumer {

    @Incoming("user-in")
    @Blocking
    @Transactional
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public void store(long priceInUsd) {
        Dashboard price = new Dashboard();
        price.setUserId(priceInUsd);
        price.persist();
        Log.info("Created");
    }

}
