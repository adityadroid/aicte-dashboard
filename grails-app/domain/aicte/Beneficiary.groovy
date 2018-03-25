package aicte

class Beneficiary {
    String name
    static belongsTo = [initiative:Initiative]
    static constraints = {
    }
}
