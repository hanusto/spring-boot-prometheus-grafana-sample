package jp.ahaoretama.springbootprometheusgrafanasample.controller;

import java.util.Random;

import javax.annotation.PostConstruct;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * A histogram samples observations (usually things like request durations or response sizes) and counts them in configurable buckets.
 * It also provides a sum of all observed values.
 * </p>
 *
 * @author hanusto
 */
@RestController
public class HistogramController {

    public static final Random RAND = new Random();

    @Autowired
    private MeterRegistry register;

    static DistributionSummary EVENTS_LATENCY;

    @PostConstruct
    public void init() {
        EVENTS_LATENCY = DistributionSummary
                .builder("his_kafka_topics_requests_latency_seconds")
                .description("Request latency in seconds.")
                .tag("topicPair", "hs360_tx_in_done_vs_hs360pf_scoring_done")
                .sla(200, 400, 600)
                .register(register);
    }

    @GetMapping("histogram")
    public String count() {

        final double l = generateLatency();
        System.out.println(l);
        EVENTS_LATENCY.record(l);

        return "bye";
    }

    private static double generateLatency() {
        return RAND.nextInt(700);
    }
}
