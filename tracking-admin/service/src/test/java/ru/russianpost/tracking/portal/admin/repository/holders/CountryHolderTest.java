package ru.russianpost.tracking.portal.admin.repository.holders;

import org.eclipse.jetty.util.IO;
import org.hamcrest.CustomMatcher;
import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.BodyWithContentType;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.socket.PortFactory;
import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.portal.admin.model.operation.Country;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.CountryHolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

public class CountryHolderTest {

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
    public void withoutDefaultsAnswer() {
        mockServer
                .when(request().withMethod("GET").withPath(URI))
                .respond(
                        response()
                                .withHeaders(new Header("Content-Type", "application/json; charset=UTF-8"))
                                .withBody(body("withoutDefaults"))
                                .withDelay(new Delay(SECONDS, 1))
                );
        final CountryHolder holder = new CountryHolder(url(URI), new RestTemplate());
        holder.load();

        assertThat(holder.getCountries(), hasSize(2));
        assertThat(holder.getCountries(), containsInAnyOrder(asList(countryMatcher("AB"), countryMatcher("AU"))));
        assertNull(holder.getCountryById(1));
        assertEquals("Австралия", holder.getCountryById(36).getNameRu());
    }

    private Matcher<Country> countryMatcher(final String isoCode) {
        return new CustomMatcher("countryMatcher") {

            @Override
            public boolean matches(final Object item) {
                return isoCode.equals(((Country)item).getCodeA2());
            }

        };
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
