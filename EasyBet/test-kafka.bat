@echo off
echo ========================================
echo   DIAGNOSTIC COMPLET KAFKA
echo ========================================
echo.

echo [1] Verification Docker...
docker --version
if errorlevel 1 (
    echo ERREUR: Docker n est pas installe
    pause
    exit /b 1
)
echo.

echo [2] Containers actifs...
docker ps
echo.

echo [3] Test port 9092...
powershell -Command "$result = Test-NetConnection -ComputerName localhost -Port 9092 -InformationLevel Quiet -WarningAction SilentlyContinue; if ($result) { Write-Host 'OK - Port 9092 accessible' -ForegroundColor Green } else { Write-Host 'ERREUR - Port 9092 NON accessible' -ForegroundColor Red }"
echo.

echo [4] Test Kafka directement...
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list 2>nul
if errorlevel 1 (
    echo ERREUR: Kafka ne repond pas
) else (
    echo OK: Kafka repond
)
echo.

echo [5] Configuration Spring Boot...
echo Contenu de application.properties:
findstr "kafka" src\main\resources\application.properties
echo.

echo [6] Build actuel...
if exist "build\resources\main\application.properties" (
    echo Fichier build existe
    findstr "kafka" build\resources\main\application.properties
) else (
    echo Fichier build n existe pas - RECOMPILATION NECESSAIRE
)
echo.

echo ========================================
echo   RECOMMANDATIONS
echo ========================================
echo.
echo 1. Si Kafka ne repond pas:
echo    docker-compose restart
echo    Attendez 60 secondes
echo.
echo 2. Si le build est ancien:
echo    gradlew clean build
echo.
echo 3. Puis demarrez l app:
echo    gradlew bootRun
echo.
pause

