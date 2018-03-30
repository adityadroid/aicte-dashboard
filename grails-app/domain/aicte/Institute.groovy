package aicte

class Institute {
    String name
    String email
    static hasMany = [students: Beneficiary]
    static constraints = {
    }
}
