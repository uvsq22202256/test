package com.easybet.domain.repository;

import com.easybet.domain.entity.SessionJeu;
import java.util.Optional;

public interface SessionJeuRepository {
    SessionJeu save(SessionJeu session);
    Optional<SessionJeu> findById(String id);
}