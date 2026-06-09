package org.shangahi.sellio_backend.api.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun sellioOpenAPI(): OpenAPI {
        val securitySchemeName = "bearerAuth"
        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .info(
                Info()
                    .title("Sellio Backend API")
                    .version("1.0.0")
                    .description(
                        """
    <b>Sellio API Documentation</b><br><br>

    - Built with Spring Boot 3 & Kotlin.<br>
    - Handles Products, Users, Stores, Favorites... etc.<br>
    - All endpoints are versioned under <code>/v1/</code>.<br>
    - <b>Google Sheet: 
    <a href="https://docs.google.com/spreadsheets/d/1GJhx3pVEVLchPsBildQqi80OqWaLbYjN60vOfcVAWp0/edit?usp=sharing" target="_blank">
      Open Sellio Backend issues Sheet
    </a>

    <hr>

    <b>UUIDs:</b><br>
    USER_ID_OWNER: <input type="text" value="f895cdbe-73fc-4e44-b5db-02f396953f64" readonly 
    style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
    
    <br>
    USER_ID_CUSTOMER: <input type="text" value="c0c0c0c0-c0c0-c0c0-c0c0-c0c0c0c0c0c0" readonly
     style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
     
     <br>
    STORE_ID: <input type="text" value="57a212fc-e4ac-4f70-90cd-21f95dc600ba" readonly
     style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
     
     <br>
    CAT_ID_ELECTRONICS: <input type="text" value="44444444-c5c5-d6d6-e7e7-444444444444" readonly 
     style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
     
     <br>
    SUB_CAT_ID_LAPTOPS: <input type="text" value="33333333-d4d4-e5e5-f6f6-333333333333" readonly 
     style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
     
     <br>
    PRODUCT_ID_GAMING: <input type="text" value="11111111-a1a1-b2b2-c3c3-111111111111" readonly 
     style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
     
     <br>
    PRODUCT_ID_OFFICE: <input type="text" value="22222222-a1a1-b2b2-c3c3-222222222222" readonly 
     style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
     
     <br>
    ITEM_ID_1 (Product Item): <input type="text" value="a1a1a1a1-aaaa-aaaa-aaaa-000000000001" readonly 
     style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
     
     <br>
    ORDER_ID_1 (Orders): <input type="text" value="b1b1b1b1-bbbb-bbbb-bbbb-000000000001" readonly 
     style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
     
     <br>
    CART_ID_1 (Cart): <input type="text" value="c1c1c1c1-cccc-cccc-cccc-000000000001" readonly 
     style="border:none;background:#f4f4f4;padding:2px 5px;font-family:monospace;width:350px">
 
     <br>
    <hr>

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