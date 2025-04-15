package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="i_identifier")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Identifier extends Element{
    public enum UseCode{
        usual, official, temp, secondary, old
    }
    @Enumerated(EnumType.STRING)
    @Column(name="i_use")
    private UseCode use;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="i_cc_id")
    private CodeableConcept type;
    @Column(name="i_system")
    private String system;
    @Column(name="i_value")
    private String value;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "i_pe_id", referencedColumnName = "id")
    private Period period;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "i_re_id", referencedColumnName = "id")
    private Reference assigner;
}
