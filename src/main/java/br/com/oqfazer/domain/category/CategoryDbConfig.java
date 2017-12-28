package br.com.oqfazer.domain.category;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * A API trabalha com conexao em dois bancos de dados.
 * A classe CategoryDbConfig e reponsavel por vincular a entidade Category ao banco de dados oqfazer.
 * @author Thiago Fortunato
 * @version 1.0
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "categoryEntityManagerFactory", transactionManagerRef = "categoryTransactionManager", basePackages = {"br.com.oqfazer.domain.category"})
public class CategoryDbConfig {


    @Bean(name = "categoryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "categoryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("categoryDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("br.com.oqfazer.domain.category").persistenceUnit("category")
                .build();
    }

    @Bean(name = "categoryTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("categoryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
