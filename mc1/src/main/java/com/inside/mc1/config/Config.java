package com.inside.mc1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public io.opentracing.Tracer initTracer() {
        io.jaegertracing.Configuration.SamplerConfiguration samplerConfig = new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1);
        io.jaegertracing.Configuration.ReporterConfiguration reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        return io.jaegertracing.Configuration.fromEnv("service-a").withSampler(samplerConfig).withReporter(reporterConfig).getTracer();
    }

}
