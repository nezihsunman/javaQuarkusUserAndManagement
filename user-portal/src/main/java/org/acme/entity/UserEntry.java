package org.acme.entity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Username;
import org.acme.enums.Approved;
import org.acme.enums.DisabledOrEnabled;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserEntry extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;
    @Username
    private String username;
    @Password
    public String password;

    private String firstName;

    private String lastName;

    private Approved approved = Approved.WaitingForApprove;

    private DisabledOrEnabled disabledOrEnabled = DisabledOrEnabled.DISABLED;

    public static long add(String username, String password, String firstName, String lastName) {
        UserEntry user = new UserEntry();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.firstName = firstName;
        user.lastName = lastName;
        user.persist();
        return user.id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
