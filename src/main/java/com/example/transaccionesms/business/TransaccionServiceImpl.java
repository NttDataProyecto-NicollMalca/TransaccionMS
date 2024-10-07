package com.example.transaccionesms.business;

import com.example.transaccionesms.model.*;
import com.example.transaccionesms.repository.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImpl implements TransaccionService{

    @Autowired
    TransaccionRepository transaccionRepository;

    @Autowired
    TransaccionMapper transaccionMapper;

    @Autowired
    private RestTemplate restTemplate;

    private static final String Account_WS = "http://localhost:8081/cuentas";

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

        verificarCuentaExistente(transactionRequest.getAccountId());

        realizarDeposito(transactionRequest.getAccountId(),transactionRequest.getAmount());


        return transaccionMapper.getTransactionResponseOfTransaction(
                transaccionRepository.save(transaccionMapper.getTransactionOfTransactionRequest(transactionRequest))
        );
    }

    @Override
    public TransactionResponse registrarRetiro(TransactionRequest transactionRequest) {
        if (!transactionRequest.getType().equals(TransactionRequest.TypeEnum.RETIRO)) {
            throw new IllegalArgumentException("Tipo de transacción inválido para retiro");
        }

        verificarCuentaExistente(transactionRequest.getAccountId());

        realizarRetiro(transactionRequest.getAccountId(),transactionRequest.getAmount());

        return transaccionMapper.getTransactionResponseOfTransaction(
                transaccionRepository.save(transaccionMapper.getTransactionOfTransactionRequest(transactionRequest))
        );
    }

    @Override
    public TransactionResponse registrarTransferencia(TransferRequest transferRequest) {

        verificarCuentaExistente(transferRequest.getFromAccountId());
        verificarCuentaExistente(transferRequest.getToAccountId());

        realizarRetiro(transferRequest.getFromAccountId(), transferRequest.getAmount());

        realizarDeposito(transferRequest.getToAccountId(), transferRequest.getAmount());

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


    private void verificarCuentaExistente(String accountId) {
        try {
            List<Map<String, Object>> cuentas = restTemplate.getForObject(Account_WS, List.class);

            boolean cuentaExiste = cuentas.stream()
                    .anyMatch(cuenta -> cuenta.get("id").equals(accountId));

            if (!cuentaExiste) {
                throw new IllegalArgumentException("La cuenta con ID " + accountId + " no existe.");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener cuentas del microservicio", e);
        }
    }


    private void realizarRetiro(String cuentaId, BigDecimal monto) {
        String url = Account_WS +"/" + cuentaId + "/retirar";

        InlineObject1 retiroRequest = new InlineObject1();
        retiroRequest.setMonto(monto.doubleValue());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InlineObject1> requestEntity = new HttpEntity<>(retiroRequest, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                Void.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error al realizar el retiro en la cuenta");
        }

    }

    private void realizarDeposito(String cuentaId, BigDecimal monto) {
        String url = Account_WS +"/" + cuentaId + "/depositar";

        InlineObject1 depositoRequest = new InlineObject1();
        depositoRequest.setMonto(monto.doubleValue());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InlineObject1> requestEntity = new HttpEntity<>(depositoRequest, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                Void.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error al realizar el deposito en la cuenta");
        }

    }





}
