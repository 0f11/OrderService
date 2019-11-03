package exservlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var context = new AnnotationConfigApplicationContext();
        context.scan("exservlet");
        context.refresh();
        sce.getServletContext().setAttribute("context",context);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
