import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MedicationComponent } from '../medication/medication.component';
import { DataService } from '../data.service';
import { Medication } from '../models/Medication';

@Component({
  selector: 'app-medications',
  standalone: true,
  imports: [CommonModule, MedicationComponent],
  templateUrl: './medications.component.html',
  styleUrl: './medications.component.scss'
})
export class MedicationsComponent implements OnInit{
  constructor(private service: DataService) { }
  medicationArr$: Medication[] = [];
  selectedMedication: Medication = new Medication();
  ngOnInit(): void {
    this.getMedication();
  }
  getMedication() {
    this.service.getMedications()
      .subscribe((data: Medication[]) => {
        console.log(data);
        this.medicationArr$ = data
      })
  }
  selectMedication(selected: Medication) {
    console.log("Selected Medication" + selected.id);
    this.selectedMedication = selected;
  }
  onMedicationModified(hideMedication: boolean) {
    console.log("Medication modified " + hideMedication);
    if (hideMedication) {
      this.selectedMedication = new Medication();
    }
    this.getMedication()
  }
  createMedication() {
    var newMedication: Medication = new Medication()
    this.service.addMedication(newMedication)
      .subscribe(
        medication => {
          console.log("Medication created")
          this.onMedicationModified(true)
        }
      );
  }

}
