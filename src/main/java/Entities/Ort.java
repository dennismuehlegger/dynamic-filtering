package Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Ort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plz")
    private String plz;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "landkreis_id")
    private Landkreis landkreis;

    @OneToMany(mappedBy = "ort")
    private List<Kunde> kunden;

}
