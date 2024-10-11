package com.example.transaccionesms.Mapper;

import com.example.transaccionesms.business.TransaccionMapper;
import com.example.transaccionesms.model.TipoTransaccion;
import com.example.transaccionesms.model.Transaccion;
import com.example.transaccionesms.model.TransactionRequest;
import com.example.transaccionesms.model.TransactionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.mongodb.assertions.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransaccionMapperTest {
    private TransaccionMapper mapper = new TransaccionMapper();

    @Test
    @DisplayName("Convierte TransactionRequest a Transaccion correctamente")
    void testGetTransactionOfTransactionRequest() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId("123");
        request.setType(TransactionRequest.TypeEnum.DEPOSITO);
        request.setAmount(new BigDecimal("100.00"));
        request.setDate("2024-01-01");

        Transaccion transaction = mapper.getTransactionOfTransactionRequest(request);

        assertEquals("123", transaction.getAccountId());
        assertEquals(TipoTransaccion.DEPOSITO, transaction.getType());
        assertEquals(new BigDecimal("100.00"), transaction.getAmount());
        assertEquals("2024-01-01", transaction.getDate());
    }

    @Test
    @DisplayName("Convierte Transaccion a TransactionResponse correctamente para depósito o retiro")
    void testGetTransactionResponseOfTransaction_DepositoRetiro() {
        Transaccion entity = new Transaccion();
        entity.setId("trans123");
        entity.setAccountId("123");
        entity.setType(TipoTransaccion.DEPOSITO);
        entity.setAmount(new BigDecimal("100.00"));
        entity.setDate("2024-01-01");

        TransactionResponse response = mapper.getTransactionResponseOfTransaction(entity);

        assertEquals("trans123", response.getId());
        assertEquals("123", response.getAccountId());
        assertEquals(TransactionResponse.TypeEnum.DEPOSITO, response.getType());
        assertEquals(new BigDecimal("100.00"), response.getAmount());
        assertEquals("2024-01-01", response.getDate());
        assertNull(response.getToAccountId());
    }

    @Test
    @DisplayName("Convierte Transaccion a TransactionResponse correctamente para transferencia")
    void testGetTransactionResponseOfTransaction_Transferencia() {
        Transaccion entity = new Transaccion();
        entity.setId("trans456");
        entity.setAccountId("123");
        entity.setType(TipoTransaccion.TRANSFERENCIA);
        entity.setAmount(new BigDecimal("200.00"));
        entity.setDate("2024-01-02");
        entity.setToAccountId("456");

        TransactionResponse response = mapper.getTransactionResponseOfTransaction(entity);

        assertEquals("trans456", response.getId());
        assertEquals("123", response.getAccountId());
        assertEquals(TransactionResponse.TypeEnum.TRANSFERENCIA, response.getType());
        assertEquals(new BigDecimal("200.00"), response.getAmount());
        assertEquals("2024-01-02", response.getDate());
        assertEquals("456", response.getToAccountId());
    }
}
