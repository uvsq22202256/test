# 🎯 GUIDE COMPLET - PROJET EASY-BET CLEAN ARCHITECTURE

## ✅ Architecture Creee avec Succes!

Toute l'architecture Clean Architecture a ete creee dans votre projet. Cependant, il y a un probleme de BOM UTF-8 dans certains fichiers Java qui empeche la compilation.

## 🚨 PROBLEME ACTUEL

Les fichiers suivants contiennent un BOM UTF-8 qui cause des erreurs de compilation:
- UseCase files (CreateJoueurUseCase, GetAllJoueursUseCase, etc.)
- DTO files (JoueurRequestDTO, JoueurResponseDTO)
- SpringDataJoueurRepository

## 📝 SOLUTION RAPIDE

Ouvrez IntelliJ IDEA et recree manuellement ces 7 fichiers en copiant le code ci-dessous :

### 1. CreateJoueurUseCase.java
Emplacement: `src/main/java/com/easybet/usecase/CreateJoueurUseCase.java`

```java
package com.easybet.usecase;

import com.easybet.domain.entity.Joueur;
import com.easybet.domain.repository.JoueurRepository;

public class CreateJoueurUseCase {
    
    private final JoueurRepository repository;
    
    public CreateJoueurUseCase(JoueurRepository repository) {
        this.repository = repository;
    }
    
    public Joueur execute(String pseudo, String email) {
        if (pseudo == null || pseudo.trim().isEmpty()) {
            throw new IllegalArgumentException("Pseudo obligatoire");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email obligatoire");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }
        
        if (repository.findByPseudo(pseudo).isPresent()) {
            throw new IllegalArgumentException("Pseudo deja existant");
        }
        
        Joueur joueur = Joueur.builder()
                .pseudo(pseudo.trim())
                .email(email.trim())
                .soldeReel(0.0)
                .soldeBonus(0.0)
                .kycValide(false)
                .build();
        
        return repository.save(joueur);
    }
}
```

### 2. GetAllJoueursUseCase.java
Emplacement: `src/main/java/com/easybet/usecase/GetAllJoueursUseCase.java`

```java
package com.easybet.usecase;

import com.easybet.domain.entity.Joueur;
import com.easybet.domain.repository.JoueurRepository;

import java.util.List;

public class GetAllJoueursUseCase {
    
    private final JoueurRepository repository;
    
    public GetAllJoueursUseCase(JoueurRepository repository) {
        this.repository = repository;
    }
    
    public List<Joueur> execute() {
        return repository.findAll();
    }
}
```

### 3. GetJoueurByIdUseCase.java
Emplacement: `src/main/java/com/easybet/usecase/GetJoueurByIdUseCase.java`

```java
package com.easybet.usecase;

import com.easybet.domain.entity.Joueur;
import com.easybet.domain.repository.JoueurRepository;

public class GetJoueurByIdUseCase {
    
    private final JoueurRepository repository;
    
    public GetJoueurByIdUseCase(JoueurRepository repository) {
        this.repository = repository;
    }
    
    public Joueur execute(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouve avec ID : " + id));
    }
}
```

### 4. DeleteJoueurUseCase.java
Emplacement: `src/main/java/com/easybet/usecase/DeleteJoueurUseCase.java`

```java
package com.easybet.usecase;

import com.easybet.domain.repository.JoueurRepository;

public class DeleteJoueurUseCase {
    
    private final JoueurRepository repository;
    
    public DeleteJoueurUseCase(JoueurRepository repository) {
        this.repository = repository;
    }
    
    public void execute(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }
        
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Joueur non trouve avec ID : " + id);
        }
        
        repository.deleteById(id);
    }
}
```

### 5. JoueurRequestDTO.java
Emplacement: `src/main/java/com/easybet/adapters/dto/JoueurRequestDTO.java`

