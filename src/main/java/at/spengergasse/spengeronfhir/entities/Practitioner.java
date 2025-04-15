package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pr_practitioner")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Practitioner extends DomainResource{
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "i_pr_id", referencedColumnName = "id")
    private List<Identifier> identifier = new ArrayList<Identifier>();

    @Column(name = "pr_active")
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="hn_pr_id",nullable = true, referencedColumnName = "id")
    private List<HumanName> name = new ArrayList<HumanName>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="c_pr_id", referencedColumnName = "id")
    private List<ContactPoint> telecom = new ArrayList<ContactPoint>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name= "a_pr_id", referencedColumnName = "id")
    private List<Address> address = new ArrayList<Address>();

    public enum GenderCode{
        male, female, other, unknown
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "pr_gender")
    private GenderCode gender;

    @PastOrPresent
    @Column(name = "pr_birthdate")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "p_pr_id", referencedColumnName = "id")
    private List<Attachment> photo = new ArrayList<Attachment>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "c_pr_id", referencedColumnName = "id")
    private List<CodeableConcept> communication = new ArrayList<CodeableConcept>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "q_pr_id", referencedColumnName = "id")
    private List<Qualification> qualification = new ArrayList<Qualification>();


}
