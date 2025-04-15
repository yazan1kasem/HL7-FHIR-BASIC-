import { CommonModule } from '@angular/common';
import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from
  '@angular/core';
import { DataService } from '../data.service';
import { HumanName, Patient } from '../models/Patient';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-patient',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './patient.component.html',
  styleUrl: './patient.component.scss'
})
export class PatientComponent implements OnInit, OnChanges {
  constructor(private service: DataService) { }
  //Input parameter from patient list, which patient details should be displayed
  @Input()
  id: string = "";
  //Notify the parent View to refresh the list
  @Output() patientModified = new EventEmitter<boolean>();
  patient: Patient = new Patient();
  humanNameUse = HumanName.useCode;
  patientGender = Patient.genderCode;
  ngOnInit(): void {
    this.getPatient();
  }
  ngOnChanges(changes: import("@angular/core").SimpleChanges): void {
    this.getPatient();
  }
  getPatient() {
    this.service.getPatient(this.id).subscribe((data: Patient) => {
      console.log(data);
      this.patient = data;
    });
  }
  deletePatient() {
    this.service
      .deletePatient(this.patient.id!) //! => Promise, that it will not benull
      .subscribe((x) => this.patientModified.emit(true));
  }
  updatePatient() {
    var newPatient: Patient = this.patient;
    this.service.updatePatient(newPatient).subscribe((patient) => {
      console.log("Patient updated");
      this.patient = patient;
      this.patientModified.emit(false);
    });
  }
  addName() {
    this.patient.name?.push(new HumanName())
  }
  deleteName(name: HumanName) {
    const index = this.patient.name?.indexOf(name, 0);
    if (index !== undefined && index > -1) {
      this.patient.name?.splice(index, 1);
    }
  }
}
