package com.example.transaccionesms.strategy;

import com.example.transaccionesms.business.TransaccionMapper;
import com.example.transaccionesms.business.strategy.DepositoStrategy;
import com.example.transaccionesms.business.strategy.RetiroStrategy;
import com.example.transaccionesms.model.Transaccion;
import com.example.transaccionesms.model.TransactionRequest;
import com.example.transaccionesms.model.TransactionResponse;
import com.example.transaccionesms.repository.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RetiroStrategyTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private TransaccionMapper transaccionMapper;

    @InjectMocks
    private RetiroStrategy retiroStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ejecuta la estrategia de retiro correctamente")
    void testEjecutar() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId("123");
        request.setAmount(BigDecimal.valueOf(100));

        Transaccion transaccion = new Transaccion();
        transaccion.setAccountId("123");
        transaccion.setAmount(BigDecimal.valueOf(100));

        TransactionResponse expectedResponse = new TransactionResponse();
        expectedResponse.setAccountId("123");
        expectedResponse.setAmount(BigDecimal.valueOf(100));

        Mockito.when(transaccionMapper.getTransactionOfTransactionRequest(request)).thenReturn(transaccion);
        Mockito.when(transaccionRepository.save(transaccion)).thenReturn(transaccion);
        Mockito.when(transaccionMapper.getTransactionResponseOfTransaction(transaccion)).thenReturn(expectedResponse);

        TransactionResponse actualResponse = retiroStrategy.ejecutar(transaccionRepository, transaccionMapper, request);

        assertEquals(expectedResponse, actualResponse);
        verify(transaccionMapper, times(1)).getTransactionOfTransactionRequest(request);
        verify(transaccionRepository, times(1)).save(transaccion);
        verify(transaccionMapper, times(1)).getTransactionResponseOfTransaction(transaccion);
    }
}
