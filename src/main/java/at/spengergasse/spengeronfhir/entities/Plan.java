package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "pl_plan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Plan extends DomainResource{
    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pl_r_id", nullable = false)
    private Reference reference;
}
