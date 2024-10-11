package com.example.transaccionesms.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class InlineObject1 {
    private double monto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InlineObject1 that = (InlineObject1) o;
        return Double.compare(that.monto, monto) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(monto);
    }
}
