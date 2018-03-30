package aicte

class Initiative {
    String name
    Date date = new Date()
    static belongsTo = [owner: User]
    static hasMany = [beneficiaries: Beneficiary,ratings:Rating,parameters:Parameter]
    static constraints = {
    }
}
