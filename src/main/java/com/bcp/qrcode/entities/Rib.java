package com.bcp.qrcode.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "rib")
public class Rib {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cle_rib", nullable = false)
    private String cleRib; // Clé R.I.B

    @Column(name = "code_banque", nullable = false)
    private String codeBanque; // Code Banque

    @Column(name = "code_localite", nullable = false)
    private String codeLocalite; // Code Localité

    @Column(name = "numero_compte", nullable = false)
    private String numeroCompte; // N° de Compte

    @Column(name = "domiciliation", nullable = false)
    private String domiciliation; // Domiciliation

    @Column(name = "code_swift", nullable = false)
    private String codeSwift; // Code SWIFT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Reference to the User entity

    // Dynamically calculate RIB number (concatenation)
    public String getRibNumber() {
        return codeBanque + codeLocalite + numeroCompte + cleRib;
    }
}

