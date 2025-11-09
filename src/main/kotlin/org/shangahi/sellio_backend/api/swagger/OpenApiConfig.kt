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
                        ----------------------------------------------------
                        - **UUIDs:**
                            - USER_ID_OWNER: 
                                - **f895cdbe-73fc-4e44-b5db-02f396953f64**
                                
                            - USER_ID_CUSTOMER: 
                                - **c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0**
                                
                            - STORE_ID:
                                - **57a212fc-e4ac-4f70-90cd-21f95dc600ba**
                                
                            - CAT_ID_ELECTRONICS: 
                                - **44444444-c5c5-d6d6-e7e7-444444444444**
                                
                            - SUB_CAT_ID_LAPTOPS: 
                                - **33333333-d4d4-e5e5-f6f6-333333333333**
                                
                            - PRODUCT_ID_GAMING: 
                                - **11111111-a1a1-b2b2-c3c3-111111111111**
                                
                            - PRODUCT_ID_OFFICE: 
                                - **22222222-a1a1-b2b2-c3c3-222222222222**
                                
                            - ITEM_ID_1 (Product Item): 
                                - **a1a1a1a1-aaaa-aaaa-aaaa-000000000001**
                                
                            - ORDER_ID_1 (Orders): 
                                - **b1b1b1b1-bbbb-bbbb-bbbb-000000000001**
                                
                            - CART_ID_1 (Cart): 
                                - **c1c1c1c1-cccc-cccc-cccc-000000000001**
                        ----------------------------------------------------

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
                    Server().url("http://127.0.0.1:8080").description("Docker environment"),
                    Server().url("https://sellio.com").description("Production")
                )
            )
    }
}