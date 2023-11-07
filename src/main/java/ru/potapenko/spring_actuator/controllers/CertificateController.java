package ru.potapenko.spring_actuator.controllers;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.potapenko.spring_actuator.configurations.CertConfiguration;

import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
@RestController()
@RequestMapping("api/mypage")
public class CertificateController {



    public CertificateController(MeterRegistry registry) {

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
