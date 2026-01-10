# 📚 API EasyBet Casino - Documentation Complète

## 🎯 Vue d'ensemble

L'API EasyBet Casino est une API REST complète pour gérer les joueurs, leurs portefeuilles et leurs transactions.

**Base URL** : `http://localhost:8080`

---

## 🔑 Authentification

Actuellement, aucune authentification n'est requise. Tous les endpoints sont publics.

---

## 📋 Endpoints

### 1️⃣ **JOUEURS** - `/joueurs`

#### 🆕 Créer un joueur
**Endpoint** : `POST /joueurs`

**Request** :
```json
{
  "pseudo": "PlayerOne",
  "email": "player@example.com"
}
```

**Response** : `201 CREATED`
```json
{
  "id": 1,
  "pseudo": "PlayerOne",
  "email": "player@example.com",
  "soldeReel": 0.0,
  "soldeBonus": 0.0,
  "kycValide": false
}
```

**Erreurs** :
- `400` : Pseudo ou email invalides / Pseudo déjà existant
- `500` : Erreur serveur

---

#### 📖 Récupérer tous les joueurs
**Endpoint** : `GET /joueurs`

**Response** : `200 OK`
```json
[
  {
    "id": 1,
    "pseudo": "PlayerOne",
    "email": "player@example.com",
    "soldeReel": 0.0,
    "soldeBonus": 0.0,
    "kycValide": false
  },
  {
    "id": 2,
    "pseudo": "PlayerTwo",
    "email": "player2@example.com",
    "soldeReel": 100.0,
    "soldeBonus": 50.0,
    "kycValide": true
  }
]
```

---

#### 🔍 Récupérer un joueur par ID
**Endpoint** : `GET /joueurs/{joueurId}`

**Parameters** :
- `joueurId` (path) : ID du joueur (requis)

**Response** : `200 OK`
```json
{
  "id": 1,
  "pseudo": "PlayerOne",
  "email": "player@example.com",
  "soldeReel": 0.0,
  "soldeBonus": 0.0,
  "kycValide": false
}
```

**Erreurs** :
- `404` : Joueur non trouvé

---

#### ❌ Supprimer un joueur
**Endpoint** : `DELETE /joueurs/{joueurId}`

**Parameters** :
- `joueurId` (path) : ID du joueur (requis)

**Response** : `204 NO CONTENT`

**Erreurs** :
- `404` : Joueur non trouvé

---

### 2️⃣ **PORTEFEUILLES** - `/portefeuilles`

#### 💰 Récupérer le portefeuille d'un joueur
**Endpoint** : `GET /portefeuilles?joueurId={id}`

**Parameters** :
- `joueurId` (query) : ID du joueur (requis)

**Response** : `200 OK`
```json
{
  "id": 1,
  "joueurId": 1,
  "pseudo": "PlayerOne",
  "soldeReel": 1500.50,
  "soldeBonus": 250.00,
  "soldeTotal": 1750.50,
  "devise": "EUR",
  "statut": "ACTIF",
  "dateCreation": "2026-01-09T10:00:00",
  "dateModification": "2026-01-09T14:30:00"
}
```

**Erreurs** :
- `404` : Portefeuille non trouvé

---

#### 📊 Récupérer les détails d'un portefeuille
**Endpoint** : `GET /portefeuilles/{portefeuilleId}`

**Parameters** :
- `portefeuilleId` (path) : ID du portefeuille (requis)

**Response** : `200 OK`
```json
{
  "id": 1,
  "joueurId": 1,
  "pseudo": "PlayerOne",
  "soldeReel": 1500.50,
  "soldeBonus": 250.00,
  "soldeTotal": 1750.50,
  "devise": "EUR",
  "statut": "ACTIF",
  "dateCreation": "2026-01-09T10:00:00",
  "dateModification": "2026-01-09T14:30:00"
}
```

**Erreurs** :
- `404` : Portefeuille non trouvé

---

#### 🔒 Modifier l'état du portefeuille (bloquer/débloquer)
**Endpoint** : `PATCH /portefeuilles/{portefeuilleId}`

**Parameters** :
- `portefeuilleId` (path) : ID du portefeuille (requis)

**Request** :
```json
{
  "statut": "BLOQUE",
  "raison": "Comportement suspect détecté",
  "dateDeblocage": "2026-01-16T00:00:00"
}
```

**Response** : `200 OK`
```json
{
  "id": 1,
  "joueurId": 1,
  "pseudo": "PlayerOne",
  "soldeReel": 1500.50,
  "soldeBonus": 250.00,
  "soldeTotal": 1750.50,
  "devise": "EUR",
  "statut": "BLOQUE",
  "dateCreation": "2026-01-09T10:00:00",
  "dateModification": "2026-01-09T15:05:00"
}
```

