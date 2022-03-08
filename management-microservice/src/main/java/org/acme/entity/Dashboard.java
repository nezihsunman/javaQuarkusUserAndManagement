package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

enum DisabledOrEnabled {
    ENABLED,
    DISABLED
}
enum Approved {
    APPROVED,
    NOT_APPROVED
}

@Entity
public class Dashboard extends PanacheEntityBase {
    @Id
    private Long userId;
    private Approved approved = Approved.NOT_APPROVED;
    private DisabledOrEnabled disabledOrEnabled = DisabledOrEnabled.DISABLED;

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

}
