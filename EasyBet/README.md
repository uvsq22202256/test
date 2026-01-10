# Easy-Bet Casino - Backend Spring Boot Clean Architecture

## Architecture du Projet

Ce projet suit les principes de **Clean Architecture** avec une separation stricte des couches :

```
com.easybet
├── domain/              # Couche Domain (regles metier)
│   ├── entity/         # Entites metier (SANS annotations)
│   └── repository/     # Interfaces repository
├── usecase/            # Couche Application (use cases)
├── adapters/           # Couche Adapters
│   ├── controller/    # Controllers REST
│   ├── repository.jpa/# Implementations JPA
│   └── dto/           # Data Transfer Objects
└── infrastructure/     # Couche Infrastructure
    ├── config/        # Configurations Spring
    ├── event/         # Kafka Events (EDA)
    └── persistence/   # JPA Entities & Mappers
```

## Technologies Utilisees

- **Spring Boot 4.0.1** - Framework backend
- **Java 17** - Langage
- **H2 Database** - Base de donnees en memoire
- **Kafka** - Event-Driven Architecture
- **Swagger/OpenAPI** - Documentation API
- **Lombok** - Reduction boilerplate code
- **Gradle** - Build tool

## Use Cases Implementes

1. **CreateJoueurUseCase** - Creation d'un nouveau joueur
2. **GetAllJoueursUseCase** - Recuperation de tous les joueurs
3. **GetJoueurByIdUseCase** - Recuperation d'un joueur par ID
4. **DeleteJoueurUseCase** - Suppression d'un joueur

## Demarrage du Projet

### Prerequisites

- Java 17+
- Gradle 9.2+

### Compilation

```bash
.\gradlew.bat clean build
```

### Lancement

```bash
.\gradlew.bat bootRun
```

### Acces aux Services

- **API REST** : http://localhost:8080
- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **H2 Console** : http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:easybetdb`
  - Username: `sa`
  - Password: (vide)

## API Endpoints

### Joueurs

| Methode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/joueurs` | Creer un nouveau joueur |
| GET | `/api/joueurs` | Lister tous les joueurs |
| GET | `/api/joueurs/{id}` | Recuperer un joueur par ID |
| DELETE | `/api/joueurs/{id}` | Supprimer un joueur |

### Exemple de Requete POST

```json
{
  "pseudo": "player123",
  "email": "player@easybet.com"
}
```

### Exemple de Reponse

```json
{
  "id": 1,
  "pseudo": "player123",
  "email": "player@easybet.com",
  "soldeReel": 0.0,
  "soldeBonus": 0.0,
  "kycValide": false
}
```

## Event-Driven Architecture (Kafka)

Le projet utilise Kafka pour publier des evenements:

- **joueur-cree-event** - Publie lors de la creation d'un joueur
- **joueur-supprime-event** - Publie lors de la suppression d'un joueur

## Regles Clean Architecture

✅ **Respectees**:
- Domain layer SANS annotations Spring/JPA
- Use cases independants du framework
- Separation entites metier / entites persistence
- Flux de dependances : Controllers → UseCases → Domain
- DTOs pour exposition API (jamais entites metier)

## Prochaines Etapes

- [ ] Implementer plus de use cases (Depot, Retrait, KYC)
- [ ] Ajouter tests unitaires et integration
- [ ] Configurer Kafka localement
- [ ] Ajouter gestion avancee des exceptions
- [ ] Implementer la securite (Spring Security + JWT)

## Auteurs

Projet TD Clean Architecture - ESIEA

## License

Apache 2.0