**Valeurs possibles** :
- `ACTIF` : Portefeuille actif
- `BLOQUE` : Portefeuille bloqué

**Erreurs** :
- `400` : Statut invalide
- `404` : Portefeuille non trouvé

---

#### 🗑️ Supprimer un portefeuille
**Endpoint** : `DELETE /portefeuilles/{portefeuilleId}`

**Parameters** :
- `portefeuilleId` (path) : ID du portefeuille (requis)

**Response** : `204 NO CONTENT`

**Erreurs** :
- `404` : Portefeuille non trouvé

---

### 3️⃣ **TRANSACTIONS** - `/transactions`

#### 📝 Récupérer l'historique des transactions
**Endpoint** : `GET /transactions?joueurId={id}`

**Parameters** :
- `joueurId` (query) : ID du joueur (requis)
- `type` (query) : Type de transaction - optionnel (DEPOT, RETRAIT, BONUS, TRANSFERT)

**Response** : `200 OK`
```json
[
  {
    "id": 1,
    "portefeuilleId": 1,
    "portefeuilleDestinataireId": null,
    "typeTransaction": "DEPOT",
    "montant": 100.00,
    "ancienSolde": 1500.50,
    "nouveauSolde": 1600.50,
    "statut": "COMPLETE",
    "dateTransaction": "2026-01-09T14:45:00",
    "reference": "DEP-uuid-123",
    "methodePayement": "CARTE_BANCAIRE",
    "description": null
  },
  {
    "id": 2,
    "portefeuilleId": 1,
    "portefeuilleDestinataireId": null,
    "typeTransaction": "RETRAIT",
    "montant": 50.00,
    "ancienSolde": 1600.50,
    "nouveauSolde": 1550.50,
    "statut": "COMPLETE",
    "dateTransaction": "2026-01-09T14:50:00",
    "reference": "RET-uuid-456",
    "methodePayement": "VIREMENT_BANCAIRE",
    "description": null
  }
]
```

---

#### 🔎 Filtrer les transactions par type
**Endpoint** : `GET /transactions?joueurId={id}&type=DEPOT`

**Parameters** :
- `joueurId` (query) : ID du joueur (requis)
- `type` (query) : Type de transaction (DEPOT, RETRAIT, BONUS, TRANSFERT)

**Response** : `200 OK` (Même format que ci-dessus, mais filtré)

---

#### 🔍 Récupérer une transaction spécifique
**Endpoint** : `GET /transactions/{transactionId}`

**Parameters** :
- `transactionId` (path) : ID de la transaction (requis)

**Response** : `200 OK`
```json
{
  "id": 1,
  "portefeuilleId": 1,
  "portefeuilleDestinataireId": null,
  "typeTransaction": "DEPOT",
  "montant": 100.00,
  "ancienSolde": 1500.50,
  "nouveauSolde": 1600.50,
  "statut": "COMPLETE",
  "dateTransaction": "2026-01-09T14:45:00",
  "reference": "DEP-uuid-123",
  "methodePayement": "CARTE_BANCAIRE",
  "description": null
}
```

**Erreurs** :
- `404` : Transaction non trouvée

---

#### ➕ Créer une transaction
**Endpoint** : `POST /transactions`

**Parameters** :
- `joueurId` (query) : ID du joueur (requis)

**Request** (Dépôt) :
```json
{
  "type": "DEPOT",
  "montant": 100.00,
  "methodePayement": "CARTE_BANCAIRE"
}
```

**Request** (Retrait) :
```json
{
  "type": "RETRAIT",
  "montant": 50.00,
  "methodePayement": "VIREMENT_BANCAIRE"
}
```

**Request** (Bonus) :
```json
{
  "type": "BONUS",
  "montant": 50.00,
  "typeBonus": "BIENVENUE",
  "codePromo": "WELCOME50"
}
```

**Request** (Transfert) :
```json
{
  "type": "TRANSFERT",
  "montant": 100.00,
  "joueurDestinataireId": 2
}
```

**Response** : `201 CREATED`
```json
{
  "id": 3,
  "portefeuilleId": 1,
  "portefeuilleDestinataireId": null,
  "typeTransaction": "DEPOT",
  "montant": 100.00,
  "ancienSolde": 1500.50,
  "nouveauSolde": 1600.50,
  "statut": "COMPLETE",
  "dateTransaction": "2026-01-09T14:45:00",
  "reference": "DEP-uuid-123",
  "methodePayement": "CARTE_BANCAIRE",
  "description": null
}
```

**Types de transactions** :
- `DEPOT` : Ajouter des fonds (nécessite `methodePayement`)
- `RETRAIT` : Retirer des fonds (nécessite `methodePayement`)
- `BONUS` : Ajouter un bonus (nécessite `typeBonus` et optionnellement `codePromo`)
- `TRANSFERT` : Transférer vers un autre joueur (nécessite `joueurDestinataireId`)

