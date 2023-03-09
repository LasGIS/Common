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
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.IndexMapHolder;
import ru.russianpost.tracking.web.model.info.IndexInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

public class IndexMapHolderTest {

    private static final String URI = "/dictionary";
    private static final Header HEADER = new Header("Content-Type", "application/json; charset=UTF-8");
    private static final Delay DELAY = new Delay(SECONDS, 1);

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
    public void withoutDefaultsAnswer() {
        mockServer
                .when(request().withMethod("GET").withPath(URI).withQueryStringParameter("from", "0"))
                .respond(
                        response()
                                .withHeaders(HEADER)
                                .withBody(body("withoutDefaults1"))
                                .withDelay(DELAY)
            );
        mockServer
            .when(request().withMethod("GET").withPath(URI).withQueryStringParameter("from", "2"))
            .respond(
                response()
                    .withHeaders(HEADER)
                    .withBody(body("withoutDefaults2"))
                    .withDelay(DELAY)
            );
        final IndexMapHolder holder = spy(new IndexMapHolder(url(URI), new RestTemplate()));
        holder.setBatchSize(2);
        holder.load();

        {
            final IndexInfo indexInfo = holder.get("101000");
            assertThat(indexInfo, notNullValue());
            assertThat(indexInfo.getId(), is("101000"));
        }
        {
            final IndexInfo indexInfo = holder.get("101300");
            assertThat(indexInfo, notNullValue());
            assertThat(indexInfo.getId(), is("101300"));
        }
        {
            final IndexInfo indexInfo = holder.get("644000");
            assertThat(indexInfo, notNullValue());
            assertThat(indexInfo.getId(), is("644000"));
        }

        final String unknownKey = "789456";
        assertThat(holder.get(unknownKey), nullValue());
        assertThat(holder.getDefaultValue(unknownKey), nullValue());
    }

    private BodyWithContentType<?> body(final String name) {
        final Class<?> clazz = getClass();
        try (InputStream stream = clazz.getResourceAsStream(clazz.getSimpleName() + '-' + name + ".body")) {
            return json(IO.toString(stream, UTF_8), UTF_8);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private UriComponentsBuilder url(final String uri) {
        return UriComponentsBuilder.fromUriString(format("http://localhost:%d%s", mockServer.getLocalPort(), uri));
    }

}
