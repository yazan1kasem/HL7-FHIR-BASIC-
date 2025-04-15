package at.spengergasse.spengeronfhir.entities;

import at.spengergasse.spengeronfhir.validators.AlleszuLocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ba_batch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Batch extends BackboneElement{

    @Column(name="b_lot_number")
    private String lotNumber;
    @JsonDeserialize(using = AlleszuLocalDateTime.class)
    @Column(name="b_expiration_date")
    private LocalDateTime expirationDate;
}
