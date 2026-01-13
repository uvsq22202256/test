package com.easybet.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "jeux")
public class JeuEntity {
    @Id
    public String id;
    public String nom;
    public String type;
    public BigDecimal tauxRistourne;
    public boolean actif;
}