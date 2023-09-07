package org.apache.catalina.domain;

import nextstep.jwp.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private final String id;
    private final Map<String, Object> values = new HashMap<>();

    private HttpSession(final String id) {
        this.id = id;
    }

    public static HttpSession of(final String userKey, final User user) {
        final HttpSession session = new HttpSession(createSession());
        session.setAttribute(userKey, user);
        return session;
    }

    private static String createSession() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(final String name) {
        return values.get(name);
    }

    public void setAttribute(final String name, final Object value) {
        values.put(name, value);
    }
}
