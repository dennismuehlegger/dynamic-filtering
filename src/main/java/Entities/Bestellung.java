package Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Bestellung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bestdat")
    private LocalDate bestDat;

    @Column(name = "liefdat")
    private LocalDate liefDat;

    @OneToOne
    @JoinColumn(name = "kunde_id")
    private Kunde kunde;

    @OneToMany(mappedBy = "bestellung")
    private List<BestProd> bestProd;
}
