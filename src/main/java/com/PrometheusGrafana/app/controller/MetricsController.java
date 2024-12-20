package com.PrometheusGrafana.app.controller;

import com.PrometheusGrafana.app.service.prometheus.HistogramAndSummaryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

public class MetricsController {

    @RestController("/apiV1")
    public class MetricsPrometheusController {

        HistogramAndSummaryService histogramAndSummaryService;

        public MetricsPrometheusController(HistogramAndSummaryService histogramAndSummaryService) {
            this.histogramAndSummaryService = histogramAndSummaryService;
        }

        @PostMapping("/setMeanResponseTimeSeconds")
        public void setResponseTimeSecondsMean(@RequestBody double seconds) {
            histogramAndSummaryService.setMeanResponseTimeSeconds(seconds);
        }

        @PostMapping("/setMeanQueueLength")
        public void setMeanQueueLength(@RequestBody double mean) {
            histogramAndSummaryService.setMeanQueueLength(mean);
        }

    }



}
