package ru.potapenko.spring_actuator.configurations;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.*;


public class CertConfiguration {
    @SneakyThrows
    public List<X509Certificate> getCertificates() {
        List<X509Certificate> certificates = new ArrayList<>();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, null);
        Iterator<String> aliasIterator = keyStore.aliases().asIterator();
        while (aliasIterator.hasNext()) {
            String alias = aliasIterator.next();
            certificates.addAll(Arrays.stream(keyStore.getCertificateChain(alias)).map(cert -> (X509Certificate) cert).toList());
        }
        return certificates;
    }
}
