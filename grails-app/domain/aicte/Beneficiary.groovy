package aicte

class Beneficiary {
    String name
    String email
    static belongsTo = [initiative:Initiative, institute:Institute]
    static constraints = {
    }
}
