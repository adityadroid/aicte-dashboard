package aicte

class Initiative {
    String name
    Date date = new Date()
    static belongsTo = [owner: User]
    static hasMany = [beneficiaries: Beneficiary,ratings:Rating,parameters:Parameter]
    byte[] picture
    String pictureContentType
    static constraints = {
        picture nullable: true, maxSize: 1073741824
        pictureContentType nullable: true
    }
}
