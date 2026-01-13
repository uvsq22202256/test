package com.easybet.infrastructure.config;

// --- Imports des Repositories ---
import com.easybet.domain.repository.JoueurRepository;
import com.easybet.domain.repository.PortefeuilleRepository;
import com.easybet.domain.repository.TransactionRepository;
import com.easybet.domain.repository.JeuRepository;
import com.easybet.domain.repository.SessionJeuRepository;
import com.easybet.domain.repository.LimiteJeuRepository;

import com.easybet.infrastructure.event.JeuEventProducer;
import com.easybet.infrastructure.event.JeuResponsableEventProducer;

// --- Imports des Events et Kafka ---
import com.easybet.infrastructure.event.*;
import org.springframework.kafka.core.KafkaTemplate;

// --- Imports de TOUS les Use Cases ---
import com.easybet.usecase.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration des Use Cases
 * Infrastructure Layer - Injection de dépendances
 */
@Configuration
public class UseCaseConfig {

    // ==========================================
    // 1. GESTION DES JOUEURS (Votre code existant)
    // ==========================================

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

    // ==========================================
    // 2. GESTION DES PORTEFEUILLES & TRANSACTIONS (Ajouts)
    // ==========================================

    @Bean
    public CreatePortefeuilleUseCase createPortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository) {
        return new CreatePortefeuilleUseCase(portefeuilleRepository);
    }

    @Bean
    public EffectuerDepotUseCase effectuerDepotUseCase(PortefeuilleRepository portefeuilleRepository,
                                                       TransactionRepository transactionRepository,
                                                       KafkaTemplate<String, PortefeuilleDepotEffectueEvent> kafkaTemplate) {
        return new EffectuerDepotUseCase(portefeuilleRepository, transactionRepository, kafkaTemplate);
    }

    @Bean
    public EffectuerRetraitUseCase effectuerRetraitUseCase(PortefeuilleRepository portefeuilleRepository,
                                                           TransactionRepository transactionRepository,
                                                           KafkaTemplate<String, PortefeuilleRetraitEffectueEvent> kafkaTemplate) {
        return new EffectuerRetraitUseCase(portefeuilleRepository, transactionRepository, kafkaTemplate);
    }

    @Bean
    public GetPortefeuilleUseCase getPortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository) {
        return new GetPortefeuilleUseCase(portefeuilleRepository);
    }

    @Bean
    public GetAllPortefeuillesUseCase getAllPortefeuillesUseCase(PortefeuilleRepository portefeuilleRepository) {
        return new GetAllPortefeuillesUseCase(portefeuilleRepository);
    }

    @Bean
    public TransfertFondsUseCase transfertFondsUseCase(PortefeuilleRepository portefeuilleRepository,
                                                       TransactionRepository transactionRepository,
                                                       KafkaTemplate<String, PortefeuilleTransfertEffectueEvent> kafkaTemplate) {
        return new TransfertFondsUseCase(portefeuilleRepository, transactionRepository, kafkaTemplate);
    }

    @Bean
    public BloquerPortefeuilleUseCase bloquerPortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository,
                                                                 KafkaTemplate<String, PortefeuilleBlockeEvent> kafkaTemplate) {
        return new BloquerPortefeuilleUseCase(portefeuilleRepository, kafkaTemplate);
    }

    @Bean
    public DebloquerPortefeuilleUseCase debloquerPortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository) {
        return new DebloquerPortefeuilleUseCase(portefeuilleRepository);
    }

    @Bean
    public AjouterBonusUseCase ajouterBonusUseCase(PortefeuilleRepository portefeuilleRepository,
                                                   TransactionRepository transactionRepository,
                                                   KafkaTemplate<String, PortefeuilleBonusAjouteEvent> kafkaTemplate) {
        return new AjouterBonusUseCase(portefeuilleRepository, transactionRepository, kafkaTemplate);
    }

    @Bean
    public SupprimerPortefeuilleUseCase supprimerPortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository) {
        return new SupprimerPortefeuilleUseCase(portefeuilleRepository);
    }

    @Bean
    public GetHistoriqueTransactionsUseCase getHistoriqueTransactionsUseCase(TransactionRepository transactionRepository) {
        return new GetHistoriqueTransactionsUseCase(transactionRepository);
    }

    // ==========================================
    // 3. GESTION DES JEUX & SESSIONS (Ajouts pour le Casino)
    // ==========================================

    @Bean
    public CreerJeuUseCase creerJeuUseCase(JeuRepository jeuRepository, JeuEventProducer eventProducer) {
        return new CreerJeuUseCase(jeuRepository, eventProducer);
    }

    @Bean
    public ListerJeuxUseCase listerJeuxUseCase(JeuRepository jeuRepository) {
        return new ListerJeuxUseCase(jeuRepository);
    }

    @Bean
    public SupprimerJeuUseCase supprimerJeuUseCase(JeuRepository jeuRepository, JeuEventProducer eventProducer) {
        return new SupprimerJeuUseCase(jeuRepository, eventProducer);
    }

    @Bean
    public DemarrerSessionUseCase demarrerSessionUseCase(SessionJeuRepository sessionRepository,
                                                         JeuRepository jeuRepository,
                                                         PortefeuilleRepository portefeuilleRepository,
                                                         EffectuerRetraitUseCase retraitUseCase,
                                                         JeuEventProducer eventProducer) {
        return new DemarrerSessionUseCase(sessionRepository, jeuRepository, portefeuilleRepository, retraitUseCase, eventProducer);
    }

    @Bean
    public TerminerSessionUseCase terminerSessionUseCase(SessionJeuRepository sessionRepository,
                                                         PortefeuilleRepository portefeuilleRepository,
                                                         EffectuerDepotUseCase depotUseCase,
                                                         JeuEventProducer eventProducer) {
        return new TerminerSessionUseCase(sessionRepository, portefeuilleRepository, depotUseCase, eventProducer);
    }

    @Bean
    public ModifierJeuUseCase modifierJeuUseCase(JeuRepository jeuRepository, JeuEventProducer eventProducer) {
        return new ModifierJeuUseCase(jeuRepository, eventProducer);
    }

    // --- JEU RESPONSABLE ---

    @Bean
    public DefinirLimiteDepotUseCase definirLimiteDepotUseCase(LimiteJeuRepository repository, JeuResponsableEventProducer producer) {
        return new DefinirLimiteDepotUseCase(repository, producer);
    }

    @Bean
    public RecupererLimiteJoueurUseCase recupererLimiteJoueurUseCase(LimiteJeuRepository repository) {
        return new RecupererLimiteJoueurUseCase(repository);
    }

    @Bean
    public ModifierLimiteDepotUseCase modifierLimiteDepotUseCase(LimiteJeuRepository repository, JeuResponsableEventProducer producer) {
        return new ModifierLimiteDepotUseCase(repository, producer);
    }

    @Bean
    public SupprimerLimiteDepotUseCase supprimerLimiteDepotUseCase(LimiteJeuRepository repository, JeuResponsableEventProducer producer) {
        return new SupprimerLimiteDepotUseCase(repository, producer);
    }
}