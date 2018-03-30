package aicte

class Beneficiary {
    String name

    static belongsTo = [initiative:Initiative, institute:Institute]
    static constraints = {
    }
}
