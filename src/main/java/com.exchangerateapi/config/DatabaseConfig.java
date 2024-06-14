package com.exchangerateapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({ "com.exchangerateapi.repository" })
@EnableTransactionManagement
public class DatabaseConfig {}
