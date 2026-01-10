# 🎯 Guide : Ce que vous devez voir après la création d'un joueur

## 📝 Scénario : Vous créez un joueur

### Commande exécutée :
```powershell
curl -X POST http://localhost:8080/api/joueurs -H "Content-Type: application/json" -d "{\"pseudo\":\"TestPlayer\",\"email\":\"test@player.com\"}"
```

---

## ✅ Ce que vous DEVEZ voir :

### 1️⃣ **Réponse HTTP (200 OK)** 
```json
{
  "id": 1,
  "pseudo": "TestPlayer",
  "email": "test@player.com",
  "soldeReel": 0.0,
  "soldeBonus": 0.0,
  "kycValide": false
}
```
✅ **Cela confirme** : Le joueur est créé et sauvegardé en base de données.

---

### 2️⃣ **Logs de l'application Spring Boot**

Dans la console où vous avez lancé `.\gradlew bootRun`, vous devriez voir :

```
📤 PUBLICATION de l'événement :
2026-01-07 ... INFO  c.e.i.e.JoueurEventProducer : Evenement publie : joueur-cree-event pour joueurId=1

⬇️ Kafka transporte le message...

📥 RÉCEPTION de l'événement (quelques millisecondes plus tard) :
2026-01-07 ... INFO  c.e.i.e.JoueurEventConsumer : 🎉 Événement reçu : Nouveau joueur créé - ID: 1, Pseudo: TestPlayer, Email: test@player.com, Timestamp: 2026-01-07T10:30:45
```

#### 🎯 Explication du flux :

```
1. JoueurController reçoit la requête POST
        ↓
2. CreateJoueurUseCase sauvegarde le joueur en DB
        ↓
3. JoueurEventProducer PUBLIE l'événement dans Kafka
        ↓ (via Kafka broker - topic: joueur-cree-event)
        ↓
4. JoueurEventConsumer CONSOMME l'événement
        ↓
5. Le log 🎉 s'affiche (preuve que l'événement a été traité)
```

---

### 3️⃣ **Dans Kafka UI** (http://localhost:8090)

1. **Accédez à** : http://localhost:8090
2. **Cliquez sur le cluster** : `local`
3. **Allez dans** : `Topics`
4. **Vous verrez** :
   - ✅ `joueur-cree-event` (1 message)
   - ✅ `joueur-supprime-event` (0 message pour l'instant)

5. **Cliquez sur** `joueur-cree-event`
6. **Allez dans l'onglet** `Messages`
7. **Vous verrez le message JSON** :
```json
{
  "joueurId": 1,
  "pseudo": "TestPlayer",
  "email": "test@player.com",
  "timestamp": "2026-01-07T10:30:45"
}
```

---

## 🔍 Comment vérifier que tout fonctionne ?

### Test Complet en 4 étapes :

#### **Étape 1 : Créer un joueur**
```powershell
curl -X POST http://localhost:8080/api/joueurs -H "Content-Type: application/json" -d "{\"pseudo\":\"Player1\",\"email\":\"player1@test.com\"}"
```

**Résultat attendu** : Réponse JSON avec l'ID du joueur créé

---

#### **Étape 2 : Vérifier les logs de l'application**
Cherchez dans la console :
```
✅ INFO ... JoueurEventProducer : Evenement publie : joueur-cree-event pour joueurId=...
✅ INFO ... JoueurEventConsumer : 🎉 Événement reçu : Nouveau joueur créé...
```

**Si vous voyez ces 2 lignes** ➡️ **Kafka fonctionne parfaitement !** 🎉

---

#### **Étape 3 : Vérifier dans Kafka UI**
1. Ouvrez : http://localhost:8090
2. Topics → `joueur-cree-event` → Messages
3. Vous devriez voir votre événement

---

#### **Étape 4 : Supprimer un joueur (bonus)**
```powershell
curl -X DELETE http://localhost:8080/api/joueurs/1
```

**Vous devriez voir dans les logs** :
```
✅ INFO ... JoueurEventProducer : Evenement publie : joueur-supprime-event pour joueurId=1
✅ INFO ... JoueurEventConsumer : 🗑️ Événement reçu : Joueur supprimé - ID: 1...
```

---

## 🚨 Si vous ne voyez PAS les logs des événements :

### Problème 1 : Kafka n'est pas démarré
```powershell
docker ps
```
Vous devez voir : `kafka`, `zookeeper`, `kafka-ui`

**Solution** :
```powershell
docker-compose up -d
```

---

### Problème 2 : L'application ne se connecte pas à Kafka

**Vérifiez dans les logs de démarrage** :
```
❌ ERREUR : Connection to node -1 (localhost/127.0.0.1:9092) could not be established
```

**Solution** : Attendez que Kafka soit complètement démarré (30-60 secondes)

---

### Problème 3 : Les événements ne sont pas consommés

**Vérifiez** `application.yml` :
```yaml
spring:
  kafka:
    consumer:
      properties:
        spring.json.trusted.packages: "*"  # ← IMPORTANT !
```

---

## 📊 Résumé Visuel

```
┌─────────────────────┐
│  POST /api/joueurs  │  ← Vous faites cette requête
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Joueur sauvegardé   │  ← Réponse JSON avec ID
│ en base H2          │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ 📤 PUBLICATION      │  ← Log: "Evenement publie"
│ vers Kafka          │
└──────────┬──────────┘
           │
           ▼  (topic: joueur-cree-event)
┌─────────────────────┐
│  Kafka Broker       │  ← Visible dans Kafka UI
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ 📥 CONSOMMATION     │  ← Log: "🎉 Événement reçu"
│ par le Consumer     │
└─────────────────────┘
```

---

## ✅ Checklist de Validation

- [ ] L'application démarre sans erreur
- [ ] Je peux créer un joueur via POST
- [ ] Je reçois une réponse JSON avec l'ID du joueur
- [ ] Je vois le log : "Evenement publie : joueur-cree-event"
- [ ] Je vois le log : "🎉 Événement reçu : Nouveau joueur créé"
- [ ] Je peux voir l'événement dans Kafka UI (http://localhost:8090)
- [ ] Je peux supprimer un joueur via DELETE
- [ ] Je vois le log : "🗑️ Événement reçu : Joueur supprimé"

---

## 🎓 Ce que cela prouve

Si vous voyez **les 2 logs** (publication + consommation), cela prouve que :

1. ✅ **Event-Driven Architecture** fonctionne
2. ✅ **Kafka** est opérationnel
3. ✅ **Producer** publie correctement
4. ✅ **Consumer** consomme correctement
5. ✅ **Votre architecture Hexagonale + EDA** est complète !

---

## 🆘 Besoin d'aide ?

### Voir les logs Kafka :
```powershell
docker-compose logs -f kafka
```

### Voir les logs de l'application :
```powershell
# Les logs s'affichent déjà dans le terminal où vous avez lancé .\gradlew bootRun
```

### Redémarrer tout :
```powershell
# Arrêter l'application : Ctrl+C dans le terminal
# Redémarrer Kafka
docker-compose restart

# Relancer l'application
.\gradlew bootRun
```

---

## 🎯 RÉSUMÉ : Les 2 logs essentiels

Quand vous créez un joueur, vous DEVEZ voir :

```
1️⃣ INFO ... JoueurEventProducer : Evenement publie : joueur-cree-event pour joueurId=X

2️⃣ INFO ... JoueurEventConsumer : 🎉 Événement reçu : Nouveau joueur créé - ID: X, Pseudo: ..., Email: ...
```

**Si vous voyez ces 2 lignes ➡️ TOUT FONCTIONNE ! 🚀**

---

Bonne chance ! 🎉

