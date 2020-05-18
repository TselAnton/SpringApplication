package com.tsel.app;

import com.tsel.app.service.TimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.time.format.DateTimeFormatter;

@Slf4j
public class App implements WebApplicationInitializer {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation(
//                "file:src/main/webapp/WEB-INF/dispatcher-config.xml");
                "file:src/main/resources/contexts/main-context.xml");

        ServletRegistration.Dynamic dispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        TimeService service = appContext.getBean("timeService", TimeService.class);
        log.info("App started");
        log.info("Program time: " + service.now().format(FORMATTER));
    }
}
