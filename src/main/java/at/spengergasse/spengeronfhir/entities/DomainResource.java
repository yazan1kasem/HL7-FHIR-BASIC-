package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class DomainResource extends Resource{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="dr_text", referencedColumnName = "id")
    private Narrative text;
}
