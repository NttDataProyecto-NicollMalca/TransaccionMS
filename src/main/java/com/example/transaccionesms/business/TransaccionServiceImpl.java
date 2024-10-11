package com.example.transaccionesms.business;

import com.example.transaccionesms.business.strategy.DepositoStrategy;
import com.example.transaccionesms.business.strategy.RetiroStrategy;
import com.example.transaccionesms.business.strategy.TransaccionStrategy;
import com.example.transaccionesms.model.*;
import com.example.transaccionesms.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImpl implements TransaccionService{

    private final TransaccionRepository transaccionRepository;
    private final TransaccionMapper transaccionMapper;
    private final CuentaValidator cuentaValidator;
    private final CuentaRestService cuentaRestService;
    private final Map<TipoTransaccion, TransaccionStrategy> estrategias = new HashMap<>();

    @Autowired
    public TransaccionServiceImpl(TransaccionRepository transaccionRepository,
                                  TransaccionMapper transaccionMapper,
                                  CuentaValidator cuentaValidator,
                                  CuentaRestService cuentaRestService,
                                  List<TransaccionStrategy> estrategiaList) {
        this.transaccionRepository = transaccionRepository;
        this.transaccionMapper = transaccionMapper;
        this.cuentaValidator = cuentaValidator;
        this.cuentaRestService = cuentaRestService;

        for (TransaccionStrategy estrategia : estrategiaList) {
            if (estrategia instanceof DepositoStrategy) {
                estrategias.put(TipoTransaccion.DEPOSITO, estrategia);
            } else if (estrategia instanceof RetiroStrategy) {
                estrategias.put(TipoTransaccion.RETIRO, estrategia);
            }
        }
    }


    @Override
    public List<TransactionResponse> consultarHistorial() {
        return transaccionRepository.findAll().stream()
                .map(transaccionMapper::getTransactionResponseOfTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponse registrarDeposito(TransactionRequest transactionRequest) {

        if(!transactionRequest.getType().equals(TransactionRequest.TypeEnum.DEPOSITO)){
            throw new IllegalArgumentException("Tipo de transacción inválido para depósito");
        }
        cuentaValidator.verificarCuentaExistente(transactionRequest.getAccountId());

        cuentaRestService.realizarDeposito(transactionRequest.getAccountId(),transactionRequest.getAmount());

        return estrategias.get(TipoTransaccion.DEPOSITO)
                .ejecutar(transaccionRepository, transaccionMapper, transactionRequest);

    }

    @Override
    public TransactionResponse registrarRetiro(TransactionRequest transactionRequest) {
        if (!transactionRequest.getType().equals(TransactionRequest.TypeEnum.RETIRO)) {
            throw new IllegalArgumentException("Tipo de transacción inválido para retiro");
        }

        cuentaValidator.verificarCuentaExistente(transactionRequest.getAccountId());

        cuentaRestService.realizarRetiro(transactionRequest.getAccountId(),transactionRequest.getAmount());

        return estrategias.get(TipoTransaccion.RETIRO)
                .ejecutar(transaccionRepository, transaccionMapper, transactionRequest);

    }

    @Override
    public TransactionResponse registrarTransferencia(TransferRequest transferRequest) {

        cuentaValidator.verificarCuentaExistente(transferRequest.getFromAccountId());
        cuentaValidator.verificarCuentaExistente(transferRequest.getToAccountId());

        cuentaRestService.realizarRetiro(transferRequest.getFromAccountId(), transferRequest.getAmount());
        cuentaRestService.realizarDeposito(transferRequest.getToAccountId(), transferRequest.getAmount());

        Transaccion transaction = new Transaccion();
        transaction.setFromAccountId(transferRequest.getFromAccountId());
        transaction.setToAccountId(transferRequest.getToAccountId());
        transaction.setType(TipoTransaccion.TRANSFERENCIA);
        transaction.setAmount(transferRequest.getAmount());
        transaction.setDate(transferRequest.getDate());

        return transaccionMapper.getTransactionResponseOfTransaction(
                transaccionRepository.save(transaction)
        );
    }

}
