package ru.potapenko.spring_actuator;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.potapenko.spring_actuator.configurations.CertConfiguration;
import ru.potapenko.spring_actuator.controllers.CertificateController;

import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class MainTest {
    @Autowired
    MeterRegistry registry;
    @Test
    void controllerTest(){
        CertConfiguration certConfiguration = new CertConfiguration();

        List<X509Certificate> certificates = certConfiguration.getCertificates();
        for (int i = 0; i < certificates.size(); i++) {
            long time = new Date().getTime();
            int finalI = i;
            Tags tag = Tags.of("cert", certificates.get(i).getSigAlgName());
            Gauge.builder("app.certificates",
                            () -> (time - certificates.get(finalI).getNotBefore().getTime()) / 60 / 60 / 24).tags(tag)
                    .register(registry);
        }
    }
}
