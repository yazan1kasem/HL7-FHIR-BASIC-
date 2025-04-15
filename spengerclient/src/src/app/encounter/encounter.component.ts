import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DataService } from '../data.service';
import {Diagnosis, Encounter, Identifier, Period, Reference, StatusHistory} from '../models/Encounter';

@Component({
  selector: 'app-encounter',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './encounter.component.html',
  styleUrl: './encounter.component.scss'
})
export class EncounterComponent implements OnInit, OnChanges {
  constructor(private service: DataService) { }

  @Input()
  id: string = "";
  @Output() encounterModified = new EventEmitter<boolean>();
  encounter: Encounter = new Encounter();
  encounterStatus = Encounter.statusCode;
  statusHistoryStatus = StatusHistory.statusCode;
  identifierUse = Identifier.useCode;
  ngOnInit(): void {
    this.getEncounter();
  }
  ngOnChanges(changes: import("@angular/core").SimpleChanges): void {
    this.getEncounter();
  }
  getEncounter() {
    this.service.getEncounter(this.id).subscribe((data: Encounter) => {
      console.log(data);
      this.encounter = data;
    });
  }
  deleteEncounter() {
    this.service
      .deleteEncounter(this.encounter.id!) //! => Promise, that it will not benull
      .subscribe((x) => this.encounterModified.emit(true));
  }
  updateEncounter() {
    var newEncounter: Encounter = this.encounter;
    this.service.updateEncounter(newEncounter).subscribe((encounter) => {
      console.log("Encounter updated");
      this.encounter = encounter;
      this.encounterModified.emit(false);
    });
  }
  addStatusHistory() {
    const currentPeriod = new Period(new Date(), new Date());
    const newStatusHistory = new StatusHistory("", currentPeriod);
    this.encounter.statusHistory?.push(newStatusHistory);
  }
  deleteStatusHistory(statusHistory: StatusHistory) {
    const index = this.encounter.statusHistory?.indexOf(statusHistory, 0);
    if (index !== undefined && index > -1) {
      this.encounter.statusHistory?.splice(index, 1);
    }
  }

  addIdentifier() {
    this.encounter.identifier?.push(new Identifier());
  }
  deleteIdentifier(identifier:Identifier) {
    const index = this.encounter.identifier?.indexOf(identifier, 0);
    if (index !== undefined && index > -1) {
      this.encounter.identifier?.splice(index, 1);
    }
  }

  addAppointment() {
    this.encounter.appointment?.push(new Reference("", ""));
  }
  deleteAppointment(appointment:Reference) {
    const index = this.encounter.appointment?.indexOf(appointment, 0);
    if (index !== undefined && index > -1) {
      this.encounter.appointment?.splice(index, 1);
    }
  }


  deleteDiagnosis(diagnosis: Diagnosis) {
    const index = this.encounter.diagnosis?.indexOf(diagnosis, 0);
    if (index !== undefined && index > -1) {
      this.encounter.diagnosis?.splice(index, 1);
    }
  }

  addDiagnosis() {
    const newDiagnosis = new Diagnosis();
    this.encounter.diagnosis?.push(newDiagnosis);
  }

  protected readonly Encounter = Encounter;
}
