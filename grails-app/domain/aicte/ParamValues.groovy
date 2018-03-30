package aicte

class ParamValues {
    String name
    String value
    static belongsTo = [rating:Rating]
    static constraints = {
    }

}
