package pl.horbo.everything;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

    public static void main(String... strings) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"WEB-INF/applicationContext.xml"});
    }

}
