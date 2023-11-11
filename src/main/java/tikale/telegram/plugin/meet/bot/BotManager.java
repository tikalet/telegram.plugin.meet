package tikale.telegram.plugin.meet.bot;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import tikale.telegram.plugin.meet.entity.MessageResponseDto;
import tikale.telegram.plugin.meet.util.StringUtil;

@Service
public class BotManager {

    //    private static final Logger LOG = LoggerFactory.getLogger(BotManager.class);

    private static final String SEND = "/send";

    private String host;
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public BotManager(RestTemplateBuilder restTemplateBuilder, @Value("${bot.host}") String host, @Value("${my.token}") String token,
            @Value("${http.timeout:1}") int timeout) {
        this.host = host;
        this.token = token;
        this.restTemplate = restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .rootUri(this.host)
                .setConnectTimeout(Duration.ofSeconds(timeout))
                .setReadTimeout(Duration.ofSeconds(timeout))
                .errorHandler(new DefaultResponseErrorHandler())
                .build();

    }

    public void send(MessageResponseDto message) {
        HttpEntity<MessageResponseDto> entity = new HttpEntity<MessageResponseDto>(message, getHeader());

        this.restTemplate.exchange(
                SEND,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<String>() {

                });
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set(StringUtil.AUTHORIZATION, StringUtil.TOKEN + StringUtil.SPACE + token);

        return headers;
    }

}
