package org.apache.coyote.controller.util;

import java.util.Arrays;

public enum Extension {

    CSS(".css", HttpResponse.CSS_CONTENT_TYPE),
    JS(".js", HttpResponse.JS_CONTENT_TYPE),
    HTML(".html", HttpResponse.HTML_CONTENT_TYPE),
    ;

    private final String value;
    private final HttpResponse extensionType;

    Extension(final String value, final HttpResponse extensionType) {
        this.value = value;
        this.extensionType = extensionType;
    }

    public static HttpResponse getMappedContentType(final String uri) {
        return Arrays.stream(Extension.values())
                     .filter(extension -> uri.contains(extension.value))
                     .map(extension -> extension.extensionType)
                     .findAny()
                     .orElseThrow(() -> new IllegalArgumentException("잘못된 확장자입니다. " + uri));
    }
}
