package com.example.transaccionesms.business.strategy;

import com.example.transaccionesms.business.TransaccionMapper;
import com.example.transaccionesms.model.TransactionRequest;
import com.example.transaccionesms.model.TransactionResponse;
import com.example.transaccionesms.repository.TransaccionRepository;

public interface TransaccionStrategy    {
    TransactionResponse ejecutar(TransaccionRepository transaccionRepository, TransaccionMapper transaccionMapper, TransactionRequest request);

}
