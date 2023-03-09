package ru.russianpost.tracking.portal.admin.repository.holders;

import org.eclipse.jetty.util.IO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.BodyWithContentType;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.socket.PortFactory;
import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.LocalizedOperationAttributeMapHolder;
import ru.russianpost.tracking.web.model.info.LocalizedOperationKey;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.once;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

public class LocalizedOperationAttributeMapHolderTest {

    private static final String URI = "/dictionary";

    private static ClientAndServer mockServer;

    @BeforeClass
    public static void startServer() {
        mockServer = startClientAndServer(PortFactory.findFreePort());
    }

    @AfterClass
    public static void stopServer() {
        mockServer.stop();
    }

    @Test
    public void fullConfiguredAnswer() {
        mockServer
                .when(request().withMethod("GET").withPath(URI), once())
                .respond(
                        response()
                                .withHeaders(new Header("Content-Type", "application/json; charset=UTF-8"))
                                .withBody(body("full"))
                                .withDelay(new Delay(SECONDS, 1))
            );
        final LocalizedOperationAttributeMapHolder holder = new LocalizedOperationAttributeMapHolder(url(URI), new RestTemplate());
        holder.load();

        assertThat(holder.get(new LocalizedOperationKey("RUS", 1, 2)), is("Партионный"));

        final LocalizedOperationKey unknownKey = new LocalizedOperationKey("RUS", 1, 3);
        assertThat(holder.get(unknownKey), nullValue());
        assertThat(holder.getDefaultValue(unknownKey), is("Неизвестное значение"));
    }

    @Test
    public void emptyDefaultsAnswer() {
        mockServer
            .when(request().withMethod("GET").withPath(URI), once())
            .respond(
                response()
                .withHeaders(new Header("Content-Type", "application/json; charset=UTF-8"))
                .withBody(body("emptyDefaults"))
                .withDelay(new Delay(SECONDS, 1))
            );
        final LocalizedOperationAttributeMapHolder holder = new LocalizedOperationAttributeMapHolder(url(URI), new RestTemplate());
        holder.load();

        assertThat(holder.get(new LocalizedOperationKey("RUS", 1, 2)), is("Партионный"));

        final LocalizedOperationKey unknownKey = new LocalizedOperationKey("RUS", 1, 3);
        assertThat(holder.get(unknownKey), nullValue());
        assertThat(holder.getDefaultValue(unknownKey), nullValue());
    }

    @Test
    public void withoutDefaultsAnswer() {
        mockServer
            .when(request().withMethod("GET").withPath(URI), once())
            .respond(
                response()
                .withHeaders(new Header("Content-Type", "application/json; charset=UTF-8"))
                .withBody(body("withoutDefaults"))
                .withDelay(new Delay(SECONDS, 1))
            );
        final LocalizedOperationAttributeMapHolder holder = new LocalizedOperationAttributeMapHolder(url(URI), new RestTemplate());
        holder.load();

        assertThat(holder.get(new LocalizedOperationKey("RUS", 1, 2)), is("Партионный"));

        final LocalizedOperationKey unknownKey = new LocalizedOperationKey("RUS", 1, 3);
        assertThat(holder.get(unknownKey), nullValue());
        assertThat(holder.getDefaultValue(unknownKey), nullValue());
    }

    private BodyWithContentType<?> body(final String name) {
        final Class<?> clazz = getClass();
        try (InputStream stream = clazz.getResourceAsStream(clazz.getSimpleName() + '-' + name + ".body");) {
            return json(IO.toString(stream, UTF_8), UTF_8);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String url(final String uri) {
        return format("http://localhost:%d%s", mockServer.getLocalPort(), uri);
    }

}
