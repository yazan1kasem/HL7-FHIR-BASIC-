package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name ="co_contact")
public class Contact extends BackboneElement {
    @OneToOne
    @JoinColumn(name="co_cc_fk")
    private CodeableConcept purpose;

    @OneToOne
    @JoinColumn(name="co_hn_fk")
    private HumanName name;
}
