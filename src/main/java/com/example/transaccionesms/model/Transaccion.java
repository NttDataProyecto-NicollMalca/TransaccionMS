package com.example.transaccionesms.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "transaccion")
public class Transaccion {
    @Id
    private String id;
    private String accountId;
    private TipoTransaccion type;
    private BigDecimal amount;
    private String fromAccountId;
    private String toAccountId;
    private String date;
}
