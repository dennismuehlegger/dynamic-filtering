package Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Kunde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "strasse")
    private String strasse;

    @ManyToOne
    @JoinColumn(name = "ort_id")
    private Ort ort;

    @OneToOne(mappedBy = "kunde")
    private Bestellung bestellung;

}
