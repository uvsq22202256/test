package com.easybet.adapters.controller;

import com.easybet.adapters.dto.*;
import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.entity.Transaction;
import com.easybet.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST - Gestion des Transactions
 * Ressource orientée : /transactions
 * Adapters Layer
 */
@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "API RESTful de gestion des transactions")
public class TransactionController {

    private final GetPortefeuilleUseCase getPortefeuilleUseCase;
    private final EffectuerDepotUseCase effectuerDepotUseCase;
    private final EffectuerRetraitUseCase effectuerRetraitUseCase;
    private final AjouterBonusUseCase ajouterBonusUseCase;
    private final TransfertFondsUseCase transfertFondsUseCase;
    private final GetHistoriqueTransactionsUseCase getHistoriqueTransactionsUseCase;

    public TransactionController(GetPortefeuilleUseCase getPortefeuilleUseCase,
                                EffectuerDepotUseCase effectuerDepotUseCase,
                                EffectuerRetraitUseCase effectuerRetraitUseCase,
                                AjouterBonusUseCase ajouterBonusUseCase,
                                TransfertFondsUseCase transfertFondsUseCase,
                                GetHistoriqueTransactionsUseCase getHistoriqueTransactionsUseCase) {
        this.getPortefeuilleUseCase = getPortefeuilleUseCase;
        this.effectuerDepotUseCase = effectuerDepotUseCase;
        this.effectuerRetraitUseCase = effectuerRetraitUseCase;
        this.ajouterBonusUseCase = ajouterBonusUseCase;
        this.transfertFondsUseCase = transfertFondsUseCase;
        this.getHistoriqueTransactionsUseCase = getHistoriqueTransactionsUseCase;
    }

    /**
     * GET /transactions?joueurId=1
     * Récupérer l'historique des transactions d'un joueur
     */
    @GetMapping
    @Operation(summary = "Récupérer l'historique des transactions",
               description = "Retourne la liste de toutes les transactions d'un joueur, optionnellement filtrées par type")
    @ApiResponse(responseCode = "200", description = "Historique récupéré avec succès")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(
            @Parameter(description = "ID du joueur", required = true)
            @RequestParam Long joueurId,
            @Parameter(description = "Type de transaction (DEPOT, RETRAIT, BONUS, TRANSFERT)", required = false)
            @RequestParam(required = false) String type) {

        Portefeuille portefeuille = getPortefeuilleUseCase.execute(joueurId);
        List<Transaction> transactions = getHistoriqueTransactionsUseCase.execute(portefeuille.getId());

        // Filtrer par type si fourni
        if (type != null && !type.isEmpty()) {
            transactions = transactions.stream()
                    .filter(t -> type.equalsIgnoreCase(t.getTypeTransaction()))
                    .collect(Collectors.toList());
        }

        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::fromDomain)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * GET /transactions/{transactionId}
     * Récupérer les détails d'une transaction spécifique
     */
    @GetMapping("/{transactionId}")
    @Operation(summary = "Récupérer une transaction",
               description = "Retourne les détails d'une transaction spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction trouvée"),
            @ApiResponse(responseCode = "404", description = "Transaction non trouvée")
    })
    public ResponseEntity<TransactionResponseDTO> getTransaction(
            @Parameter(description = "ID de la transaction", required = true)
            @PathVariable Long transactionId) {

        // TODO: Implémenter un use case pour récupérer une transaction par ID
        throw new RuntimeException("À implémenter : GetTransactionByIdUseCase");
    }

    /**
     * POST /transactions
     * Créer une nouvelle transaction (dépôt, retrait, bonus, transfert)
     */
    @PostMapping
    @Operation(summary = "Créer une transaction",
               description = "Crée une nouvelle transaction (dépôt, retrait, bonus ou transfert)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou solde insuffisant"),
            @ApiResponse(responseCode = "404", description = "Joueur ou portefeuille non trouvé")
    })
    public ResponseEntity<TransactionResponseDTO> creerTransaction(
            @RequestParam(required = false) Long joueurId,
            @Valid @RequestBody TransactionRequestDTO request) {

        Transaction transaction;

        switch (request.getType().toUpperCase()) {
            case "DEPOT":
                transaction = effectuerDepotUseCase.execute(
                        joueurId,
                        request.getMontant(),
                        request.getMethodePayement(),
                        null
                );
                break;

            case "RETRAIT":
                transaction = effectuerRetraitUseCase.execute(
                        joueurId,
                        request.getMontant(),
                        request.getMethodePayement(),
                        null
                );
                break;

            case "BONUS":
                transaction = ajouterBonusUseCase.execute(
                        joueurId,
                        request.getMontant(),
                        request.getTypeBonus(),
                        request.getCodePromo()
                );
                break;

            case "TRANSFERT":
                transaction = transfertFondsUseCase.execute(
                        joueurId,
                        request.getJoueurDestinataireId(),
                        request.getMontant()
                );
                break;

            default:
                throw new RuntimeException("Type de transaction invalide : " + request.getType());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TransactionResponseDTO.fromDomain(transaction));
    }
}

