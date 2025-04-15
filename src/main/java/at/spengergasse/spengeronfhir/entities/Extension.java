package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ex_extension")
@SuperBuilder
public class Extension extends Element{
    @Column(name = "ex_url")
    private String url;
    @Column(name = "ex_value")
    private String value;




}
