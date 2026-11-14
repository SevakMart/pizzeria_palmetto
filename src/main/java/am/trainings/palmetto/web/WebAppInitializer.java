package am.trainings.palmetto.web;

import am.trainings.palmetto.common.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {

        log.info("Registering root config class");
        return new Class<?>[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        log.info("Registering web config class");

        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
