package com.easybet.adapters.repository;

import com.easybet.adapters.repository.jpa.SpringDataSessionJeuRepository;
import com.easybet.domain.entity.SessionJeu;
import com.easybet.domain.repository.SessionJeuRepository;
import com.easybet.infrastructure.persistence.entity.SessionJeuEntity;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class JpaSessionJeuRepositoryAdapter implements SessionJeuRepository {
    private final SpringDataSessionJeuRepository repository;

    public JpaSessionJeuRepositoryAdapter(SpringDataSessionJeuRepository repository) {
        this.repository = repository;
    }

    @Override
    public SessionJeu save(SessionJeu session) {
        SessionJeuEntity entity = new SessionJeuEntity();
        entity.id = session.getId();
        entity.joueurId = session.getJoueurId();
        entity.jeuId = session.getJeuId();
        entity.mise = session.getMise();
        entity.gain = session.getGain();
        entity.dateDebut = session.getDateDebut();
        entity.dateFin = session.getDateFin();
        entity.statut = session.getStatut();
        repository.save(entity);
        return session;
    }

    @Override
    public Optional<SessionJeu> findById(String id) {
        return repository.findById(id).map(e -> new SessionJeu(
                e.id, e.joueurId, e.jeuId, e.mise, e.dateDebut, e.dateFin, e.gain, e.statut
        ));
    }
}