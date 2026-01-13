package com.easybet.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "limites_jeu")
@Data
public class LimiteJeuEntity {
    @Id
    public String id;
    public String joueurId;
    public BigDecimal limiteDepotHebdomadaire;
}