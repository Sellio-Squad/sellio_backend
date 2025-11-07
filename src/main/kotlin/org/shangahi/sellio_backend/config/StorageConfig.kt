package org.shangahi.sellio_backend.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

@Configuration
@EnableConfigurationProperties(StorageProperties::class)
class StorageConfig(private val props: StorageProperties) {

    @Bean
    fun s3Client(): S3Client {
        val creds = StaticCredentialsProvider.create(
            AwsBasicCredentials.create(props.key, props.secret)
        )
        return buildClient(props.endpoint, creds)
    }

    private fun buildClient(
        endpoint: String,
        creds: StaticCredentialsProvider
    ): S3Client {
        return S3Client.builder()
            .region(Region.US_EAST_1)
            .endpointOverride(URI.create(endpoint))
            .credentialsProvider(creds)
            .serviceConfiguration(pathStyleConfiguration())
            .build()
    }

    @Bean
    fun pathStyleConfiguration(): S3Configuration {
        return S3Configuration.builder()
            .pathStyleAccessEnabled(true)
            .build()
    }

}