package com.PrometheusGrafana.app.service.client;

import com.PrometheusGrafana.app.DTOS.ClientDTO;
import com.PrometheusGrafana.app.domain.Client;
import com.PrometheusGrafana.app.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientFactory implements ClientFactoryInterface{

    private final ClientRepository clientRepository;

    @Autowired
    public ClientFactory(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createAndSaveClient(ClientDTO clientDTO) {
        Client newClient = new Client(clientDTO);
        newClient.setName(clientDTO.name());
        newClient.setAge(clientDTO.age());
        newClient.setGender(clientDTO.gender());
        this.clientRepository.save(newClient);
        return newClient;
    }
}
