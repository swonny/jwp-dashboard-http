package org.apache.coyote.domain;

import org.apache.coyote.http11.domain.HttpCookie;

import java.util.Map;

public class HttpRequestHeader {

    private final String method;
    private final String uri;
    private final String version;
    private final HttpCookie cookie;
    private final Map<String, String> headerValues;

    public HttpRequestHeader(final String method, final String uri, final String version, final HttpCookie cookie, final Map<String, String> headerValues) {
        this.method = method;
        this.uri = uri;
        this.version = version;
        this.cookie = cookie;
        this.headerValues = headerValues;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaderValues() {
        return headerValues;
    }

    public HttpCookie getCookie() {
        return cookie;
    }
}
