package com.iobuilders.bank.infrastructure.config;

import com.iobuilders.bank.application.useCases.AccountsService;
import com.iobuilders.bank.application.useCases.MovementsService;
import com.iobuilders.bank.application.useCases.UsersService;
import com.iobuilders.bank.domain.ports.input.AccountsInputPort;
import com.iobuilders.bank.domain.ports.input.MovementsInputPort;
import com.iobuilders.bank.domain.ports.input.UsersInputPort;
import com.iobuilders.bank.infrastructure.adapters.output.AccountsRepository;
import com.iobuilders.bank.infrastructure.adapters.output.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfiguration {

    @Bean
    public UsersInputPort usersInputPort(UsersRepository usersRepository, AccountsInputPort accountsService) {
        return new UsersService(usersRepository, accountsService);
    }

    @Bean
    public AccountsInputPort accountsInputPort(AccountsRepository accountsRepository, MovementsInputPort movementsInputPort) {
        return new AccountsService(accountsRepository, movementsInputPort);
    }

    @Bean
    public MovementsInputPort movementsInputPort() {
        return new MovementsService();
    }


//    @Bean
//    public ProductPersistenceAdapter productPersistenceAdapter(ProductRepository productRepository, ProductPersistenceMapper productPersistenceMapper) {
//        return new ProductPersistenceAdapter(productRepository, productPersistenceMapper);
//    }
//
//    @Bean
//    public ProductEventPublisherAdapter productEventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
//        return new ProductEventPublisherAdapter(applicationEventPublisher);
//    }
//
//    @Bean
//    public ProductService productService(ProductPersistenceAdapter productPersistenceAdapter, ProductEventPublisherAdapter productEventPublisherAdapter) {
//        return new ProductService(productPersistenceAdapter, productEventPublisherAdapter);
//    }

}
