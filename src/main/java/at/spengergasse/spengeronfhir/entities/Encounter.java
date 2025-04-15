package at.spengergasse.spengeronfhir.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="en_encounter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Encounter extends DomainResource{

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "i_en_fk", referencedColumnName = "id")
    private List<Identifier> identifier;

    public enum StatusCode {
        planned,
        arrived,
        triaged,
        @JsonProperty("in-progress")in_progress,
        onleave,
        finished,
        cancelled;

    }
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "en_status")
    private StatusCode status;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "sh_en_id", referencedColumnName = "id")
    private List<StatusHistory> statusHistory;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "t_en_id", referencedColumnName = "id")
    private List<CodeableConcept> type;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Reference subject;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "eoc_en_id", referencedColumnName = "id")
    private List<Reference> episodeOfCare;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "pa_en_id", referencedColumnName = "id")
    private List<Participant> participant;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "a_en_id", referencedColumnName = "id")
    private List<Reference> appointment;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "p_en_id")
    private Period period;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "rr_en_id", referencedColumnName = "id")
    private List<Reference> reasonReference;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "d_en_id", referencedColumnName = "id")
    private List<Diagnosis> diagnosis;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "part_of_id", referencedColumnName = "id")
    private Reference partOf;
}
