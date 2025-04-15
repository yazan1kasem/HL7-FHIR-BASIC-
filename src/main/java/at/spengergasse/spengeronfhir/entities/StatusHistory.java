package at.spengergasse.spengeronfhir.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="sh_statushistory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StatusHistory extends BackboneElement{

    public enum StatusCode{
        planned,
        arrived,
        triaged,
        @JsonProperty("in-progress")in_progress,
        onleave,
        finished,
        cancelled;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "sh_status")
    @NotNull
    private StatusCode status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sh_p_id", nullable = false)
    @NotNull
    private Period period;

}
