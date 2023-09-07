package org.apache.coyote.http11;

import nextstep.FileResolver;
import nextstep.jwp.exception.UncheckedServletException;
import org.apache.coyote.Controller;
import org.apache.coyote.handler.ControllerMapper;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStream = connection.getInputStream();
             final var outputStream = connection.getOutputStream();
             final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            final HttpRequest request = HttpRequest.from(bufferedReader);
            final String parsedUri = request.getUri();
            String response;
            if (!FileResolver.containsExtension(parsedUri)) {
                final Controller controller = ControllerMapper.getController(parsedUri);
                response = controller.run(request);
            } else {
                final FileResolver fileResolver = FileResolver.getFile(parsedUri);
                final URL url = getClass().getClassLoader().getResource(fileResolver.getFilePath());
                final var responseBody = new String(Files.readAllBytes(new File(url.getFile()).toPath()));
                response = String.join("\r\n",
                        fileResolver.getResponseHeader(),
                        "Content-Type: " + fileResolver.getContentType(),
                        "Content-Length: " + responseBody.getBytes().length + " ",
                        "",
                        responseBody);
            }
            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
