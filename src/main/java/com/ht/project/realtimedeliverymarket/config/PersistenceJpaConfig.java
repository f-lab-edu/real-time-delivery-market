package com.ht.project.realtimedeliverymarket.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @EnableJpaRepositories : spring-data-jpa는 어플리케이션을 실행 시,
 * basePackage에 있는 리포지토리 인터페이스를 찾아 해당 인터페이스를 구현한 클래스를 동적으로 생성하고,
 * 스프링 빈으로 등록합니다.
 *
 * @EnableTransactionManager : XML의 <tx:annotation-driven/> 과
 * 동일한 컨테이너 인프라 빈을 등록해주는 자바 코드 설정용 어노테이션입니다.
 * 트랜잭션 AOP 관련 인프라 빈들은 @EnableTransactionManagement로 모두 등록됩니다.
 * PlatformTransactionManager 타입의 빈을 찾아서 사용합니다.
 * TransactionManager가 2개 이상일 경우, TransactionManagementConfigurer 를 구현하여,
 * annotationDrivenTransactionManger() 메소드에서 사용할 빈을 반환하면 명시적으로 지정 가능합니다.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.ht.project.realtimedeliverymarket")
@EnableTransactionManagement
public class PersistenceJpaConfig {

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  public DataSource dataSource() {

    return DataSourceBuilder.create().build();
  }

  /**
   * LocalContainerEntityManagerFactoryBean: JPA를 스프링 컨테이너에서 사용할 수 있도록
   * 스프링 프레임워크가 제공하는 기능입니다.
   *
   * packagesToScan: @Entity가 붙은 클래스를 자동으로 검색하기 위한 시작점을 지정합니다.
   *
   * JpaVendorAdapter: 사용할 JPA 벤더를 지정합니다. 지정한 벤더 별로 다른 설정 프로퍼티를 사용해야 하는 경우가 많습니다.
   * 현재는 Hibernate르 사용하기 때문에 HibernateVenderAdapter를 사용합니다.
   * JPA 벤더를 사용하면 showSql, generateDdl database, databasePlatform 등을 지정할 수 있습니다.
   * @param dataSource
   * @return
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {

    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com.ht.project.realtimedeliverymarket");

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    //em.setJpaProperties(jpaProperties());

    return em;
  }

  /**
   * PersistenceExceptionTranslationPostProcessor: @Repository 어노테이션이 붙은 스프링 빈에 예외 변환 AOP를 적용합니다.
   * 해당 AOP는 JPA 예외를 스프링 프레임워크가 추상화한 예외로 변환해줍니다.
   * @return
   */
  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
    return new PersistenceExceptionTranslationPostProcessor();
  }

  /**
   * PlatformTransactionManager: 모든 스프링 트랜잭션 기능과 코드는 해당 인터페이스를 통해서
   * 로우레벨의 트랜잭션 서비스를 이용할 수 있습니다.
   * getTransaction(), commit(), rollback() 메소드로 이루어져 있습니다.
   * 이에 따라 트랜잭션 경계를 지정하는데 사용됩니다.
   * 트랜잭션이 어디서 시작하고 종료하는지, 정상적으로 종료되었는지, 비정상적으로 종료되었는지를 결정합니다.
   *
   * JpaTransactionManager: JPA를 이용하는 DAO는 JpaTransactionManager를 사용합니다.
   * 해당 빈에는 LocalContainerEntityManagerFactoryBean 타입의 빈을 프로퍼티로 등록해줘야 합니다.
   * @param entityManagerFactory
   * @return
   */
  @Bean
  public PlatformTransactionManager transactionManager(
          @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {

    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }

  /*해당 내용은 application-default.properties 에서 로드될 것으로 예상. 현재는 주석 처리 후, 문제시 재설정 해보기.
  private Properties jpaProperties() {

    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

    return properties;
  }*/

}