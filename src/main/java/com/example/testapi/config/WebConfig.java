package com.example.testapi.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/www/**")
                .addResourceLocations("classpath:/www/", "/www/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Bean
    public freemarker.template.Configuration ftlConfiguration(){
        freemarker.template.Configuration ftlConfiguration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_27);
        ftlConfiguration.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/"));
        ftlConfiguration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        ftlConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return ftlConfiguration;
    }

}
