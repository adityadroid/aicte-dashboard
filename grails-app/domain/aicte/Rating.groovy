package aicte
//TODO: PAGINATION FOR RATINGS
class Rating {
    static belongsTo = [initiative:Initiative,beneficiary:Beneficiary]
    static hasMany = [parameters:ParamValues]
    static constraints = {
    }
}
