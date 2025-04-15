package at.spengergasse.spengeronfhir.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Clob;
import java.time.LocalDateTime;

@Entity
@Table(name = "a_attachment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Attachment extends Element{
    public enum AttachmentContentType{
        png
    }
    public enum AttachmentLanguage{
        de, en, fr, it, es
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "a_contenttype")
    private AttachmentContentType contentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "a_language")
    private AttachmentLanguage language;

    @Column(name = "a_data", columnDefinition = "LONGTEXT")
    private String data;

    @Column(name = "a_title")
    private String title;

    @Column(name = "a_creation")
    private LocalDateTime creation;


}
