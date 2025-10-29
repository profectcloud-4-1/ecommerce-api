package profect.group1.goormdotcom.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import profect.group1.goormdotcom.payment.config.TossPaymentConfig;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final TossPaymentConfig tossPaymentConfig;

    @Bean
    public WebClient tossWebClient(WebClient.Builder builder, ObjectMapper objectMapper) {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(c -> {
                    c.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                    c.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                })
                .build();

        return builder
                .baseUrl(tossPaymentConfig.getConfirmUrl())
                .exchangeStrategies(strategies)
                .build();
    }
}
