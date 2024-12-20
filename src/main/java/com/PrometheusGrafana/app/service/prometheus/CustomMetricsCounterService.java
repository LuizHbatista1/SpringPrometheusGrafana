package com.PrometheusGrafana.app.service.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetricsCounterService {

    private final Counter customMetricCounter;

    public CustomMetricsCounterService(MeterRegistry meterRegistry){

        customMetricCounter = Counter.builder("custom_metric").description("MY First Counter")
                .tags("enviroment","development").register(meterRegistry);

    }

    public void incrementCustomMetric(){

        customMetricCounter.increment();

    }





}
