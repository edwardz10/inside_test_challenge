package com.inside.mc1.config;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class that initializes Spring beans.
 */
@Configuration
public class Config {

    @Value("${application.jaeger.serviceName}")
    private String serviceName;
    @Value("${application.websocket.mc2Endpoint}")
    private String mc2WebsocketEndpoint;
    @Value("${application.websocket.topicName}")
    private String mc2WebsocketTopicName;

    @Bean
    public io.opentracing.Tracer initTracer() {
        val samplerConfig = new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1);
        val reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        return io.jaegertracing.Configuration.fromEnv(serviceName)
                .withSampler(samplerConfig)
                .withReporter(reporterConfig)
                .getTracer();
    }

    @SneakyThrows
    @Bean
    public StompSession stompSession() {
        val simpleWebSocketClient =
                new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));

        val sockJsClient = new SockJsClient(transports);
        val stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new Mc2StompSessionHandler(mc2WebsocketTopicName);
        return stompClient.connect(mc2WebsocketEndpoint, sessionHandler).get();
    }
}
