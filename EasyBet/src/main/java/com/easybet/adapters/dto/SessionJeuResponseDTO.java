package com.easybet.adapters.dto;

import com.easybet.domain.entity.SessionJeu;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Détails d'une session de jeu")
public class SessionJeuResponseDTO {

    @Schema(description = "ID de la session", example = "sess-123")
    private String id;

    @Schema(description = "ID du joueur", example = "1")
    private String joueurId;

    @Schema(description = "ID du jeu", example = "jeu-456")
    private String jeuId;

    @Schema(description = "Mise engagée", example = "10.00")
    private BigDecimal mise;

    @Schema(description = "Gain réalisé", example = "20.00")
    private BigDecimal gain;

    @Schema(description = "Statut de la session", example = "EN_COURS")
    private String statut;

    @Schema(description = "Date de début")
    private LocalDateTime dateDebut;

    @Schema(description = "Date de fin")
    private LocalDateTime dateFin;

    public SessionJeuResponseDTO(String id, String joueurId, String jeuId, BigDecimal mise, BigDecimal gain, String statut, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.id = id;
        this.joueurId = joueurId;
        this.jeuId = jeuId;
        this.mise = mise;
        this.gain = gain;
        this.statut = statut;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public static SessionJeuResponseDTO fromDomain(SessionJeu session) {
        return new SessionJeuResponseDTO(
                session.getId(),
                session.getJoueurId(),
                session.getJeuId(),
                session.getMise(),
                session.getGain(),
                session.getStatut(),
                session.getDateDebut(),
                session.getDateFin()
        );
    }

    // Getters... (Générez ou ajoutez les getters si besoin, Lombok peut être utilisé si configuré)
    public String getId() { return id; }
    public String getJoueurId() { return joueurId; }
    public String getJeuId() { return jeuId; }
    public BigDecimal getMise() { return mise; }
    public BigDecimal getGain() { return gain; }
    public String getStatut() { return statut; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }
}