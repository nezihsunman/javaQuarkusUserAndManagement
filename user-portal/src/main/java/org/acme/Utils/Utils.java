package org.acme.Utils;

import org.acme.entity.UserEntry;

import javax.persistence.EntityManager;

public class Utils {
    public static UserEntry getEntityByUserName(EntityManager em, String email) {
        var entity = em.createQuery("Select t from UserEntry t WHERE t.username =" + email, UserEntry.class).getSingleResult();
        return entity;
    }
}
