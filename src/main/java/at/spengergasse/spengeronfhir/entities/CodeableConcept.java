package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name ="cc_codeableconcept")
@SuperBuilder
public class CodeableConcept extends  Element {
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="cc_co_fk")
    private List<Coding> coding;
    @Column(name="cc_text")
    private String text;
}
