package com.example.transaccionesms.business;

import com.example.transaccionesms.model.TipoTransaccion;
import com.example.transaccionesms.model.Transaccion;
import com.example.transaccionesms.model.TransactionRequest;
import com.example.transaccionesms.model.TransactionResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
public class TransaccionMapper {

    public Transaccion getTransactionOfTransactionRequest(TransactionRequest request) {
        Transaccion entity = new Transaccion();
        entity.setAccountId(request.getAccountId());
        entity.setType(TipoTransaccion.valueOf(String.valueOf(request.getType())));
        entity.setAmount(request.getAmount());
        entity.setDate(request.getDate());
        return entity;
    }

    public TransactionResponse getTransactionResponseOfTransaction(Transaccion entity) {
        TransactionResponse response = new TransactionResponse();
        response.setId(entity.getId());
        response.setType(TransactionResponse.TypeEnum.valueOf(entity.getType().name()));
        response.setAmount(entity.getAmount());
        response.setDate(entity.getDate());
        response.setAccountId(entity.getAccountId());
        if (entity.getType() == TipoTransaccion.TRANSFERENCIA) {
            response.setToAccountId(entity.getToAccountId());
        }
        return response;
    }
}
