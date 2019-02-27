package jp.ahaoretama.springbootprometheusgrafanasample;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootPrometheusGrafanaSampleApplication {

	@Value("${spring.application.name}")
	private String appName;

	@Value("${HOSTNAME}")
	private String instance;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPrometheusGrafanaSampleApplication.class, args);
	}

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return registry -> registry.config().commonTags("app", appName, "instance", instance);
	}
}
