package com.PrometheusGrafana.app.service.client;

import com.PrometheusGrafana.app.DTOS.ClientDTO;
import com.PrometheusGrafana.app.domain.Client;

public interface ClientFactoryInterface {

    Client createAndSaveClient(ClientDTO clientDTO);



}
