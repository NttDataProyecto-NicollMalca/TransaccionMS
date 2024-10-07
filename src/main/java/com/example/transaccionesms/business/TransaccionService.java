package com.example.transaccionesms.business;

import com.example.transaccionesms.api.TransaccionesApiDelegate;
import com.example.transaccionesms.model.TransactionRequest;
import com.example.transaccionesms.model.TransactionResponse;
import com.example.transaccionesms.model.TransferRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransaccionService {


    List<TransactionResponse> consultarHistorial();

    TransactionResponse registrarDeposito(TransactionRequest transactionRequest);

    TransactionResponse registrarRetiro(TransactionRequest transactionRequest);

    TransactionResponse registrarTransferencia(TransferRequest transferRequest);

}
