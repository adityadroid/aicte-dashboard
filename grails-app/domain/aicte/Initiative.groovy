package aicte

class Initiative {

    static belongsTo = [owner: User]
    static hasMany = [beneficiaries: Beneficiary,ratings:Rating,parameters:Parameter]

    static constraints = {
    }
}
