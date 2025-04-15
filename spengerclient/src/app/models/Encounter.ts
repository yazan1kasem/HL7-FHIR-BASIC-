export class Encounter {
    public static statusCode: Array<string> = ["planned", "arrived", "triaged", "in_progress", "onleave", "finished", "cancelled"]
    constructor(
        public id: string = "",
        public resourceType: string = "Encounter",
        public identifier?: Array<Identifier>,
        public status: string = Encounter.statusCode[0],
        public statusHistory?: Array<StatusHistory>,
        public type?: Array<CodeableConcept>,
        public subject?: Reference,
        public episodeOfCare?: Array<Reference>,
        public participant?: Array<Participant>,
        public appointment?: Array<Reference>,
        public period?: Period,
        public reasonReference?: Array<Reference>,
        public diagnosis?: Array<Diagnosis>,
        public partOf?: Reference
    ) { }
}
export class Identifier {
    public static useCode: Array<string> = ["usual", "official", "temp", "secondary", "old"]
    constructor(
        public use: string = "",
        public value: string = ""
    ) { }
}
export class StatusHistory {
    public static statusCode: Array<string> = ["planned", "arrived", "triaged", "in_progress", "onleave", "finished", "cancelled"]
        constructor(
        public status: string="",
        public period?: Period
    ) { }
}
export class CodeableConcept {
    constructor(
        public coding: Array<Coding>,
    ) { }
}
export class Coding {
    constructor(
        public system: string,
        public code: string,
        public display: string
    ) { }
}
export class Reference {
    constructor(
        public reference: string,
        public display: string
    ) { }
}
export class Participant {
    constructor(
        public id:string,
        public type: Array<CodeableConcept>,
        public period: Period,
        public individual: Reference
    ) { }
}
export class Period {
    constructor(
        public start: Date,
        public end: Date
    ) { }
}
export class Diagnosis {
    constructor(
        public condition?: Reference,
        public use?: CodeableConcept,
        public rank?: number
    ) { }
}
