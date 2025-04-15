package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="ru_rule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Rule extends BackboneElement{

    public enum TypeCode{
        deny, permit
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ru_type")
    private TypeCode type;
}
