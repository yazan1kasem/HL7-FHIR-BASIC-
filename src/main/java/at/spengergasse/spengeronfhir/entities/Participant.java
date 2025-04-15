package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pa_participant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Participant extends BackboneElement{

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "cc_pa_id", referencedColumnName = "id")
    private List<CodeableConcept> type = new ArrayList<CodeableConcept>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pa_p_id")
    private Period period;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pa_r_id")
    private Reference individual;
}
