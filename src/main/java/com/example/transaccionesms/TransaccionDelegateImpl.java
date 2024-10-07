package com.example.transaccionesms;

import com.example.transaccionesms.api.TransaccionesApiDelegate;
import com.example.transaccionesms.business.TransaccionService;
import com.example.transaccionesms.model.TransactionRequest;
import com.example.transaccionesms.model.TransactionResponse;
import com.example.transaccionesms.model.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionDelegateImpl implements TransaccionesApiDelegate {

    @Autowired
    TransaccionService transaccionService;

    @Override
    public ResponseEntity<TransactionResponse> registrarDeposito(TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transaccionService.registrarDeposito(transactionRequest));
    }

    @Override
    public ResponseEntity<TransactionResponse> registrarRetiro(TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transaccionService.registrarRetiro(transactionRequest));
    }

    @Override
    public ResponseEntity<TransactionResponse> registrarTransferencia(TransferRequest transferRequest) {
        return ResponseEntity.ok(transaccionService.registrarTransferencia(transferRequest));
    }

    @Override
    public ResponseEntity<List<TransactionResponse>> consultarHistorial() {
        return ResponseEntity.ok(transaccionService.consultarHistorial());
    }
}
