export class Practitioner {
    //wie bei patient
    public static genderCode: Array<string> = ["male", "female", "other",
        "unknown"]

    constructor(
        public id: string = '',
        public resourceType: string = 'Practitioner',
        public identifier?: Array<Identifier>,
        public name?: Array<HumanName>,
        public telecom?: Array<ContactPoint>,
        public active: boolean = false,
        public gender: string = 'unknown',
        public birthDate: Date = new Date(1000, 1, 1),
        public address?: Array<Address>,
        public photo?: Array<Attachment>,
        public qualification?: Array<Qualification>,
        public communication?: Array<CodeableConcept>
    ) { }
}
export class HumanName {
    //wie bei patient
    public static useCode: Array<string> = ["usual" , "official" , "temp" ,
        "nickname" , "anonymous" , "old" , "maiden"]
        
    constructor(
        public id: string = '',
        public use: string = '',
        public text: string = '',
        public family: string = ''
    ) { }
}
export class Identifier {
    constructor(public value: string = '') { }
}
export class ContactPoint {
    constructor(
        public system: string = '',
        public value: string = '',
        public use: string = ''
    ) { }
}

export class Address{
    constructor(
        public use: string = '',
        public type: string = '',
        public text: string = '',
        public line?: Array<string>,
        public city: string = '',
        public district: string = '',
        public state: string = '',
        public postalCode: string = '',
        public country: string = ''
    ) { }
}

export class Attachment{
    constructor(
        public contentType: string = '',
        public language: string = '',
        public data: string = '', // bild
        public title: string = '',
        public creation: Date = new Date(1000,1,1)
    ) { }
}

export class Qualification{
    constructor(
        public code: CodeableConcept,
        public period: Period,
    ) { }
}

export class CodeableConcept{
    constructor(
        public coding?: Array<Coding>,
    ) { }
}

export class Period{
    constructor(
        public start: Date = new Date(1000,1,1),
        public end: Date = new Date(1000,1,1)
    ) { }
}

export class Coding{
    constructor(
        public system: string = '',
        public code: string = ''
    ) { }
}