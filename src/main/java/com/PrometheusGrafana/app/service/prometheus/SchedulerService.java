package com.PrometheusGrafana.app.service.prometheus;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerService {

    private final HistogramAndSummaryService histogramAndSummaryService;

    public SchedulerService(HistogramAndSummaryService histogramAndSummaryService) {
        this.histogramAndSummaryService = histogramAndSummaryService;
    }

    @Scheduled(fixedRate = 1000)
    public void schedule() {
        histogramAndSummaryService.testApi();
        histogramAndSummaryService.recordQueueLength();
    }
}
