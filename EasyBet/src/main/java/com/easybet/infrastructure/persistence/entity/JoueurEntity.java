package com.easybet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA Entity pour Joueur - Infrastructure Layer
 * AVEC annotations JPA (séparation du modèle métier)
 */
@Entity
@Table(name = "joueurs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoueurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String pseudo;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private double soldeReel;

    @Column(nullable = false)
    private double soldeBonus;

    @Column(nullable = false)
    private boolean kycValide;
}

