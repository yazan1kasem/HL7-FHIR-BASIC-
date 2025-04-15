import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { Patient } from '../models/Patient';
import { CommonModule } from '@angular/common';
import { PatientComponent } from '../patient/patient.component';


@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [CommonModule, PatientComponent],
  templateUrl: './patients.component.html',
  styleUrl: './patients.component.scss'
})
export class PatientsComponent implements OnInit {
  constructor(private service: DataService) { }
  patientArr$: Patient[] = [];
  selectedPatient: Patient = new Patient();
  ngOnInit(): void {
    this.getPatient();
  }
  getPatient() {
    this.service.getPatients()
      .subscribe((data: Patient[]) => {
        console.log(data);
        this.patientArr$ = data
      })
  }
  selectPatient(selected: Patient) {
    console.log("clicked Patient" + selected.id);
    this.selectedPatient = selected;
  }
  onPatientModified(hidePatient: boolean) {
    console.log("Patient modified " + hidePatient);
    if (hidePatient) {
      this.selectedPatient = new Patient();
    }
    this.getPatient()
  }
  createPatient() {
    var newPatient: Patient = new Patient()
    this.service.addPatient(newPatient)
      .subscribe(
        patient => {
          console.log("Patient created")
          this.onPatientModified(true)
        }
      );
  }
}
