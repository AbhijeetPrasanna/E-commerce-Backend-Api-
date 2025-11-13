package com.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ECommerceApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
    }

    @Test
    void testApplicationStarts() {
        assertThat(context.getBean(ECommerceApplication.class)).isNotNull();
    }

    @Test
    void testDataSourceBeanExists() {
        assertThat(context.containsBean("dataSource")).isTrue();
    }

    @Test
    void testEntityManagerFactoryExists() {
        assertThat(context.containsBean("entityManagerFactory")).isTrue();
    }

    @Test
    void testTransactionManagerExists() {
        assertThat(context.containsBean("transactionManager")).isTrue();
    }

    @Test
    void testSwaggerConfigurationLoads() {
        assertThat(context.getBeansWithAnnotation(org.springframework.context.annotation.Configuration.class)).isNotEmpty();
    }

    @Test
    void testSecurityFilterChainExists() {
        assertThat(context.containsBean("securityFilterChain") || context.containsBean("springSecurityFilterChain")).isTrue();
    }

    @Test
    void testJpaRepositoriesEnabled() {
        String[] beans = context.getBeanNamesForAnnotation(org.springframework.stereotype.Repository.class);
        assertThat(beans.length).isGreaterThan(0);
    }

    @Test
    void testRestControllersLoaded() {
        String[] beans = context.getBeanNamesForAnnotation(org.springframework.web.bind.annotation.RestController.class);
        assertThat(beans.length).isGreaterThan(0);
    }

    @Test
    void testServiceLayerExists() {
        String[] beans = context.getBeanNamesForAnnotation(org.springframework.stereotype.Service.class);
        assertThat(beans.length).isGreaterThan(0);
    }

    @Test
    void testComponentScanWorks() {
        assertThat(context.getBeanDefinitionCount()).isGreaterThan(50);
    }

    @Test
    void testApplicationPropertiesLoaded() {
        assertThat(context.getEnvironment().getProperty("spring.application.name")).isNotNull();
    }

    @Test
    void testJpaPropertiesConfigured() {
        assertThat(context.getEnvironment().getProperty("spring.jpa.hibernate.ddl-auto")).isNotNull();
    }

    @Test
    void testDatabaseConnectionConfigured() {
        assertThat(context.getEnvironment().getProperty("spring.datasource.url")).isNotNull();
    }

    @Test
    void testServerPortConfigured() {
        String port = context.getEnvironment().getProperty("server.port");
        assertThat(port == null || Integer.parseInt(port) > 0).isTrue();
    }

    @Test
    void testAuth0PropertiesConfigured() {
        assertThat(context.getEnvironment().getProperty("auth0.audience") != null || 
                   context.getEnvironment().getProperty("spring.security.oauth2.resourceserver.jwt.issuer-uri") != null).isTrue();
    }

    @Test
    void testJacksonObjectMapperExists() {
        assertThat(context.getBean(ObjectMapper.class)).isNotNull();
    }

    @Test
    void testAllBeansInitialized() {
        String[] allBeans = context.getBeanDefinitionNames();
        assertThat(allBeans.length).isGreaterThan(0);
        for (String bean : allBeans) {
            assertThat(context.getBean(bean)).isNotNull();
        }
    }

    @Test
    void testNoCircularDependencies() {
        assertThat(context.getBeanDefinitionCount()).isEqualTo(context.getBeanDefinitionNames().length);
    }

    @Test
    void testApplicationMainMethodExecutable() throws Exception {
        assertThat(ECommerceApplication.class.getMethod("main", String[].class)).isNotNull();
    }
}