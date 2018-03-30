package aicte

class ParamValues {
    String name
    String value
    static belongsTo = [rating:Rating]
    static constraints = {
        value max: 5, min: 0
    }

}
