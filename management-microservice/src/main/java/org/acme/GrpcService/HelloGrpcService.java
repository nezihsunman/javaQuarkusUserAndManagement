package org.acme.GrpcService;

import io.quarkus.grpc.GrpcService;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import org.acme.HelloGrpc;
import org.acme.HelloReply;
import org.acme.HelloRequest;
import org.acme.entity.Dashboard;

import java.util.ArrayList;
import java.util.List;


@GrpcService
public class HelloGrpcService implements HelloGrpc {

    @Override
    public Uni<HelloReply> sayHello(HelloRequest request) {
        return Uni.createFrom().item(()-> {
                    List<Dashboard> item = Dashboard.getAllList();
                    return item;
                })
                .runSubscriptionOn(Infrastructure.getDefaultExecutor())
                .onItem().transform(i -> HelloReply.newBuilder().setMessage("" + i.stream().map(y -> y.getUserId().toString()).reduce("",String::concat)).build());

    }

}