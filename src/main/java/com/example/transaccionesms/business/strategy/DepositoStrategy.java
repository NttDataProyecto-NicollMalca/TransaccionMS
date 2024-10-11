package com.example.transaccionesms.business.strategy;

import com.example.transaccionesms.business.TransaccionMapper;
import com.example.transaccionesms.model.Transaccion;
import com.example.transaccionesms.model.TransactionRequest;
import com.example.transaccionesms.model.TransactionResponse;
import com.example.transaccionesms.repository.TransaccionRepository;
import org.springframework.stereotype.Component;

@Component
public class DepositoStrategy implements TransaccionStrategy {

    @Override
    public TransactionResponse ejecutar(TransaccionRepository transaccionRepository, TransaccionMapper transaccionMapper, TransactionRequest request) {
        Transaccion transaccion = transaccionMapper.getTransactionOfTransactionRequest(request);
        return transaccionMapper.getTransactionResponseOfTransaction(transaccionRepository.save(transaccion));
    }

}
