package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "ra_ratio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Ratio extends Element{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ra_n_id")
    private Quantity numerator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ra_d_id")
    private Quantity denominator;
}
