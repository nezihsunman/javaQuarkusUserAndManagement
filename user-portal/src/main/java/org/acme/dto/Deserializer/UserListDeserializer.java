package org.acme.dto.Deserializer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import org.acme.dto.UserListDto;

public class UserListDeserializer extends ObjectMapperDeserializer<UserListDto> {
    public UserListDeserializer()  {
        super(UserListDto.class);
    }
}
