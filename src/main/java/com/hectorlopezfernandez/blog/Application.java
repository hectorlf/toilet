package com.hectorlopezfernandez.blog;

import io.undertow.Undertow.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;

import com.hectorlopezfernandez.blog.metadata.MetadataService;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class,ManagementWebSecurityAutoConfiguration.class})
@ServletComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Bean
	@Autowired
	public LocaleResolver localeResolver(MetadataService metadataService) {
		return new CustomLocaleResolver(metadataService);
	}

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
    	final UndertowEmbeddedServletContainerFactory containerFactory = new UndertowEmbeddedServletContainerFactory();
    	containerFactory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
			@Override
			public void customize(Builder builder) {
				String address = containerFactory.getAddress() == null ? "0.0.0.0" : containerFactory.getAddress().getHostAddress();
				builder.addHttpListener(8080, address);
			}
    	});
    	return containerFactory;
    }

}