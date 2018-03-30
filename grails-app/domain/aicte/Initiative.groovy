package aicte

class Initiative {
    String name
    static belongsTo = [owner: User]
    static hasMany = [beneficiaries: Beneficiary,ratings:Rating,parameters:Parameter]

    static constraints = {
    }
}
