package com.example.customer_management.generator;

import com.example.customer_management.domain.Musteri;
import com.example.customer_management.enums.MukellefTuru;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class MusteriIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Musteri musteri = (Musteri) object;

        if (musteri.getMukellefTuru() == null) {
            throw new IllegalArgumentException("MukellefTuru must be set before persisting!");
        }

        // Determine prefix based on MukellefTuru
        String prefix = musteri.getMukellefTuru() == MukellefTuru.GERCEK ? "m" : "s";

        // Query the max numeric part of existing IDs with this prefix
        String sql = "SELECT MAX(CAST(SUBSTRING(id, 2) AS INTEGER)) FROM ms_musteri WHERE id LIKE :prefixPattern";

        Number maxNumber = (Number) session.createNativeQuery(sql)
                .setParameter("prefixPattern", prefix + "%")
                .getSingleResult();

        long nextNumber = (maxNumber == null) ? 1 : maxNumber.longValue() + 1;

        return String.format("%s%04d", prefix, nextNumber);
    }
}
