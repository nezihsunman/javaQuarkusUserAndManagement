package org.acme.dto.Deserializer;

import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;
import org.acme.dto.CreatedUserDto;

public class CreatedUserSerializer extends ObjectMapperSerializer<CreatedUserDto> {
    public CreatedUserSerializer() {
    }
}
