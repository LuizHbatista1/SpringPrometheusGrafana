package com.PrometheusGrafana.app.repository;

import com.PrometheusGrafana.app.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
