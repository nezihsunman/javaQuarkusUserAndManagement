package org.acme.GrpcService;

import io.quarkus.grpc.GrpcService;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import org.acme.*;
import org.acme.DTOs.UserListDto;
import org.acme.entity.Dashboard;
import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;
import org.acme.kafka.KafkaConsumer;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@GrpcService
public class ManagementGrpcService implements ManagementGrpc {
    @Inject
    KafkaConsumer kafkaConsumer;

    @Override
    public Uni<UserReply> getListOfUser(HelloRequest request) {
        return Uni.createFrom().item(() -> {
                    List<Dashboard> item = Dashboard.getAllList();
                    var ids = item.stream().map(Dashboard::getUserId).collect(Collectors.toList());
                    return item;
                })
                .runSubscriptionOn(Infrastructure.getDefaultExecutor())
                .onItem().transform(i -> {
                    var msg = UserReply.newBuilder();
                    i.forEach((Dashboard item) -> {
                        msg.addResponse(DashBoardDTO.newBuilder()
                                .setUserId(item.getUserId())
                                .setDisabledOrEnabled(String.valueOf(item.getDisabledOrEnabled()))
                                .setApprovedOrNot(String.valueOf(item.getApproved()))
                                .setUsername(item.getUsername()).build());
                    });
                    return msg.build();
                }).log();

    }

    @Override
    @Transactional
    public Uni<UserReply> approveListOfUser(ApproveOrDisapproveUserDto request) {
        return Uni.createFrom().item(() -> {
                    Dashboard dashboard = Dashboard.findById(request.getUserId());
                    dashboard.setApproved(Approved.valueOf(request.getApprovedOrNot()));
                    dashboard.persist();
                    kafkaConsumer.approveUsers(new UserListDto(dashboard));
                    List<Dashboard> item = Dashboard.getAllList();
                    var ids = item.stream().map(Dashboard::getUserId).collect(Collectors.toList());
                    return item;
                })
                .runSubscriptionOn(Infrastructure.getDefaultExecutor())
                .onItem().transform(i -> {
                    var msg = UserReply.newBuilder();
                    i.forEach((Dashboard item) -> {
                        msg.addResponse(DashBoardDTO.newBuilder()
                                .setUserId(item.getUserId())
                                .setDisabledOrEnabled(String.valueOf(item.getDisabledOrEnabled()))
                                .setApprovedOrNot(String.valueOf(item.getApproved())).build());
                    });
                    return msg.build();
                });

    }

    @Override
    public Uni<UserReply> enableOrDisableUser(EnableOrDisableDto request) {
        return Uni.createFrom().item(() -> {
                    Dashboard dashboard = Dashboard.findById(request.getUserId());
                    dashboard.setDisabledOrEnabled(DisabledOrEnabled.valueOf(request.getDisabledOrEnabled()));
                    dashboard.persist();
                    kafkaConsumer.approveUsers(new UserListDto(dashboard));
                    List<Dashboard> item = Dashboard.getAllList();
                    var ids = item.stream().map(Dashboard::getUserId).collect(Collectors.toList());
                    return item;
                })
                .runSubscriptionOn(Infrastructure.getDefaultExecutor())
                .onItem().transform(i -> {
                    var msg = UserReply.newBuilder();
                    i.forEach((Dashboard item) -> {
                        msg.addResponse(DashBoardDTO.newBuilder()
                                .setUserId(item.getUserId())
                                .setDisabledOrEnabled(String.valueOf(item.getDisabledOrEnabled()))
                                .setApprovedOrNot(String.valueOf(item.getApproved())).build());
                    });
                    return msg.build();
                });
    }
}