# 🎯 GUIDE DE TEST - EASYBET API

## 🚀 Lancer l'application

Dans un terminal PowerShell :
```powershell
cd C:\Users\maxim\IdeaProjects\EasyBet
./gradlew --no-daemon bootRun
```

**Attendre** le message : `Started EasyBetApplication in X seconds`
L'app reste à "80% EXECUTING" = **NORMAL** (serveur actif).

---

## 📋 TESTS CURL (dans un AUTRE terminal)

### 1️⃣ Health Check
```powershell
curl http://localhost:8080/actuator/health
```
**Résultat attendu** : `{"status":"UP"}`

---

### 2️⃣ Liste des joueurs (vide au début)
```powershell
curl http://localhost:8080/api/joueurs
```
**Résultat attendu** : `[]`

---

### 3️⃣ Créer un joueur
```powershell
curl -X POST http://localhost:8080/api/joueurs `
  -H "Content-Type: application/json" `
  -d '{\"pseudo\":\"maxim99\",\"email\":\"maxim@easybet.com\"}'
```
**Résultat attendu** :
```json
{
  "id": 1,
  "pseudo": "maxim99",
  "email": "maxim@easybet.com",
  "soldeReel": 0.0,
  "soldeBonus": 0.0,
  "kycValide": false
}
```

---

### 4️⃣ Récupérer tous les joueurs
```powershell
curl http://localhost:8080/api/joueurs
```
**Résultat attendu** : Liste avec le joueur créé

---

### 5️⃣ Récupérer un joueur par ID
```powershell
curl http://localhost:8080/api/joueurs/1
```

---

### 6️⃣ Supprimer un joueur
```powershell
curl -X DELETE http://localhost:8080/api/joueurs/1
```
**Résultat attendu** : Status 204 (No Content)

---

## 🌐 SWAGGER UI

Ouvrir dans le navigateur :
```
http://localhost:8080/swagger-ui.html
```
OU
```
http://localhost:8080/swagger-ui/index.html
```

**Documentation API JSON** :
```
http://localhost:8080/v3/api-docs
```

---

## 🗄️ CONSOLE H2 (Base de données)

```
http://localhost:8080/h2-console
```

**Connexion** :
- JDBC URL: `jdbc:h2:mem:easybetdb`
- Username: `sa`
- Password: *(vide)*

**Query SQL** :
```sql
SELECT * FROM joueurs;
```

---

## 🧪 TEST AVEC INVOKE-WEBREQUEST (PowerShell natif)

### Créer un joueur
```powershell
$body = @{
    pseudo = "player123"
    email = "player@casino.com"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/joueurs" `
  -Method Post `
  -ContentType "application/json" `
  -Body $body
```

### Lister les joueurs
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/joueurs" -Method Get
```

---

## ✅ CHECKLIST VALIDATION TD

- [ ] Application démarre sans erreur
- [ ] GET /api/joueurs retourne liste (vide ou avec données)
- [ ] POST /api/joueurs crée un joueur (201 Created)
- [ ] GET /api/joueurs/{id} retourne le joueur
- [ ] DELETE /api/joueurs/{id} supprime le joueur (204 No Content)
- [ ] Swagger UI accessible et fonctionnel
- [ ] H2 Console montre la table `joueurs`
- [ ] Kafka producer loggue (sans erreur même si Kafka absent)
- [ ] GlobalExceptionHandler gère les erreurs (test avec email invalide)

---

## 🐛 DÉPANNAGE

### Port 8080 déjà utilisé
```powershell
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Relancer proprement
```powershell
./gradlew clean
./gradlew --no-daemon bootRun
```

### Voir les logs en temps réel
Garder le terminal `bootRun` visible pendant les tests.

---

## 🎓 POINTS TD VALIDÉS

✅ **Clean Architecture** (domain/usecase/adapters/infrastructure)
✅ **4 Use Cases** (Create/GetAll/GetById/Delete)
✅ **API REST** (4 endpoints CRUD)
✅ **DTOs** (JoueurRequestDTO/ResponseDTO)
✅ **JPA/H2** (persistence)
✅ **Swagger/OpenAPI** (documentation)
✅ **Kafka Producer** (event publishing)
✅ **Exception Handling** (GlobalExceptionHandler)

**Manque** : Kafka Consumer (bonus)

