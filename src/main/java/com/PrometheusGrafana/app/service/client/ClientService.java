package com.PrometheusGrafana.app.service.client;

import com.PrometheusGrafana.app.DTOS.ClientDTO;
import com.PrometheusGrafana.app.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientFactory clientFactory;

    @Autowired
    public ClientService(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public Client CreateClient(ClientDTO clientDTO){

        return clientFactory.createAndSaveClient(clientDTO);

    }

}
