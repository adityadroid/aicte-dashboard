package aicte

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import static org.apache.poi.ss.usermodel.Cell.*
import java.io.File
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class RatingController {

    RatingService ratingService
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", addRating: "POST",uploadFile: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ratingService.list(params), model:[ratingCount: ratingService.count()]
    }

    def show(Long id) {
        respond ratingService.get(id)
    }

    def save(Rating rating) {
        if (rating == null) {
            render status: NOT_FOUND
            return
        }

        try {
            def parameters = request.getParameterMap()
            List<ParamValues> paramValues = new ArrayList<>()
            for(def entry: parameters){
                if(!entry.getKey().equals("initiative")){
                def paramValue = new ParamValues(name: entry.getKey(),value: entry.getValue()[0])
                paramValue.rating = rating
                paramValues.add(paramValue)
                }
            }
            rating.parameters = paramValues
            ratingService.save(rating)
        } catch (ValidationException e) {
            respond rating.errors, view:'create'
            return
        }
        System.out.println(rating as JSON)
        respond rating, [status: CREATED, view:"show"]
    }

    def update(Rating rating) {
        if (rating == null) {
            render status: NOT_FOUND
            return
        }

        try {
            ratingService.save(rating)
        } catch (ValidationException e) {
            respond rating.errors, view:'edit'
            return
        }

        respond rating, [status: OK, view:"show"]
    }

    def addRating(Long id){
        def initiative = Initiative.findById(id)
        def beneficiaryName = request.getParameter("beneficiaryName")
        def beneficiaryEmail = request.getParameter("beneficiaryEmail")
        def instituteId = Integer.parseInt(request.getParameter("institute"))
        def institute = Institute.findById(instituteId)
        def beneficiary = new Beneficiary(name: beneficiaryName,email: beneficiaryEmail,institute: institute,initiative: initiative)
        Rating rating = new Rating()
        List<ParamValues> paramValues = new ArrayList<>()
        for (Parameter par: initiative.parameters){
            def paramValue = new ParamValues(name: par.name,value: request.getParameter(par.name))
            paramValue.rating = rating
            paramValues.add(paramValue)
            }
        rating.parameters = paramValues
        rating.initiative = initiative
        initiative.ratings.add(rating)
        beneficiary.rating = rating
        rating.beneficiary = beneficiary
        initiative.save(true)
        rating.save(true)
        beneficiary.save(true)
        println(initiative)
        println(rating)
        println(beneficiary)
        respond(response:[message:"Rating created"],status: OK)

    }
    def uploadFile(Long id) {
        def initiative = Initiative.findById(id)
        def file = request.getPart('excelFile')
            if(file!=null) {
            def sheetheader = []
            def values = []

            def workbook = new XSSFWorkbook(file.getInputStream())
            def sheet = workbook.getSheetAt(0)

            for (cell in sheet.getRow(0).cellIterator()) {
                sheetheader << cell.stringCellValue
                println(cell.stringCellValue)
            }

            def headerFlag = true
            for (row in sheet.rowIterator()) {
                if (headerFlag) {
                    headerFlag = false
                    continue
                }
                def value = ''
                def map = [:]
                for (cell in row.cellIterator()) {
                    switch(cell.cellType) {
                        case 1:
                            value = cell.stringCellValue
                            map["${sheetheader[cell.columnIndex]}"] = value
                              //map["${sheetheader}"] = value

                            break
                        case 0:
                            value = cell.numericCellValue
                            map["${sheetheader[cell.columnIndex]}"] = value
                            break
                        default:
                            value = ''
                    }
                }
                println(map)
                values.add(map)
            }

            values.each { v ->
                if(v) {
                    def beneficiaryName = v.beneficiaryName
                    println(beneficiaryName)
                    def beneficiaryEmail = v.beneficiaryEmail
                    def instituteId =(Integer)v.institute
                    println(instituteId)
                    def institute = Institute.findById(instituteId)
                    println(institute.name)
                    def beneficiary = new Beneficiary(name: beneficiaryName,email: beneficiaryEmail,institute: institute,initiative: initiative)
                    Rating rating = new Rating()
                    List<ParamValues> paramValues = new ArrayList<>()
                    for (Parameter par: initiative.parameters){
                        println(par.name+" "+v.getAt(par.name))
                        def paramValue = new ParamValues(name: par.name,value: v.getAt(par.name))
                        println(paramValue)
                        paramValue.rating = rating
                        paramValues.add(paramValue)
                    }
                    rating.parameters = paramValues
                    rating.initiative = initiative
                    initiative.ratings.add(rating)
                    beneficiary.rating = rating
                    rating.beneficiary = beneficiary
                    initiative.save(flush:true,failOnError:true)
                    rating.save(flush:true,failOnError:true)
                    beneficiary.save(flush:true,failOnError:true)
                    println(initiative)
                    println(rating)
                    println(beneficiary)

                }
            }

                render([message:"Created"] as JSON,status: OK)
                return
        }else{
            respond(status: BAD_REQUEST,response:["Message":"Empty file"] )
        }
    }





    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        ratingService.delete(id)

        render status: NO_CONTENT
    }
}
