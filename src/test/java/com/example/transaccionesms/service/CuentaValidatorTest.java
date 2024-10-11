package com.example.transaccionesms.service;

import com.example.transaccionesms.business.CuentaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CuentaValidatorTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CuentaValidator cuentaValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Verifica que la cuenta exista en la base de datos")
    void testVerificarCuentaExistente_cuentaExiste() {
        String accountId = "123";
        List<Map<String, Object>> cuentasMock = List.of(
                Map.of("id", "123")
        );

        Mockito.when(restTemplate.getForObject(Mockito.eq("http://localhost:8081/cuentas"), Mockito.eq(List.class)))
                .thenReturn(cuentasMock);

        cuentaValidator.verificarCuentaExistente(accountId);

        verify(restTemplate, times(1)).getForObject(Mockito.anyString(), Mockito.eq(List.class));
    }

    @Test
    @DisplayName("Lanza una excepción cuando la cuenta no existe")
    void testVerificarCuentaExistente_cuentaNoExiste() {
        String accountId = "123";
        List<Map<String, Object>> cuentasMock = Collections.emptyList();

        Mockito.when(restTemplate.getForObject(Mockito.eq("http://localhost:8081/cuentas"), Mockito.eq(List.class)))
                .thenReturn(cuentasMock);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.verificarCuentaExistente(accountId));
    }

    @Test
    @DisplayName("Cuando el consumo al otro microservicio falla")
    void testVerificarCuentaExistente_restClientException() {
        Mockito.when(restTemplate.getForObject(Mockito.eq("http://localhost:8081/cuentas"), Mockito.eq(List.class)))
                .thenThrow(new RestClientException("Error de conexión"));

        assertThrows(RuntimeException.class, () -> cuentaValidator.verificarCuentaExistente("123"));
    }
}
