# HL7-FHIR-BASIC – Fullstack Healthcare Data Management System

![image](https://github.com/user-attachments/assets/da03654c-0357-4a6c-ba9f-f38d8f576e9b)

*Abbildung: Beispielhafte Ansicht des Angular-Frontends (Screenshot hier einfügen)*

## Projektbeschreibung

Dieses Projekt stellt eine vollständige Fullstack-Anwendung zur Verwaltung medizinischer Daten gemäß dem HL7-FHIR-Standard dar. Ziel ist es, ein interoperables System zu entwickeln, das sowohl die strukturierte Speicherung medizinischer Informationen als auch deren sichere Verarbeitung und Darstellung ermöglicht. Die Anwendung kombiniert moderne Webtechnologien mit bewährten Backend-Architekturen und bietet darüber hinaus eine produktionsreife Bereitstellungslösung.

Die Anwendung bildet zentrale FHIR-Ressourcen wie `Patient`, `Practitioner`, `Medication`, `Encounter` u. a. ab und stellt RESTful APIs bereit, über die diese Ressourcen gelesen, erstellt, geändert und gelöscht werden können. Auf der Client-Seite ermöglicht eine Angular-basierte Oberfläche eine benutzerfreundliche Interaktion mit den medizinischen Daten. Als persistente Datenbank wird MySQL verwendet, das über JPA/Hibernate im Backend angebunden ist.

Das Projekt wurde mit Fokus auf Modularität, Wartbarkeit und realweltnahe Umsetzbarkeit konzipiert und eignet sich sowohl für den produktiven Einsatz als auch als Referenzlösung für Ausbildung und Lehre im Gesundheits-IT-Umfeld.


## Technologien

### Backend
- Java 17 mit Spring Boot
- Hibernate (JPA) für ORM und Datenbankintegration
- MySQL als relationale Datenbank
- HL7 FHIR R4 Standard zur Modellierung medizinischer Daten
- Maven zur Projektverwaltung und zum Build
- Custom Validatoren (z. B. für Sozialversicherungsnummern)

### Frontend
- Angular mit TypeScript
- Template-Driven Forms mit bidirektionaler Datenbindung
- HTTP-Client zur Kommunikation mit REST-API
- Bootstrap / SCSS für das Styling

### Infrastruktur & Bereitstellung
- Docker zur Containerisierung der Anwendung und Datenbank
- Docker Compose zur Orchestrierung aller Dienste
- `render.yaml` zur automatisierten Bereitstellung auf Render.com (CI/CD-fähig)



## Projektstruktur (Auszug)


HL7-FHIR-BASIC-/
│
├── spengerclient/                  → Angular-Frontend
├── src/main/java/…/entities/       → FHIR-Entitäten wie Patient, Medication, Practitioner
├── src/main/java/…/controller/     → REST-Controller für die Ressourcen
├── src/main/java/…/repositories/   → JPA-Repositories
├── src/main/java/…/services/       → Business-Logik und Validierungen
├── render.yaml                     → Bereitstellungskonfiguration (Render.com)
├── docker-compose.yml             → Container-Orchestrierung (App + DB)
├── Dockerfile                     → Backend-Containerdefinition
├── pom.xml                        → Maven-Konfiguration


## Initialisierung & Start

### Voraussetzungen
- Java 17
- Node.js und npm (für Angular)
- Docker & Docker Compose

### Lokale Installation

1. Repository klonen:
   ```bash
   git clone https://github.com/yazan1kasem/HL7-FHIR-BASIC-.git
   cd HL7-FHIR-BASIC-
   ```

2. Backend bauen:
   ```bash
   ./mvnw clean install
   ```

3. Anwendung & Datenbank starten:
   ```bash
   docker-compose up
   ```

4. Frontend starten:
   ```bash
   cd spengerclient
   npm install
   ng serve
   ```

---

## Produktionsbereitstellung

Dieses Projekt ist durch die im Repository enthaltene Datei `render.yaml` für die automatisierte Bereitstellung vorbereitet. Die Konfiguration ermöglicht:

- Definition von Build- und Start-Kommandos
- Health Checks
- automatische Bindung externer Dienste wie Datenbanken
- einfache Reproduzierbarkeit der Umgebung

Die Anwendung kann so direkt über Render.com oder vergleichbare Plattformen in eine produktionsähnliche Umgebung überführt werden.

---

## Fazit

Diese Anwendung stellt ein voll funktionsfähiges System zur Verwaltung medizinischer Daten nach dem HL7-FHIR-Standard dar. Sie zeigt praxisnah die Kombination moderner Fullstack-Technologien mit medizinischer Datenmodellierung und produktionsreifer Infrastruktur. Sie eignet sich gleichermaßen als Lehrprojekt, Demonstrator oder Grundlage für produktive eHealth-Systeme.
