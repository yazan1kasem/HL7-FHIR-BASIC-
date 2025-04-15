import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { EncounterComponent } from '../encounter/encounter.component';
import { DataService } from '../data.service';
import { Encounter } from '../models/Encounter';

@Component({
  selector: 'app-encounters',
  standalone: true,
  imports: [CommonModule, EncounterComponent],
  templateUrl: './encounters.component.html',
  styleUrl: './encounters.component.scss'
})
export class EncountersComponent implements OnInit {
  constructor(private service: DataService) { }
  encounterArr$: Encounter[] = [];
  selectedEncounter: Encounter = new Encounter();
  ngOnInit(): void {
    this.getEncounter();
  }
  getEncounter() {
    this.service.getEncounters()
      .subscribe((data: Encounter[]) => {
        console.log(data);
        this.encounterArr$ = data
      })
  }
  selectEncounter(selected: Encounter) {
    console.log("clicked Encounter" + selected.id);
    this.selectedEncounter = selected;
  }
  onEncounterModified(hideEncounter: boolean) {
    console.log("Encounter modified " + hideEncounter);
    if (hideEncounter) {
      this.selectedEncounter = new Encounter();
    }
    this.getEncounter()
  }
  createEncounter() {
    var newEncounter: Encounter = new Encounter()
    this.service.addEncounter(newEncounter)
      .subscribe(
        encounter => {
          console.log("Encounter created")
          this.onEncounterModified(true)
        }
      );
  }
}
