package com.hectorlopezfernandez.toilet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hectorlopezfernandez.toilet.metadata.MetadataService;
import com.hectorlopezfernandez.toilet.page.PageModelAssembler;
import com.hectorlopezfernandez.toilet.tag.TagModelAssembler;

@SpringBootApplication
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
	public TagModelAssembler tagModelAssembler() {
		return new TagModelAssembler();
	}

	@Bean
	public PageModelAssembler pageModelAssembler() {
		return new PageModelAssembler();
	}

	@Configuration
	public static class ApplicationWebConfiguration implements WebMvcConfigurer {

		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
			resolvers.add(new PaginationDataArgumentResolver());
		}

	}

}
