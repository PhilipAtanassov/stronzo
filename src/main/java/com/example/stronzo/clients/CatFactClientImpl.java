package com.example.stronzo.clients;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CatFactClientImpl implements CatFactClient {

    private WebClient wc = WebClient.builder()
            .baseUrl("https://cat-fact.herokuapp.com/facts")
            .apply(ClientCustomizations.defaultClientSetup)
            .build();

    @Value("stronzo.urls.cats")
    private String catsUrl;

    @Override
    public Mono<List<CatFact>> getCatFactsMono() {
        // this throws a webClientException on 4xx or 5xx
        // var res = wc.get().uri(catsUrl).retrieve().bodyToMono(CatFactClient.CatFact.class);

        var result = wc
                .get()
                .uri(catsUrl)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(new ParameterizedTypeReference<List<CatFact>>() {
                        });
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                })
                .retryWhen(Retry
                        .backoff(3, Duration.ofSeconds(5))
                        .jitter(0.75)
                        // do not retry 4xx
                        .filter(e -> e instanceof WebClientResponseException
                                && ((WebClientResponseException) e).getStatusCode().is4xxClientError())
                );

        return result;
    }

    @Override
    public List<CatFact> getCatFacts() {
        return getCatFactsMono().block();
    }
}
