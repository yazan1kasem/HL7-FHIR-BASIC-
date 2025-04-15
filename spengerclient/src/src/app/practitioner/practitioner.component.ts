import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataService } from '../data.service';
import {Attachment, HumanName, Practitioner} from '../models/Practitioner';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-practitioner',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './practitioner.component.html',
  styleUrl: './practitioner.component.scss'
})
export class PractitionerComponent implements OnInit, OnChanges {
  constructor(private service: DataService) { }
  @Input()
  id: string = "";
  @Output()
  practitionerModified = new EventEmitter<boolean>();
  practitioner: Practitioner = new Practitioner();
  humanNameUse = HumanName.useCode;
  practitionerGender = Practitioner.genderCode;
  ngOnInit(): void {
    this.getPractitioner();
  }
  ngOnChanges(changes: import('@angular/core').SimpleChanges): void {
    this.getPractitioner();
  }
  getPractitioner() {
    this.service.getPractitioner(this.id).subscribe((data: Practitioner) => {
      console.log(data);
      this.practitioner = data;
    });
  }
  deletePractitioner() {
    this.service
      .deletePractitioner(this.practitioner.id!) //! => Promise, that it will not be null
      .subscribe((x) => this.practitionerModified.emit(true));
  }
  updatePractitioner() {
    const newPractitioner: Practitioner = this.practitioner;
    this.service.updatePractitioner(newPractitioner).subscribe({
      next: (practitioner) => {
        console.log('Practitioner updated');
        this.practitioner = practitioner;
        this.practitionerModified.emit(false);
      },
      error: (error) => {
        console.error('Fehler beim Aktualisieren des Practitioners:', error);
        alert(`Fehler: ${error.error?.error || 'Unbekannter Fehler'}`);
      }
    });
  }
  addName() {
    this.practitioner.name?.push(new HumanName())
    }
    deleteName( name:HumanName) {
    const index = this.practitioner.name?.indexOf(name, 0);
    if (index !== undefined && index > -1) {
    this.practitioner.name?.splice(index, 1);
    }
    }
  async onPhotoChange(event: Event) {
    try {
      const input = event.target as HTMLInputElement;
      if (!input.files || input.files.length === 0) {
        alert('Bitte ein Bild auswählen.');
        return;
      }

      const file = input.files[0];
      if (file.type !== 'image/png') {
        alert('Bitte nur PNG-Bilder hochladen.');
        return;
      }

      try {
        const base64 = await this.convertToBase64(file);
        const attachment: Attachment = {
          contentType: 'png',
          data: base64.split(',')[1],
          language: 'de',
          title: 'practitioner photo',
          creation: new Date()
        };
        this.practitioner.photo = [attachment];
        console.log("Photo added:", this.practitioner.photo);
      } catch (conversionError) {
        console.error('Fehler beim Konvertieren der Datei:', conversionError);
        alert('Es gab ein Problem beim Verarbeiten des Bildes. Bitte versuchen Sie es erneut.');
      }
    } catch (error) {
      console.error('Allgemeiner Fehler beim Hochladen des Bildes:', error);
      alert('Ein unerwarteter Fehler ist aufgetreten. Bitte versuchen Sie es erneut.');
    }
  }

  convertToBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      try {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result as string);
        reader.onerror = error => {
          console.error('Fehler beim Lesen der Datei:', error);
          reject('Fehler beim Lesen der Datei.');
        };
        reader.readAsDataURL(file);
      } catch (error) {
        console.error('Allgemeiner Fehler in convertToBase64:', error);
        reject('Ein unerwarteter Fehler ist aufgetreten.');
      }
    });
  }

  deletePhoto() {
    try {
      if (this.practitioner.photo && this.practitioner.photo.length > 0) {
        this.practitioner.photo.splice(0); // Leeres Array, um das Bild zu entfernen
      } else {
        alert('Es gibt kein Foto zum Löschen.');
      }
    } catch (error) {
      console.error('Fehler beim Löschen des Fotos:', error);
      alert('Ein Fehler ist beim Löschen des Fotos aufgetreten.');
    }
  }
}
