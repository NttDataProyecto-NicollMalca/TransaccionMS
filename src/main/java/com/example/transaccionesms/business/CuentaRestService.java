package com.example.transaccionesms.business;

import com.example.transaccionesms.model.InlineObject1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CuentaRestService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String Account_WS = "http://localhost:8081/cuentas";

    public void realizarDeposito(String cuentaId, BigDecimal monto) {
        String url = Account_WS + "/" + cuentaId + "/depositar";
        InlineObject1 request = new InlineObject1();
        request.setMonto(monto.doubleValue());
        restTemplate.put(url, request);
    }

    public void realizarRetiro(String cuentaId, BigDecimal monto) {
        String url = Account_WS + "/" + cuentaId + "/retirar";
        InlineObject1 request = new InlineObject1();
        request.setMonto(monto.doubleValue());
        restTemplate.put(url, request);
    }
}
