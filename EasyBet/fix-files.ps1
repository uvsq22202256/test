# Script PowerShell pour recreer les fichiers Java sans BOM UTF-8
# Execute ce script si tu as des problemes de compilation dus au BOM

$utf8NoBom = New-Object System.Text.UTF8Encoding $false
$baseDir = "C:\Users\maxim\IdeaProjects\EasyBet\src\main\java\com\easybet"

Write-Host "Recreation des fichiers Use Cases..." -ForegroundColor Green

# CreateJoueurUseCase
$content1 = @'
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
'@
[System.IO.File]::WriteAllText("$baseDir\usecase\CreateJoueurUseCase.java", $content1, $utf8NoBom)

# GetAllJoueursUseCase
$content2 = @'
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
'@
[System.IO.File]::WriteAllText("$baseDir\usecase\GetAllJoueursUseCase.java", $content2, $utf8NoBom)

# GetJoueurByIdUseCase
$content3 = @'
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
'@
[System.IO.File]::WriteAllText("$baseDir\usecase\GetJoueurByIdUseCase.java", $content3, $utf8NoBom)

# DeleteJoueurUseCase
$content4 = @'
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
'@
[System.IO.File]::WriteAllText("$baseDir\usecase\DeleteJoueurUseCase.java", $content4, $utf8NoBom)

Write-Host "Recreation des DTOs..." -ForegroundColor Green

# JoueurRequestDTO
$content5 = @'
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
'@
[System.IO.File]::WriteAllText("$baseDir\adapters\dto\JoueurRequestDTO.java", $content5, $utf8NoBom)

# JoueurResponseDTO
$content6 = @'
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

    @Schema(description = "Solde reel du joueur", example = "100.50")
    private double soldeReel;

    @Schema(description = "Solde bonus du joueur", example = "25.00")
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
'@
[System.IO.File]::WriteAllText("$baseDir\adapters\dto\JoueurResponseDTO.java", $content6, $utf8NoBom)

Write-Host "Recreation du repository..." -ForegroundColor Green

# SpringDataJoueurRepository
$content7 = @'
package com.easybet.adapters.repository.jpa;

import com.easybet.infrastructure.persistence.entity.JoueurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataJoueurRepository extends JpaRepository<JoueurEntity, Long> {

    Optional<JoueurEntity> findByPseudo(String pseudo);
}
'@
[System.IO.File]::WriteAllText("$baseDir\adapters\repository\jpa\SpringDataJoueurRepository.java", $content7, $utf8NoBom)

Write-Host "Fichiers recrees avec succes!" -ForegroundColor Cyan
Write-Host "Compile maintenant avec: .\gradlew.bat build" -ForegroundColor Yellow

