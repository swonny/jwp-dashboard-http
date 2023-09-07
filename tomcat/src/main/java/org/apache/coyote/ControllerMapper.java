package org.apache.coyote;

import org.apache.catalina.manager.SessionManager;
import org.apache.coyote.controller.LoginController;
import org.apache.coyote.controller.RegisterController;

public enum ControllerMapper {

    LOGIN("/login"),
    REGISTER("/register"),
    ;

    private final String uri;

    ControllerMapper(final String uri) {
        this.uri = uri;
    }

    public static Controller getController(final String parsedUri) {
        if (parsedUri.contains(LOGIN.uri)) {
            return new LoginController(new SessionManager());
        }
        if (parsedUri.contains(REGISTER.uri)) {
            return new RegisterController();
        }

        return null;
    }
}
