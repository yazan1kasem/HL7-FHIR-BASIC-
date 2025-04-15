package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="r_reference")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Reference extends Element {

    @Column(name="r_ref")
    private String reference;

    @Column(name="r_display")
    private String display;
}