**Erreurs** :
- `400` : Montant invalide, solde insuffisant, type invalide
- `404` : Joueur ou portefeuille non trouvé

---

## 🔄 Types de données

### Statuts de portefeuille
- `ACTIF` : Portefeuille actif et utilisable
- `BLOQUE` : Portefeuille gelé, aucune transaction possible

### Types de transactions
- `DEPOT` : Dépôt de fonds
- `RETRAIT` : Retrait de fonds
- `BONUS` : Bonus appliqué
- `TRANSFERT` : Transfert entre joueurs

### Méthodes de paiement
- `CARTE_BANCAIRE` : Paiement par carte bancaire
- `VIREMENT_BANCAIRE` : Virement bancaire
- `PORTEFEUILLE` : Transfert depuis un portefeuille

### Types de bonus
- `BIENVENUE` : Bonus de bienvenue
- `FIDELITE` : Bonus de fidélité
- `PROMOTION` : Promotion spéciale

---

## 📊 Codes de statut HTTP

| Code | Signification |
|------|---------------|
| `200` | OK - Requête réussie |
| `201` | CREATED - Ressource créée avec succès |
| `204` | NO CONTENT - Suppression réussie |
| `400` | BAD REQUEST - Données invalides |
| `404` | NOT FOUND - Ressource non trouvée |
| `500` | INTERNAL SERVER ERROR - Erreur serveur |

---

## 🚀 Accès à Swagger UI

**URL** : `http://localhost:8080/swagger-ui.html`

Swagger UI vous permet de tester tous les endpoints directement depuis le navigateur.

---

## 🔗 Accès à Kafka UI

**URL** : `http://localhost:8090`

**Kafka Bootstrap Server** : `kafka:29092` (interne) ou `localhost:9092` (externe)

Kafka UI vous permet de visualiser :
- Les topics Kafka
- Les messages publiés
- Les consommateurs
- Les logs

**Topics disponibles** :
- `portefeuille-depot-effectue`
- `portefeuille-retrait-effectue`
- `portefeuille-bonus-ajoute`
- `portefeuille-transfert-effectue`
- `portefeuille-bloque`
- `joueur-cree-event`
- `joueur-supprime-event`

---

## 📌 Exemples d'utilisation complets

### Exemple 1 : Créer un joueur et ajouter un dépôt

```bash
# 1. Créer un joueur
curl -X POST http://localhost:8080/joueurs \
  -H "Content-Type: application/json" \
  -d '{
    "pseudo": "PlayerOne",
    "email": "player@example.com"
  }'

# Response: 201 Created
# {
#   "id": 1,
#   "pseudo": "PlayerOne",
#   ...
# }

# 2. Récupérer le portefeuille du joueur
curl -X GET "http://localhost:8080/portefeuilles?joueurId=1"

# 3. Effectuer un dépôt
curl -X POST "http://localhost:8080/transactions?joueurId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "type": "DEPOT",
    "montant": 100.00,
    "methodePayement": "CARTE_BANCAIRE"
  }'

# 4. Consulter l'historique
curl -X GET "http://localhost:8080/transactions?joueurId=1"
```

### Exemple 2 : Transférer des fonds

```bash
# Transférer 50€ du joueur 1 au joueur 2
curl -X POST "http://localhost:8080/transactions?joueurId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "type": "TRANSFERT",
    "montant": 50.00,
    "joueurDestinataireId": 2
  }'
```

### Exemple 3 : Bloquer un portefeuille

```bash
# Bloquer le portefeuille du joueur 1
curl -X PATCH "http://localhost:8080/portefeuilles/1" \
  -H "Content-Type: application/json" \
  -d '{
    "statut": "BLOQUE",
    "raison": "Comportement suspect",
    "dateDeblocage": "2026-01-16T00:00:00"
  }'
```

---

## ⚙️ Architecture

L'API suit une architecture **Clean Architecture** :

- **Domain** : Entités métier
- **Use Cases** : Logique métier
- **Adapters** : Contrôleurs et DTOs
- **Infrastructure** : Persistance et configuration

**Événements Kafka** :
- `portefeuille-depot-effectue`
- `portefeuille-retrait-effectue`
- `portefeuille-bonus-ajoute`
- `portefeuille-transfert-effectue`
- `portefeuille-bloque`

---

## 📝 Notes

- Tous les montants sont en **EUR** (Euro)
- Les dates sont au format **ISO 8601** : `YYYY-MM-DDTHH:mm:ss`
- Le solde total d'un portefeuille = soldeReel + soldeBonus
- Les transactions sont automatiquement sauvegardées
- Les événements Kafka sont publiés automatiquement

---

**Version** : 1.0.0  
**Dernière mise à jour** : 2026-01-09

