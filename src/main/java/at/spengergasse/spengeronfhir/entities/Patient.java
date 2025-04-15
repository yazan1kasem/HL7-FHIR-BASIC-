package at.spengergasse.spengeronfhir.entities;

import at.spengergasse.spengeronfhir.validators.PatientValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "p_patient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@PatientValid
public class Patient extends DomainResource{
   public enum GenderCode{
      male, female, other, unknown
   }

   @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name = "i_p_id", referencedColumnName = "id")
   private List<Identifier> identifier = new ArrayList<Identifier>();

   @Column(name = "p_active")
   private Boolean active;

   @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name="hn_p_id",nullable = true, referencedColumnName = "id")
   private List<HumanName> name = new ArrayList<HumanName>();

   @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name="c_p_id", referencedColumnName = "id")
   private List<ContactPoint> telecom = new ArrayList<ContactPoint>();

   @Enumerated(EnumType.STRING)
   @Column(name = "p_gender")
   private GenderCode gender;

   @PastOrPresent
   @Column(name = "p_birthdate")
   private LocalDate birthDate;

   @Column(name= "p_deceasedboolean")
   private Boolean deceasedBoolean;

   @PastOrPresent
   @Column(name="p_deceaseddatetime")
   private LocalDateTime deceasedDateTime;

   @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name= "p_a_id", referencedColumnName = "id")
   private List<Address> address = new ArrayList<Address>();

   @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name = "p_p_id", referencedColumnName = "id")
   private List<Attachment> photo = new ArrayList<Attachment>();

   @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
   @JoinColumn(name = "p_g_id", referencedColumnName = "id")
   private List<Reference> generalPractitioner = new ArrayList<Reference>();
}
