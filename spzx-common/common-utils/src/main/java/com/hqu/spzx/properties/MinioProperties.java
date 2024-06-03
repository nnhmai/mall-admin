package com.hqu.spzx.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "spzx.minio")
public class MinioProperties {
    private String endpointUrl;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
