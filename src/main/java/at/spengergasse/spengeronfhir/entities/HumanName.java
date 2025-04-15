package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="hn_humanname")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class HumanName extends Element {
    public enum UseCode{
        usual , official , temp , nickname , anonymous , old , maiden
    }
    @Column(name = "hn_use")
    @Enumerated(EnumType.STRING)
    private UseCode use;
    @Column(name="hn_text")
    private String text;
    @Column(name="hn_family")
    private String family;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="given_humanname", joinColumns =
    @JoinColumn(name="id"))
    private List<String> given = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="prefix_humanname", joinColumns =
    @JoinColumn(name="id"))
    private List<String> prefix = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="suffix_humanname", joinColumns =
    @JoinColumn(name="id"))
    private List<String> suffix = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hn_pe_id")
    private Period period;



}
