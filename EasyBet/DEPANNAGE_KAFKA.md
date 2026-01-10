# 🔧 DÉPANNAGE KAFKA - KafkaTemplate absent

## ❌ Problème actuel :
```
WARN ... KafkaTemplate absent, publication de l'evenement ignoree
```

Cela signifie que Spring Boot **ne peut pas se connecter à Kafka**.

---

## 🔍 ÉTAPES DE DIAGNOSTIC (à faire dans l'ordre)

### Étape 1 : Vérifier que les containers Docker existent et tournent

```powershell
docker ps
```

**Vous DEVEZ voir 3 containers** :
- `kafka`
- `zookeeper`  
- `kafka-ui`

#### ❌ Si vous ne voyez PAS ces containers :

```powershell
cd C:\Users\maxim\IdeaProjects\EasyBet
docker-compose up -d
```

Attendez **30-60 secondes** que tout démarre.

---

### Étape 2 : Vérifier que Kafka écoute sur le port 9092

```powershell
# Windows PowerShell
Test-NetConnection -ComputerName localhost -Port 9092
```

**Résultat attendu** :
```
TcpTestSucceeded : True
```

#### ❌ Si `TcpTestSucceeded : False` :

Kafka n'est pas encore prêt. **Solutions** :

**Option A** : Attendre plus longtemps (Kafka met ~30-60 secondes à démarrer)
```powershell
Start-Sleep -Seconds 30
Test-NetConnection -ComputerName localhost -Port 9092
```

**Option B** : Redémarrer les containers
```powershell
docker-compose restart
Start-Sleep -Seconds 30
docker ps
```

**Option C** : Voir les logs pour comprendre le problème
```powershell
docker-compose logs kafka
```

---

### Étape 3 : Vérifier que Kafka fonctionne correctement

```powershell
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list
```

