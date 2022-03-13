package org.acme.GrpcService;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.acme.*;
import org.acme.entity.Dashboard;
import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;
import org.acme.kafka.KafkaConsumer;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.mockito.InjectMock;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ManagementGrpcServiceTest {

    ManagementGrpcService managementGrpcService;


    @Test
    void getListOfUser() {
        CompletableFuture<String> message = new CompletableFuture<>();
        var req = HelloRequest.newBuilder().build();
        this.managementGrpcService = new ManagementGrpcService();
        managementGrpcService.getListOfUser(req)
                .subscribe().with(reply -> message.complete(reply.getResponseList().toString()));
    }
    @Test
    void getListOfUserWithData() {
        PanacheMock.mock(Dashboard.class);
        List<Dashboard> dashboardList = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            Dashboard dashboard = new Dashboard();
            dashboard.setUsername("user" + i);
            dashboard.setUserId((long) i);
            dashboard.setApproved(Approved.APPROVED);
            dashboard.setDisabledOrEnabled(DisabledOrEnabled.ENABLED);
            dashboardList.add(dashboard);
        }

        Mockito.when(Dashboard.getAllList()).thenReturn(dashboardList);
        CompletableFuture<String> message = new CompletableFuture<>();
        var req = HelloRequest.newBuilder().build();
        this.managementGrpcService = new ManagementGrpcService();
        managementGrpcService.getListOfUser(req)
                .subscribe().with(reply -> message.complete(reply.getResponseList().toString()));
    }

    @Test
    void approveListOfUser() {
    }

    @Test
    void enableOrDisableUser() {
    }

}