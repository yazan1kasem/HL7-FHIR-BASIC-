package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
public abstract class BackboneElement extends Element {
}
