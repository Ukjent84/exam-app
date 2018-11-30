package movies


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@SpringBootApplication(scanBasePackages = ["movies"])
@EnableJpaRepositories(basePackages = ["movies"])
@EntityScan(basePackages = ["movies"])
@EnableSwagger2 //needed to enable Swagger
class RestApplication {

    /*
        Bean used to configure Swagger documentation
     */
    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Rest API for movies")
                .description("Some description")
                .version("1.0")
                .build()
    }
}

/*
    If you run this directly, you can then check the Swagger documentation at:

    http://localhost:8080/movierest/api/swagger-ui.html

 */
fun main(args: Array<String>) {
    SpringApplication.run(RestApplication::class.java, *args)
}