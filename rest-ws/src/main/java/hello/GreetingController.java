package hello;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class GreetingController.
 * @author Vladimir Laskin
 * @version 1.0
 */
@RestController
@Slf4j
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(
        @RequestParam(value="name", defaultValue="World") String name,
        final HttpServletRequest request, final HttpServletResponse response
    ) {
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        log.info("check1.do with header:origin = {}", request.getHeader("origin"));
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}