package com.example.transaccionesms.repository;

import com.example.transaccionesms.model.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransaccionRepository extends MongoRepository<Transaccion,String> {
}
