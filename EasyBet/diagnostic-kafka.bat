@echo off
echo ======================================
echo   DIAGNOSTIC KAFKA - EasyBet
echo ======================================
echo.

echo [1/5] Verification Docker...
docker --version
if errorlevel 1 (
    echo ERREUR: Docker n'est pas installe ou pas demarre
    pause
    exit /b 1
)
echo ✓ Docker OK
echo.

echo [2/5] Verification des containers...
docker ps --filter "name=kafka" --filter "name=zookeeper" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
echo.

echo [3/5] Test de connexion au port Kafka (9092)...
powershell -Command "Test-NetConnection -ComputerName localhost -Port 9092 -InformationLevel Quiet"
if errorlevel 1 (
    echo ✗ Port 9092 NON accessible
    echo.
    echo SOLUTION: Attendez 30 secondes que Kafka demarre completement
    echo OU executez: docker-compose restart
) else (
    echo ✓ Port 9092 accessible
)
echo.

echo [4/5] Logs Kafka (dernieres lignes)...
docker logs kafka --tail 10
echo.

echo [5/5] Verification des topics...
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list 2>nul
if errorlevel 1 (
    echo ✗ Impossible de lister les topics
    echo Kafka n'est probablement pas encore pret
) else (
    echo ✓ Topics Kafka disponibles
)
echo.

echo ======================================
echo   INSTRUCTIONS
echo ======================================
echo.
echo Si le port 9092 n'est PAS accessible:
echo   1. Attendez 30-60 secondes
echo   2. Relancez ce script: diagnostic-kafka.bat
echo   3. Puis redemarrez votre application Spring Boot
echo.
echo Si le port 9092 EST accessible mais l'app ne se connecte pas:
echo   1. Verifiez application.yml (spring.kafka.bootstrap-servers: localhost:9092)
echo   2. Redemarrez l'application Spring Boot
echo.
pause

