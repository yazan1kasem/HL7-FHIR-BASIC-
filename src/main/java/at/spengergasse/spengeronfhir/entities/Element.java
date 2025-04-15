package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name="id")
    private String id;
}
