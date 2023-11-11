package tikale.telegram.plugin.meet.checkday;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import tikale.telegram.plugin.meet.util.StringUtil;

@Service
public class CheckDayManager {

    private static final Logger LOG = LoggerFactory.getLogger(CheckDayManager.class);

    private String host;
    private final RestTemplate restTemplate;

    @Autowired
    public CheckDayManager(RestTemplateBuilder restTemplateBuilder, @Value("${check.day.host}") String host,
            @Value("${check.day.http.timeout:2}") int timeout) {
        this.host = host;
        this.restTemplate = restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .rootUri(this.host)
                .setConnectTimeout(Duration.ofSeconds(timeout))
                .setReadTimeout(Duration.ofSeconds(timeout))
                .errorHandler(new DefaultResponseErrorHandler())
                .build();

    }

    public CheckDayType checkDay(Date date) {
        HttpEntity<String> entity = new HttpEntity<String>(getHeader());

        ResponseEntity<String> response = this.restTemplate.exchange(
                StringUtil.SPALH + convertDate(date),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<String>() {

                });

        if (response.getStatusCode() == HttpStatus.OK
                || response.getStatusCode() == HttpStatus.BAD_REQUEST
                || response.getStatusCode() == HttpStatus.NOT_FOUND) {
            int responseData = Integer.valueOf(response.getBody());
            return CheckDayType.find(responseData);
        } else {
            LOG.error("For data [" + convertDate(date) + "] return code = " + response.getStatusCode());
        }

        return CheckDayType.UNKWOWN;
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }

    private String convertDate(Date date) {
        return new SimpleDateFormat(StringUtil.DATE_FORMAT).format(date);
    }
}
