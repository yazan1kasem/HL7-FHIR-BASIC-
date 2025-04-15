package at.spengergasse.spengeronfhir.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "qu_quantity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Quantity extends Element{

    @Column(name = "qu_value")
    private Integer value;

    public enum ComparatorCode{
        @JsonProperty("<") less_than,
        @JsonProperty("<=") less_or_equals,
        @JsonProperty(">=") greater_or_equals,
        @JsonProperty(">") greater_than
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "qu_comparator")
    private ComparatorCode comparator;

    @Column(name = "qu_unit")
    private String unit;

    @Column(name = "qu_system")
    private String system;

    @Column(name = "qu_code")
    private String code;

    public Quantity(String number, String url, String mL) {
    }
}
