package Entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class BestProdId implements Serializable {
    private long produktId;
    private long bestellungId;
}
