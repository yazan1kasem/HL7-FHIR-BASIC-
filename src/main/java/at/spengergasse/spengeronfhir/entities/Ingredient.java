package at.spengergasse.spengeronfhir.entities;

import at.spengergasse.spengeronfhir.validators.IngredientValid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "in_ingredient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@IngredientValid
public class Ingredient extends BackboneElement{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "in_icc_id")
    private CodeableConcept itemCodeableConcept;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "in_ir_id")
    private Reference itemReference;

    @Column(name = "in_isactive")
    private Boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "in_s_id")
    private Ratio strength;
}
