package com.example.demo.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Data
public class MinioConfig {

    private String endpoint = "http://127.0.0.1:9000";

    private String user = "admin";

    private String password = "password";


    @Bean
    public MinioClient getMinioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(user,password)
                .build();
    }



}
