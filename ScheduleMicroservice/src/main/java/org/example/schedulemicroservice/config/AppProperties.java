package org.example.schedulemicroservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix  = "app")
@Component
public class AppProperties {
    private String jwtSecret;
    private Long jwtExpirationMs;
}
