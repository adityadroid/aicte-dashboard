package aicte
//TODO: PAGINATION FOR RATINGS
class Rating {
    static belongsTo = [initiative:Initiative]
    static hasMany = [parameters:ParamValues]
    static constraints = {
    }
}
