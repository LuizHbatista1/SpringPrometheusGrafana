package com.PrometheusGrafana.app.controller;

import com.PrometheusGrafana.app.DTOS.ClientDTO;
import com.PrometheusGrafana.app.domain.Client;
import com.PrometheusGrafana.app.service.client.ClientService;
import com.PrometheusGrafana.app.service.prometheus.CustomMetricsCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiV1")
public class ClientController {

    private final ClientService clientService;
    private final CustomMetricsCounterService customMetricsService;

    @Autowired
    public ClientController(ClientService clientService, CustomMetricsCounterService customMetricsService) {
        this.clientService = clientService;
        this.customMetricsService = customMetricsService;
    }

    @PostMapping("/create")
    public ResponseEntity<Client>createClient_Return201(@RequestBody ClientDTO clientDTO){

        Client newClient = clientService.CreateClient(clientDTO);
        customMetricsService.incrementCustomMetric();
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);

    }


}
