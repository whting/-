package com.haoting.client;

import org.springframework.util.Assert;

/**
 * Created by kuiyuexiang on 15/11/19.
 */
public class SessionHolder {

    private static final ThreadLocal<SessionUser> sessionHolder = new ThreadLocal<SessionUser>();

    public static void clearSession() {
        sessionHolder.remove();
    }

    public static void setSession(SessionUser sessionUser) {
        Assert.notNull(sessionUser, "Only non-null Session instances are permitted");
        sessionHolder.set(sessionUser);
    }

    public static SessionUser getSession() {
        return sessionHolder.get();
    }
}
