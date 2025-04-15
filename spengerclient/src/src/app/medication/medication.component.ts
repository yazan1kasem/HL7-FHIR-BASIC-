import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataService } from '../data.service';
import { Medication} from '../models/Medication';

@Component({
  selector: 'app-medication',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './medication.component.html',
  styleUrl: './medication.component.scss'
})
export class MedicationComponent implements OnInit, OnChanges {
  constructor(private service: DataService) { }

  @Input()
  id: string = "";

  @Output() medicationModified = new EventEmitter<boolean>(); 
  medication: Medication = new Medication();
  medicationStatusCodes = Medication.statusCode;

  ngOnInit(): void {
  }

  ngOnChanges(changes: import("@angular/core").SimpleChanges): void {
    this.getMedication();
  }

  getMedication() {
    this.service.getMedication(this.id).subscribe((data: Medication) => {
      console.log(data);
      this.medication = data;
    });
  }

  deleteMedication() {
    this.service
      .deleteMedication(this.medication.id!)
      .subscribe((x) => this.medicationModified.emit(true));
  }

  updateMedication() {
    var newMedication: Medication = this.medication;
    this.service.updateMedication(newMedication).subscribe((medication) => {
      console.log("Medication updated");
      this.medication = medication;
      this.medicationModified.emit(false);
    });
  }
}