**Résultat attendu** : Une liste de topics (peut être vide au début, c'est normal)

#### ❌ Si erreur :

Kafka n'est pas opérationnel. **Relancez tout** :
```powershell
docker-compose down
docker-compose up -d
Start-Sleep -Seconds 60
```

---

### Étape 4 : Vérifier la configuration Spring Boot

Ouvrez `src/main/resources/application.yml` et vérifiez :

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092   # ← IMPORTANT
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      group-id: easybet-group
      auto-offset-reset: earliest
      properties:
        spring.json.trusted.packages: "*"
```

**Vérifiez qu'il N'Y A PAS** cette ligne (elle désactive Kafka) :
```yaml
# ❌ SUPPRIMEZ OU COMMENTEZ CETTE SECTION SI ELLE EXISTE :
spring:
  autoconfigure:
    exclude: org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
```

---

### Étape 5 : Redémarrer l'application Spring Boot

**Une fois que les étapes 1-4 sont OK** :

1. **Arrêtez l'application** (Ctrl+C dans le terminal)
2. **Attendez 5 secondes**
3. **Relancez** :
```powershell
.\gradlew clean bootRun
```

4. **Cherchez dans les logs de démarrage** :

✅ **BON SIGNE** :
```
INFO ... KafkaAdmin : Initialized
INFO ... ConsumerFactory : Creating
INFO ... ProducerFactory : Creating
```

❌ **MAUVAIS SIGNE** :
```
WARN ... Connection to node -1 (localhost/127.0.0.1:9092) could not be established
ERROR ... Failed to construct kafka consumer
```

---

## 🚀 SOLUTION RAPIDE (à essayer en premier)

### Option 1 : Tout redémarrer dans le bon ordre

```powershell
# Terminal 1 : Arrêter l'application Spring Boot
# Faites Ctrl+C

# Terminal 2 : Redémarrer Kafka proprement
cd C:\Users\maxim\IdeaProjects\EasyBet
docker-compose down
docker-compose up -d

# Attendre que Kafka soit prêt (60 secondes)
Start-Sleep -Seconds 60

# Vérifier
docker ps
Test-NetConnection -ComputerName localhost -Port 9092

# Terminal 1 : Relancer l'application
.\gradlew clean bootRun
```

---

### Option 2 : Utiliser une configuration de secours

Si Kafka refuse toujours de se connecter, créez ce fichier de configuration alternatif :

**Fichier** : `src/main/resources/application-kafka.yml`
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      properties:
        spring.json.add.type.headers: false
    consumer:
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      group-id: easybet-group
      auto-offset-reset: earliest
      properties:
        spring.json.trusted.packages: "*"
        spring.json.use.type.headers: false
    admin:
      fail-fast: false
```

Puis lancez :
```powershell
.\gradlew bootRun --args='--spring.profiles.active=kafka'
```

---

## 📊 CHECKLIST DE VALIDATION

Faites cette checklist **dans l'ordre** :

- [ ] **Docker Desktop est lancé**
- [ ] **`docker ps` montre 3 containers (kafka, zookeeper, kafka-ui)**
- [ ] **`Test-NetConnection localhost -Port 9092` retourne `TcpTestSucceeded: True`**
- [ ] **`docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list` ne donne PAS d'erreur**
- [ ] **`application.yml` contient la configuration Kafka**
- [ ] **`application.yml` NE contient PAS l'exclusion `KafkaAutoConfiguration`**
- [ ] **L'application Spring Boot est redémarrée APRÈS Kafka**
- [ ] **Les logs de démarrage montrent `KafkaAdmin : Initialized`**

---

## 🔍 DIAGNOSTIC AVANCÉ

### Voir les logs Kafka en temps réel :
```powershell
docker-compose logs -f kafka
```

### Tester manuellement Kafka :
```powershell
# Créer un topic de test
docker exec kafka kafka-topics --create --topic test --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

# Lister les topics
docker exec kafka kafka-topics --list --bootstrap-server localhost:9092

# Envoyer un message
docker exec -it kafka kafka-console-producer --topic test --bootstrap-server localhost:9092
# Tapez un message puis Ctrl+C

# Lire le message
docker exec kafka kafka-console-consumer --topic test --from-beginning --bootstrap-server localhost:9092 --max-messages 1
```

Si ces commandes **fonctionnent**, Kafka est OK. Le problème vient de Spring Boot.

Si ces commandes **ne fonctionnent pas**, Kafka n'est pas démarré correctement.

---

## 🆘 DERNIER RECOURS

Si rien ne fonctionne, supprimez tout et recommencez :

```powershell
# Supprimer tous les containers et volumes
docker-compose down -v

# Supprimer les images
docker rmi confluentinc/cp-kafka:7.5.0
docker rmi confluentinc/cp-zookeeper:7.5.0
docker rmi provectuslabs/kafka-ui:latest

# Recréer
docker-compose up -d

# Attendre 90 secondes
Start-Sleep -Seconds 90

# Vérifier
docker ps
Test-NetConnection localhost -Port 9092

# Relancer l'application
.\gradlew clean bootRun
```

---

## 📞 AIDE SUPPLÉMENTAIRE

### Fichiers à vérifier :
1. `docker-compose.yml` - Configuration Docker
2. `src/main/resources/application.yml` - Configuration Spring
3. Logs Docker : `docker-compose logs`
4. Logs Spring Boot : Dans le terminal où vous lancez l'app

### Ports utilisés :
- **9092** : Kafka (connexion depuis l'application)
- **2181** : Zookeeper
- **8090** : Kafka UI
- **8080** : Application Spring Boot

---

## ✅ QUAND C'EST RÉPARÉ

Une fois que tout fonctionne, créez un joueur :
```powershell
curl -X POST http://localhost:8080/api/joueurs -H "Content-Type: application/json" -d "{\"pseudo\":\"TestKafka\",\"email\":\"test@kafka.com\"}"
```

**Vous DEVEZ voir** :
```
✅ INFO ... JoueurEventProducer : Evenement publie : joueur-cree-event pour joueurId=1
✅ INFO ... JoueurEventConsumer : 🎉 Événement reçu : Nouveau joueur créé
```

**PAS ce warning** :
```
❌ WARN ... KafkaTemplate absent, publication de l'evenement ignoree
```

---

Bonne chance ! 🚀

