package com.example.transaccionesms.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CuentaValidator {

    @Autowired
    private RestTemplate restTemplate;

    private static final String Account_WS = "http://localhost:8081/cuentas";

    public void verificarCuentaExistente(String accountId) {
        try {
            List<Map<String, Object>> cuentas = restTemplate.getForObject(Account_WS, List.class);

            boolean cuentaExiste = cuentas.stream()
                    .anyMatch(cuenta -> cuenta.get("id").equals(accountId));

            if (!cuentaExiste) {
                throw new IllegalArgumentException("La cuenta con ID " + accountId + " no existe.");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener cuentas del microservicio", e);
        }
    }
}
