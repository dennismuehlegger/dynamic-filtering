package Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BestProd {
    @EmbeddedId
    private BestProdId id;

    @ManyToOne
    @JoinColumn(name = "produkt_id")
    private Produkt produkt;

    @ManyToOne
    @JoinColumn(name = "bestellung_id")
    private Bestellung bestellung;
}
