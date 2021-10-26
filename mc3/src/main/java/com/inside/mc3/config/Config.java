package com.inside.mc3.config;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that initializes Spring beans,
 * a Jaeger Tracer bean in particular.
 */
@Configuration
public class Config {

    @Value("${application.jaeger.serviceName}")
    private String serviceName;

    @Bean
    public io.opentracing.Tracer initTracer() {
        val samplerConfig = new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1);
        val reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        return io.jaegertracing.Configuration.fromEnv(serviceName)
                .withSampler(samplerConfig)
                .withReporter(reporterConfig)
                .getTracer();
    }

}
