package com.easybet.infrastructure.config;

import com.easybet.domain.repository.JoueurRepository;
import com.easybet.infrastructure.event.JoueurEventProducer;
import com.easybet.usecase.CreateJoueurUseCase;
import com.easybet.usecase.CreatePortefeuilleUseCase;
import com.easybet.usecase.DeleteJoueurUseCase;
import com.easybet.usecase.GetAllJoueursUseCase;
import com.easybet.usecase.GetJoueurByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration des Use Cases
 * Infrastructure Layer - Injection de dépendances
 */
@Configuration
public class UseCaseConfig {

    @Bean
    public CreateJoueurUseCase createJoueurUseCase(JoueurRepository joueurRepository,
                                                   JoueurEventProducer eventProducer,
                                                   CreatePortefeuilleUseCase createPortefeuilleUseCase) {
        return new CreateJoueurUseCase(joueurRepository, eventProducer, createPortefeuilleUseCase);
    }

    @Bean
    public GetAllJoueursUseCase getAllJoueursUseCase(JoueurRepository joueurRepository) {
        return new GetAllJoueursUseCase(joueurRepository);
    }

    @Bean
    public GetJoueurByIdUseCase getJoueurByIdUseCase(JoueurRepository joueurRepository) {
        return new GetJoueurByIdUseCase(joueurRepository);
    }

    @Bean
    public DeleteJoueurUseCase deleteJoueurUseCase(JoueurRepository joueurRepository, JoueurEventProducer eventProducer) {
        return new DeleteJoueurUseCase(joueurRepository, eventProducer);
    }
}

