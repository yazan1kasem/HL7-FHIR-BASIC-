export class Medication {
    public static statusCode: Array<string> = ["active", "inactive", "entered-in-error"]
    constructor(
        public id: string = "",
        public resourceType: string = "Medication",
        public identifier?: Array<Identifier>,
        public code: CodeableConcept = new CodeableConcept([], ""),
        public status: string = Medication.statusCode[0],
        public manufacturer?: Reference,
        public form?: CodeableConcept,
        public amount?: Ratio,
        public ingredient?: Array<Ingredient>,
        public batch: Batch = new Batch()
    ) { }
}
export class Identifier {
    public static useCode: Array<string> = ["usual", "official", "temp", "secondary", "old"]
    constructor(
        public use: string = "",
        public value: string = ""
    ) { }
}
export class CodeableConcept {
    constructor(
        public coding: Array<Coding>,
        public text: string = ""
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
export class Ratio {
    constructor(
        public numerator: Quantity,
        public denominator: Quantity
    ) { }
}
export class Quantity {
    public static useCode: Array<string> = ["<", "<=", ">=", ">"]
    constructor(
        public value: number,
        public comparator: string,
        public unit: string,
        public system: string,
        public code: string
    ) { }
}
export class Ingredient {
    constructor(
        public itemCodeableConcept: CodeableConcept,
        public itemReference: Reference = new Reference("", ""),
        public isActive: boolean,
        public strengh: Ratio
    ) { }
}
export class Batch {
    constructor(
        public lotNumber: string = "",
        public expirationDate: Date = new Date(2025, 1, 1),
    ) { }
}