package org.acme.DTOs;

import org.acme.entity.Dashboard;
import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;

public class UserListDto {
    public Long userId;
    public Approved approved;
    public DisabledOrEnabled disabledOrEnabled;

    public UserListDto(Dashboard dashboard) {
        this.userId = dashboard.getUserId();
        this.approved = dashboard.getApproved();
        this.disabledOrEnabled = dashboard.getDisabledOrEnabled();
    }
}
