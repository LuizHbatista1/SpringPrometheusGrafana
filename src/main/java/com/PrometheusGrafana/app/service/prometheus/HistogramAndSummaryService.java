package com.PrometheusGrafana.app.service.prometheus;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import io.micrometer.core.instrument.Timer;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.stream.IntStream;

@Service
@Slf4j
public class HistogramAndSummaryService {
    private static final String TEST_SERVICE_RESPONSE_TIME_SECONDS = "test.service.response.time.seconds";
    private static final String TEST_SERVICE_QUEUE_LENGTH_HISTOGRAM = "test.service.queue.length.histogram";
    private static final String TEST_SERVICE_QUEUE_LENGTH_SUMMARY = "test.service.queue.length.summary";
    private final Timer timer;
    private final DistributionSummary queueLengthHistogram;
    private final DistributionSummary QueueLengthSummary;
    private final RandomDataGenerator randomDataGenerator;
    private NormalDistribution responseTimeSecondsDistribution;
    private PoissonDistribution meanQueueLengthDistribution;

    public HistogramAndSummaryService(
            @Value("${spring.application.test.service.mean.response.time.seconds}")
            double meanResponseTimeSeconds,
            @Value("${spring.application.test.service.stddev.response.time.seconds}")
            double stddevResponseTimeSeconds,
            @Value("${spring.application.test.service.mean.queue.length}") double meanQueueLength,
            MeterRegistry meterRegistry) {

        this.randomDataGenerator = new RandomDataGenerator();

        this.responseTimeSecondsDistribution =
                new NormalDistribution(meanResponseTimeSeconds, stddevResponseTimeSeconds);

        this.meanQueueLengthDistribution = new PoissonDistribution(meanQueueLength);

        this.queueLengthHistogram =
                DistributionSummary.builder(TEST_SERVICE_QUEUE_LENGTH_HISTOGRAM)
                        .maximumExpectedValue(20.0)
                        .publishPercentileHistogram()
                        .register(meterRegistry);

        this.QueueLengthSummary =
                DistributionSummary.builder(TEST_SERVICE_QUEUE_LENGTH_SUMMARY)
                        .maximumExpectedValue(20.0)
                        .publishPercentiles(0.25, 0.5, 0.75, 0.95)
                        .register(meterRegistry);

        int[] responseTimeThresholdsInMillis = IntStream.range(1, 16)
                .map(i -> i * 100)
                .toArray();

        this.timer =
                Timer.builder(TEST_SERVICE_RESPONSE_TIME_SECONDS)
                        .serviceLevelObjectives(
                                IntStream.of(responseTimeThresholdsInMillis)
                                        .mapToObj(Duration::ofMillis)
                                        .toArray(Duration[]::new)
                        )
                        .maximumExpectedValue(Duration.ofMillis(1500))
                        .register(meterRegistry);
    }

    public void testApi() {

        Logger log = LoggerFactory.getLogger(HistogramAndSummaryService.class);
        double responseTimeSeconds = responseTimeSecondsDistribution.sample();
        long responseTimeMillis = (long) (responseTimeSeconds * 1_000);
        timer.record(Duration.ofMillis(responseTimeMillis));
        log.debug("The response time is {} ms", responseTimeMillis);

    }

    public void recordQueueLength() {

        Logger log = LoggerFactory.getLogger(HistogramAndSummaryService.class);
        int queueLength = meanQueueLengthDistribution.sample();
        queueLengthHistogram.record(queueLength);
        QueueLengthSummary.record(queueLength);
        log.debug("The queue length {}", queueLength);

    }

    public void setMeanResponseTimeSeconds(double newMean) {
        responseTimeSecondsDistribution = new NormalDistribution(newMean, responseTimeSecondsDistribution.getStandardDeviation());
    }

    public void setMeanQueueLength(double mean) {
        meanQueueLengthDistribution = new PoissonDistribution(mean);
    }
}
