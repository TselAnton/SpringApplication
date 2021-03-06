package com.tsel.app;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

@Slf4j
public class App implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        // Подключаем веб-конфиг из контекста
        XmlWebApplicationContext mainContext = new XmlWebApplicationContext();
        mainContext.setConfigLocation("file:src/main/resources/contexts/main-context.xml");

        // Передаём управление жизненным циклом корневого контекста Сервлет контексту
        container.addListener(new ContextLoaderListener(mainContext));

        // Создаём контекст для dispatcher servlet'а
        AnnotationConfigWebApplicationContext dispatcherContext =
                new AnnotationConfigWebApplicationContext();
        dispatcherContext.scan("com.tsel.app");

        // Регистрируем фильтр
        FilterRegistration.Dynamic filterRegistration =
            container.addFilter("recaptchaResponseFilter", new CharacterEncodingFilter());
        filterRegistration.setInitParameter("encoding", "UTF-8");
        filterRegistration.setInitParameter("forceEncoding", "true");
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");

        // Регистрируем dispatcher servlet
        ServletRegistration.Dynamic dispatcher =
            container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");


        log.info("App started");
    }
}
