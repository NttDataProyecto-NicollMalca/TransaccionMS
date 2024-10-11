package com.example.transaccionesms.service;

import com.example.transaccionesms.business.CuentaRestService;
import com.example.transaccionesms.model.InlineObject1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CuentaRestServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CuentaRestService cuentaRestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Realiza un depósito en la cuenta especificada")
    void testRealizarDeposito() {
        String cuentaId = "123";
        BigDecimal monto = new BigDecimal("100.00");
        String url = "http://localhost:8081/cuentas/" + cuentaId + "/depositar";

        cuentaRestService.realizarDeposito(cuentaId, monto);

        InlineObject1 request = new InlineObject1();
        request.setMonto(monto.doubleValue());

        verify(restTemplate, times(1)).put(eq(url), eq(request));
    }

    @Test
    @DisplayName("Realiza un retiro en la cuenta especificada")
    void testRealizarRetiro() {
        String cuentaId = "123";
        BigDecimal monto = new BigDecimal("100.00");
        String url = "http://localhost:8081/cuentas/" + cuentaId + "/retirar";

        cuentaRestService.realizarRetiro(cuentaId, monto);

        InlineObject1 request = new InlineObject1();
        request.setMonto(monto.doubleValue());

        verify(restTemplate, times(1)).put(eq(url), eq(request));
    }
}
