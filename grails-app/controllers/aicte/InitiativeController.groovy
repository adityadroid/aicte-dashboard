package aicte

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class InitiativeController {

    InitiativeService initiativeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

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


        for(Rating rating: initiative.ratings){
        }

    }
}
