package aicte

class Initiative {
    String name
    String desc
    Date date = new Date()
    static belongsTo = [owner: User]
    static hasMany = [beneficiaries: Beneficiary,ratings:Rating,parameters:Parameter]
    static constraints = {
    }
}
