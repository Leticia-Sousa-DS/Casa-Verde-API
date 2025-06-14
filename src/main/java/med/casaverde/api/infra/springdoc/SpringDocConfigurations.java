package med.casaverde.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                        .info(new Info()
                            .title("CasaVerde Med API")
                            .version("1.0.0")
                        .description("API Rest da CasaVerde Med, contendo CRUD de médicos e paciente e funcionalidades de agendamento e cancelamento de consultas")
                                .contact(new Contact()
                                        .name("Equipe CasaVerde")
                                        .email("equipe@casaverde.med")
                                )
                                .termsOfService("http://casaverde.med/api/terms/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://casaverde.med/api/licenca")
                                )
                        );
    }



}
