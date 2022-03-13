package org.acme.GrpcService;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.acme.*;
import org.acme.DTOs.UserListDto;
import org.acme.entity.Dashboard;
import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;
import org.acme.kafka.KafkaConsumer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.mockito.InjectMock;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class ManagementGrpcServiceTest {

    ManagementGrpcService managementGrpcService;

    @InjectMock
    Session session;
    @InjectMock
    KafkaConsumer kafkaConsumer;

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
        PanacheMock.mock(Dashboard.class);

        Dashboard dashboard = new Dashboard();
        dashboard.setUsername("user");
        dashboard.setUserId(1L);
        dashboard.setApproved(Approved.NOT_APPROVED);
        dashboard.setDisabledOrEnabled(DisabledOrEnabled.DISABLED);

        Mockito.when(Dashboard.findById(Mockito.anyLong())).thenReturn(dashboard);

        Query mockQuery = Mockito.mock(Query.class);
        Mockito.doNothing().when(kafkaConsumer).approveUsers(any());
        Mockito.doNothing().when(session).persist(any());

        List<Dashboard> dashboardList = new ArrayList<>();
        for (int i = 10; i < 20 ; i++) {
            Dashboard dashboard2 = new Dashboard();
            dashboard2.setUsername("mock_user" + i);
            dashboard2.setUserId((long) i);
            dashboard2.setApproved(Approved.APPROVED);
            dashboard2.setDisabledOrEnabled(DisabledOrEnabled.ENABLED);
            dashboardList.add(dashboard2);
        }

        Mockito.when(Dashboard.getAllList()).thenReturn(dashboardList);

        CompletableFuture<String> message = new CompletableFuture<>();
        var req = ApproveOrDisapproveUserDto.newBuilder().setUserId(1).setApprovedOrNot(String.valueOf(Approved.APPROVED)).build();
        this.managementGrpcService = new ManagementGrpcService();
        managementGrpcService.approveListOfUser(req)
                .subscribe().with(reply -> message.complete(reply.getResponseList().toString()));

    }

    @Test
    void enableOrDisableUser() {
        PanacheMock.mock(Dashboard.class);

        Dashboard dashboard = new Dashboard();
        dashboard.setUsername("user");
        dashboard.setUserId(1L);
        dashboard.setApproved(Approved.NOT_APPROVED);
        dashboard.setDisabledOrEnabled(DisabledOrEnabled.DISABLED);


        Mockito.when(Dashboard.findById(Mockito.anyLong())).thenReturn(dashboard);


        List<Dashboard> dashboardList = new ArrayList<>();
        for (int i = 10; i < 20 ; i++) {
            Dashboard dashboard2 = new Dashboard();
            dashboard2.setUsername("mock_user" + i);
            dashboard2.setUserId((long) i);
            dashboard2.setApproved(Approved.APPROVED);
            dashboard2.setDisabledOrEnabled(DisabledOrEnabled.ENABLED);
            dashboardList.add(dashboard2);
        }

        Mockito.when(Dashboard.getAllList()).thenReturn(dashboardList);

        CompletableFuture<String> message = new CompletableFuture<>();
        var req = EnableOrDisableDto.newBuilder().setUserId(1).setDisabledOrEnabled(String.valueOf(DisabledOrEnabled.ENABLED)).build();
        this.managementGrpcService = new ManagementGrpcService();
        managementGrpcService.enableOrDisableUser(req)
                .subscribe().with(reply -> message.complete(reply.getResponseList().toString()));
    }

}