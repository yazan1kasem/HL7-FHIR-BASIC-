package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "q_qualification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Qualification extends DomainResource{
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "i_q_id", referencedColumnName = "id")
    private List<Identifier> identifier = new ArrayList<Identifier>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_q_id", referencedColumnName = "id")
    private List<CodeableConcept> code = new ArrayList<CodeableConcept>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_q_id", referencedColumnName = "id")
    private List<Period> period = new ArrayList<Period>();

}
