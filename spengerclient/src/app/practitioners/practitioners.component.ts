import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { Practitioner } from '../models/Practitioner';
import { CommonModule } from '@angular/common';
import { PractitionerComponent } from '../practitioner/practitioner.component';


@Component({
  selector: 'app-practitioners',
  standalone: true,
  imports: [CommonModule, PractitionerComponent],
  templateUrl: './practitioners.component.html',
  styleUrl: './practitioners.component.scss'
})
export class PractitionersComponent implements OnInit{
  constructor(private service: DataService) { }
  practitionerArr$: Practitioner[] = [];
  selectedPractitioner: Practitioner = new Practitioner();
  ngOnInit(): void {
    this.getPractitioner();
  }
  getPractitioner() {
    this.service.getPractitioners()
      .subscribe((data: Practitioner[]) => {
        console.log(data);
        this.practitionerArr$ = data
      })
  }
  selectPractitioner(selected: Practitioner) {
    console.log("clicked Practitioner" + selected.id);
    this.selectedPractitioner = selected;
  }
  onPractitionerModified(hidePractitioner: boolean) {
    console.log("Practitioner modified " + hidePractitioner);
    if (hidePractitioner) {
      this.selectedPractitioner = new Practitioner();
    }
    this.getPractitioner()
  }
  createPractitioner() {
    var newPractitioner: Practitioner = new Practitioner()
    this.service.addPractitioner(newPractitioner)
      .subscribe(
        practitioner => {
          console.log("Practitioner created")
          this.onPractitionerModified(true)
        }
      );
  }
}
