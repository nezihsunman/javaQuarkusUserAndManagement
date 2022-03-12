package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Dashboard extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Approved approved = Approved.WaitingForApprove;
    private DisabledOrEnabled disabledOrEnabled = DisabledOrEnabled.DISABLED;
    private String username;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Approved getApproved() {
        return approved;
    }

    public void setApproved(Approved approved) {
        this.approved = approved;
    }

    public DisabledOrEnabled getDisabledOrEnabled() {
        return disabledOrEnabled;
    }

    public void setDisabledOrEnabled(DisabledOrEnabled disabledOrEnabled) {
        this.disabledOrEnabled = disabledOrEnabled;
    }
    public static List<Dashboard> getAllList() {
        return Dashboard.listAll();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
