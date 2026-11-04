package lifecycletest;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = BeanLifecycleOrderTest.Config.class)
public class BeanLifecycleOrderTest {


    static final List<String> executionLog = new ArrayList<>();

    @Autowired
    private LifecycleDemoBean demoBean;

    static class LifecycleDemoBean implements InitializingBean {

        public LifecycleDemoBean() {
            executionLog.add("1. Constructor");
        }

        @PostConstruct
        public void postConstruct() {
            executionLog.add("2. @PostConstruct");
        }

        @Override
        public void afterPropertiesSet() {
            executionLog.add("3. InitializingBean.afterPropertiesSet");
        }

        public void customInit() {
            executionLog.add("4. Custom initMethod");
        }
    }


    @Configuration
    static class Config {

        @Bean(initMethod = "customInit")
        LifecycleDemoBean demoBean() {
            return new LifecycleDemoBean();
        }
    }


    @Test
    void verifyLifecycleOrder() {

        List<String> expectedOrder = List.of(
                "1. Constructor",
                "2. @PostConstruct",
                "3. InitializingBean.afterPropertiesSet",
                "4. Custom initMethod"
        );

        assertEquals(expectedOrder, executionLog);
    }

}

