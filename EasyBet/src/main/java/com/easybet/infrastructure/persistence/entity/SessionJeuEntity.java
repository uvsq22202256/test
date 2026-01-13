package com.easybet.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions_jeu")
public class SessionJeuEntity {
    @Id
    public String id;
    public String joueurId;
    public String jeuId;
    public LocalDateTime dateDebut;
    public LocalDateTime dateFin;
    public BigDecimal mise;
    public BigDecimal gain;
    public String statut;
}