package com.example.transaccionesms.delegate;

import com.example.transaccionesms.TransaccionDelegateImpl;
import com.example.transaccionesms.business.TransaccionService;
import com.example.transaccionesms.model.TransactionRequest;
import com.example.transaccionesms.model.TransactionResponse;
import com.example.transaccionesms.model.TransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransaccionDelegateImplTest {

    @Mock
    private TransaccionService transaccionService;

    @InjectMocks
    private TransaccionDelegateImpl transaccionDelegate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Registra un depósito con TransaccionDelegateImpl")
    void testRegistrarDeposito() {
        TransactionRequest request = new TransactionRequest();
        TransactionResponse expectedResponse = new TransactionResponse();

        when(transaccionService.registrarDeposito(request)).thenReturn(expectedResponse);

        ResponseEntity<TransactionResponse> responseEntity = transaccionDelegate.registrarDeposito(request);

        assertEquals(expectedResponse, responseEntity.getBody());
        verify(transaccionService, times(1)).registrarDeposito(request);
    }

    @Test
    @DisplayName("Registra un retiro con TransaccionDelegateImpl")
    void testRegistrarRetiro() {
        TransactionRequest request = new TransactionRequest();
        TransactionResponse expectedResponse = new TransactionResponse();

        when(transaccionService.registrarRetiro(request)).thenReturn(expectedResponse);

        ResponseEntity<TransactionResponse> responseEntity = transaccionDelegate.registrarRetiro(request);

        assertEquals(expectedResponse, responseEntity.getBody());
        verify(transaccionService, times(1)).registrarRetiro(request);
    }

    @Test
    @DisplayName("Registra un transferencia con TransaccionDelegateImpl")
    void testRegistrarTransferencia() {
        TransferRequest request = new TransferRequest();
        TransactionResponse expectedResponse = new TransactionResponse();

        when(transaccionService.registrarTransferencia(request)).thenReturn(expectedResponse);

        ResponseEntity<TransactionResponse> responseEntity = transaccionDelegate.registrarTransferencia(request);

        assertEquals(expectedResponse, responseEntity.getBody());
        verify(transaccionService, times(1)).registrarTransferencia(request);
    }

    @Test
    @DisplayName("Consulta el historial de transacciones correctamente")
    void testConsultarHistorial() {
        List<TransactionResponse> expectedList = new ArrayList<>();

        when(transaccionService.consultarHistorial()).thenReturn(expectedList);

        ResponseEntity<List<TransactionResponse>> responseEntity = transaccionDelegate.consultarHistorial();

        assertEquals(expectedList, responseEntity.getBody());
        verify(transaccionService, times(1)).consultarHistorial();
    }
}
