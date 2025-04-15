package at.spengergasse.spengeronfhir.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m_medication")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Medication extends DomainResource{

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "i_m_id", referencedColumnName = "id")
    private List<Identifier> identifier = new ArrayList<Identifier>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_c_id")
    private CodeableConcept code;

    public enum StatusCode{
        active,
        inactive,
        @JsonProperty("entered-in-error") entered_in_error
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "m_status")
    private StatusCode status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_m_id")
    private Reference manufacturer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_f_id")
    private CodeableConcept form;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "m_a_id")
    private Ratio amount;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "in_m_id", referencedColumnName = "id")
    private List<Ingredient> ingredient = new ArrayList<Ingredient>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "m_b_id")
    private Batch batch;
}