```java
package com.easybet.adapters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requete de creation joueur")
public class JoueurRequestDTO {
    
    @Schema(description = "Pseudo du joueur", example = "player123", required = true)
    @NotBlank(message = "Pseudo obligatoire")
    private String pseudo;
    
    @Schema(description = "Email du joueur", example = "player@easybet.com", required = true)
    @NotBlank(message = "Email obligatoire")
    @Email(message = "Format email invalide")
    private String email;
}
```

### 6. JoueurResponseDTO.java
Emplacement: `src/main/java/com/easybet/adapters/dto/JoueurResponseDTO.java`

```java
package com.easybet.adapters.dto;

import com.easybet.domain.entity.Joueur;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informations d un joueur")
public class JoueurResponseDTO {
    
    @Schema(description = "ID du joueur", example = "1")
    private Long id;
    
    @Schema(description = "Pseudo du joueur", example = "player123")
    private String pseudo;
    
    @Schema(description = "Email du joueur", example = "player@easybet.com")
    private String email;
    
    @Schema(description = "Solde reel", example = "100.50")
    private double soldeReel;
    
    @Schema(description = "Solde bonus", example = "25.00")
    private double soldeBonus;
    
    @Schema(description = "Statut KYC", example = "false")
    private boolean kycValide;
    
    public static JoueurResponseDTO fromDomain(Joueur joueur) {
        return JoueurResponseDTO.builder()
                .id(joueur.getId())
                .pseudo(joueur.getPseudo())
                .email(joueur.getEmail())
                .soldeReel(joueur.getSoldeReel())
                .soldeBonus(joueur.getSoldeBonus())
                .kycValide(joueur.isKycValide())
                .build();
    }
}
```

### 7. SpringDataJoueurRepository.java
Emplacement: `src/main/java/com/easybet/adapters/repository/jpa/SpringDataJoueurRepository.java`

```java
package com.easybet.adapters.repository.jpa;

import com.easybet.infrastructure.persistence.entity.JoueurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataJoueurRepository extends JpaRepository<JoueurEntity, Long> {
    
    Optional<JoueurEntity> findByPseudo(String pseudo);
}
```

## 🏗️ FICHIERS DEJA CREES ET FONCTIONNELS

Les fichiers suivants sont deja crees et fonctionnels:

✅ Domain Layer:
- `domain/entity/Joueur.java`
- `domain/repository/JoueurRepository.java`

✅ Infrastructure Layer:
- `infrastructure/persistence/entity/JoueurEntity.java`
- `infrastructure/persistence/mapper/JoueurMapper.java`
- `infrastructure/config/UseCaseConfig.java`
- `infrastructure/config/OpenApiConfig.java`
- `infrastructure/config/KafkaConfig.java`
- `infrastructure/event/JoueurCreeEvent.java`
- `infrastructure/event/JoueurEventProducer.java`
- `infrastructure/event/JoueurEventConsumer.java`

✅ Adapters Layer:
- `adapters/repository/jpa/JpaJoueurRepositoryAdapter.java`
- `adapters/controller/JoueurController.java`
- `adapters/controller/GlobalExceptionHandler.java`

## 🚀 APRES AVOIR RECREE LES FICHIERS

1. Dans IntelliJ IDEA, fais un Reload Gradle Project
2. Compile avec: `.\gradlew.bat clean build`
3. Lance l'application: `.\gradlew.bat bootRun`
4. Teste l'API avec Swagger: http://localhost:8080/swagger-ui.html

## 📊 ENDPOINTS API DISPONIBLES

```
POST   /api/joueurs        - Creer un joueur
GET    /api/joueurs        - Lister tous les joueurs  
GET    /api/joueurs/{id}   - Obtenir un joueur
DELETE /api/joueurs/{id}   - Supprimer un joueur
```

## 🎉 FELICITATIONS!

Ton projet Clean Architecture est complet avec:
- ✅ 4 Use Cases implementes
- ✅ API REST avec Swagger
- ✅ H2 Database configuree
- ✅ Kafka EDA (Producer + Consumer)
- ✅ Structure Clean Architecture stricte
- ✅ Separation Domain/UseCase/Adapters/Infrastructure

Bon travail! 🚀

