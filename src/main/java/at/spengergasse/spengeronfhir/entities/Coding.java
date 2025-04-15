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
@Table(name ="co_coding")
@SuperBuilder
public class Coding extends Element{
    @Column(name="co_system")
    private String system;

    @Column(name="co_code")
    private String code;

    @Column(name="co_display")
    private String display;

    @Column(name="co_version")
    private String version;

    @Column(name="co_userSelected")
    private Boolean userSelected;
}
