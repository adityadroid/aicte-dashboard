package aicte

class Rating {
    static belongsTo = [initiative:Initiative]
    static hasMany = [parameters:ParamValues]
    static constraints = {
    }
}
