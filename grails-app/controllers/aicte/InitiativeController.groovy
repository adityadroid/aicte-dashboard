package aicte

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class InitiativeController {

    InitiativeService initiativeService
    UtilityService utilityService
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",getRating: "GET", show: "GET",listBeneficiaries: "GET",listInstitutes: "GET",generateReport: "GET"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond initiativeService.list(params), model:[initiativeCount: initiativeService.count()]
    }

    def show(Long id) {
        respond initiativeService.get(id)
    }

    def save(Initiative initiative) {

        if (initiative == null) {
            render status: NOT_FOUND
            return
        }
        //def uploadedFile = request.getPart('picture')
//        if (uploadedFile != null) {//If File was defined/updated
//            initiative.picture = uploadedFile.inputStream.getBytes()
//            initiative.pictureContentType = uploadedFile.getContentType()
//        }
        try {
            initiativeService.save(initiative)
        } catch (ValidationException e) {
            respond initiative.errors, view:'create'
            return
        }

        respond initiative, [status: CREATED, view:"show"]
    }

    def update(Initiative initiative) {
        if (initiative == null) {
            render status: NOT_FOUND
            return
        }

        try {
            initiativeService.save(initiative)
        } catch (ValidationException e) {
            respond initiative.errors, view:'edit'
            return
        }

        respond initiative, [status: OK, view:"show"]
    }

//    def showPicture(Initiative initiative) {
//        response.outputStream << initiative.picture
//        response.outputStream.flush()
//    }

    def listBeneficiaries(Long id){
        def initiative = initiativeService.get(id)
        def beneficiaries = Beneficiary.findAllByInitiative(initiative)
        respond(beneficiaries,status: OK)

    }
    def listInstitutes(Long id){
        def initiative = initiativeService.get(id)
        def beneficiaries = Beneficiary.findAllByInitiative(initiative)
        def institutes = []
        for(Beneficiary beneficiary:beneficiaries){
            println(beneficiary.email)
            if(!institutes.contains(beneficiary.institute))
                institutes.add(beneficiary.institute)
        }
        respond(institutes,status: OK)
        }


    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        initiativeService.delete(id)

        render status: NO_CONTENT
    }
    def getRating(Long id){
        def initiative = initiativeService.get(id)
        if(initiative==null){
            render status: NOT_FOUND
        return
    }
        try {
            def params = initiative.parameters
            def map = new HashMap<String, Integer>()
            for (Parameter parameter : params) {
                map[parameter.name] = 0
            }

            for (Rating rating : initiative.ratings) {
                for (ParamValues pm : rating.parameters) {
                    println(pm.name+" "+pm.value)
                    map[pm.name] = map[pm.name] + Double.valueOf(pm.value)
                }
            }

            for (Parameter parameter : params) {
                if(initiative.ratings.size()!=0)
                map[parameter.name] = map[parameter.name] / initiative.ratings.size()

            }
            respond(summary: map, ratings: initiative.ratings, status: OK)
        }catch(NumberFormatException e){
            e.printStackTrace()
            respond(status: INTERNAL_SERVER_ERROR)
        }catch(ArithmeticException e1){
            e1.printStackTrace()
            respond(status: INTERNAL_SERVER_ERROR)
        }


    }

    def generateReport(Long id){
        def initiative = initiativeService.get(id)
        def beneficiaries = Beneficiary.findAllByInitiative(initiative)
        println(beneficiaries.size())
        def institutes = []
        for(Beneficiary beneficiary:beneficiaries){
            println(beneficiary.email)
            if(!institutes.contains(beneficiary.institute))
                institutes.add(beneficiary.institute)
        }
        println(institutes.size())
        def map = [:]
        def standard = initiative.standard
        map.standard = standard
        def resultInstitutes= []
        for(Institute institute:institutes){
            def instituteBeneficiaries = Beneficiary.findAllByInstitute(institute)
            def quantityNum = instituteBeneficiaries.size()
            def qualityNum = 0
            for(Beneficiary ben: instituteBeneficiaries){
                qualityNum = qualityNum + utilityService.getAvg(ben.rating)
            }
            if(instituteBeneficiaries.size()!=0)
            qualityNum = qualityNum/instituteBeneficiaries.size()
            def nestedMap = [:]
            nestedMap.name = institute.name
            nestedMap.qualityNum = qualityNum
            nestedMap.quantityNum = quantityNum
            def quantityIndicator
            if((quantityNum*100)/standard>75)
                quantityIndicator=2
            else if((quantityNum*100)/standard>50)
                quantityIndicator=1
            else
                quantityIndicator =0

            def qualityIndicator
            if(qualityNum>3.75)
                qualityIndicator=2
            else if(qualityNum>2.5)
                qualityIndicator=1
                else
                qualityIndicator=0
            nestedMap.qualityIndicator = qualityIndicator
            nestedMap.quantityIndicator = quantityIndicator
         resultInstitutes.add(nestedMap)
        }
        map.institutes = resultInstitutes

        respond(map, status: OK)

    }
}
