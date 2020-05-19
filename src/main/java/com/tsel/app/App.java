package com.tsel.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Slf4j
public class App implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        XmlWebApplicationContext mainContext = new XmlWebApplicationContext();
        mainContext.setConfigLocation("file:src/main/resources/contexts/main-context.xml");

        // Передаём управление жизненным циклом корневого контекста Сервлет контексту
        container.addListener(new ContextLoaderListener(mainContext));

        // Создаём контекст для dispatcher servlet'а
        AnnotationConfigWebApplicationContext dispatcherContext =
                new AnnotationConfigWebApplicationContext();
        dispatcherContext.scan("com.tsel.app");

        // Регистрируем dispatcher servlet
        ServletRegistration.Dynamic dispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");

        log.info("App started");
    }
}
