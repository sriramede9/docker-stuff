package lifecycletest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;

// 1. Include CoolingSystem.class directly in classes
@SpringBootTest(classes = {BeanDefinitionMutationTest.Config.class, CoolingSystem.class})
class BeanDefinitionMutationTest {

    @Autowired
    private CoolingSystem coolingSystem;

    @Test
    void shouldModifyBeanDefinitionBeforeCreation() {
        assertEquals(
                "LIQUID_NITROGEN",
                coolingSystem.getCoolingFluid()
        );
    }

    @TestConfiguration
    static class Config {

        @Bean
        static BeanFactoryPostProcessor coolingProcessor() {
            return beanFactory -> {
                BeanDefinition definition =
                        beanFactory.getBeanDefinition("coolingSystem");

                definition.getPropertyValues()
                        .add("coolingFluid", "LIQUID_NITROGEN");
            };
        }
    }
}

@Component
class CoolingSystem {

    private String coolingFluid = "WATER";

    public String getCoolingFluid() {
        return coolingFluid;
    }

    public void setCoolingFluid(String coolingFluid) {
        this.coolingFluid = coolingFluid;
    }
}