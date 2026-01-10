# Script de démarrage complet Kafka + Application
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   DÉMARRAGE KAFKA + EasyBet" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Étape 1 : Vérifier Docker
Write-Host "[1/6] Vérification Docker..." -ForegroundColor Yellow
try {
    docker --version | Out-Null
    Write-Host "✅ Docker OK" -ForegroundColor Green
} catch {
    Write-Host "❌ Docker n'est pas installé ou pas démarré" -ForegroundColor Red
    exit 1
}
Write-Host ""

# Étape 2 : Démarrer Kafka
Write-Host "[2/6] Démarrage des containers Kafka..." -ForegroundColor Yellow
docker-compose up -d
Write-Host "✅ Containers démarrés" -ForegroundColor Green
Write-Host ""

# Étape 3 : Attendre que Kafka soit prêt
Write-Host "[3/6] Attente que Kafka soit prêt (60 secondes)..." -ForegroundColor Yellow
Start-Sleep -Seconds 60
Write-Host "✅ Attente terminée" -ForegroundColor Green
Write-Host ""

# Étape 4 : Vérifier Kafka
Write-Host "[4/6] Vérification de Kafka..." -ForegroundColor Yellow
docker ps --format "table {{.Names}}\t{{.Status}}" | Where-Object { $_ -match "kafka|zookeeper" }

$portTest = Test-NetConnection -ComputerName localhost -Port 9092 -InformationLevel Quiet -WarningAction SilentlyContinue
if ($portTest) {
    Write-Host "✅ Port 9092 accessible" -ForegroundColor Green
} else {
    Write-Host "❌ Port 9092 NON accessible - Attendez encore 30 secondes" -ForegroundColor Red
    Start-Sleep -Seconds 30
}
Write-Host ""

# Étape 5 : Nettoyer le build
Write-Host "[5/6] Nettoyage du build..." -ForegroundColor Yellow
.\gradlew clean | Out-Null
Write-Host "✅ Build nettoyé" -ForegroundColor Green
Write-Host ""

# Étape 6 : Démarrer l'application
Write-Host "[6/6] Démarrage de l'application Spring Boot..." -ForegroundColor Yellow
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   RECHERCHEZ CES LOGS :" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "✅ INFO ... o.s.k.core.KafkaAdmin : Initialized" -ForegroundColor Green
Write-Host "✅ INFO ... ConsumerConfig values: bootstrap.servers = [localhost:9092]" -ForegroundColor Green
Write-Host "✅ INFO ... ProducerConfig values: bootstrap.servers = [localhost:9092]" -ForegroundColor Green
Write-Host "✅ INFO ... partitions assigned: [joueur-cree-event-0]" -ForegroundColor Green
Write-Host ""
Write-Host "Appuyez sur Ctrl+C pour arrêter l'application" -ForegroundColor Yellow
Write-Host ""

.\gradlew bootRun

