package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Clob;

@Entity
@Table(name="n_narrative")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Narrative extends Element{
    public enum NarrativeStatus {generated, extensions, additional, empty};

    @Enumerated(EnumType.STRING)
    @Column(name="n_status", nullable = false)
    private NarrativeStatus status;

    @Lob
    @Column(name="n_div", nullable = false)
    private String div;
}
