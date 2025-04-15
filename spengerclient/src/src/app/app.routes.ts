import { Routes } from '@angular/router';
import { PatientsComponent } from './patients/patients.component';
import { PractitionersComponent } from './practitioners/practitioners.component';
import { EncountersComponent } from './encounters/encounters.component';
import { MedicationsComponent } from './medications/medications.component';


export const routes: Routes = [{
    path: '',
    title: "Patienten",
    component: PatientsComponent
},
{
    path: 'patients',
    title: "Patienten",
    component: PatientsComponent
},
{
    path: 'practitioners',
    title: "Practititioners",
    component: PractitionersComponent
},
{
    path: 'encounters',
    title: "Encounters",
    component: EncountersComponent
},
{
    path: 'medications',
    title: "Medications",
    component: MedicationsComponent
}
];
