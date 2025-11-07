package org.shangahi.sellio_backend.api.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun sellioOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Sellio Backend API")
                    .version("1.0.0")
                    .description(
                        """
                        Welcome to the **Sellio API Documentation**.

                        - Built with Spring Boot 3 & Kotlin.
                        - Handles Products, Users, Stores, Favorites... etc.
                        - All endpoints are versioned under `/v1/`.
                        """.trimIndent()
                    )

                    .license(
                        License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                    )
            )
            .servers(
                listOf(
                    Server().url("http://localhost:8080").description("Local Development"),
                    Server().url("https://sellio.com").description("Production")
                )
            )
    }
}