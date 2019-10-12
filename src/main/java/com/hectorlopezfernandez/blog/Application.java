package com.hectorlopezfernandez.blog;

import org.eclipse.jetty.server.NetworkTrafficServerConnector;
import org.eclipse.jetty.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
    public ServletWebServerFactory servletContainer() {
    	final JettyServletWebServerFactory containerFactory = new JettyServletWebServerFactory();
    	containerFactory.addServerCustomizers(new JettyServerCustomizer() {
			@Override
			public void customize(Server server) {
				NetworkTrafficServerConnector httpConnector = new NetworkTrafficServerConnector(server);
				httpConnector.setPort(8080);
				server.addConnector(httpConnector);
			}
    	});
    	return containerFactory;
    }

}
