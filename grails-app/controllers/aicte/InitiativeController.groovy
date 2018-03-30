package aicte

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class InitiativeController {

    InitiativeService initiativeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",getRating: "GET", show: "GET"]

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
        def params = initiative.parameters
        def map = new HashMap<String, Integer>()
        for(Parameter parameter:params){
            map[parameter.name] = 0
        }

        for(Rating rating: initiative.ratings){
            for(ParamValues pm:rating.parameters){
                map[pm.name] = map[pm.name]+ Integer.valueOf(pm.value)
            }
        }
        for(Parameter parameter:params){
            map[parameter.name] = map[parameter.name]/initiative.ratings.size()
        }
        respond(summary:map,ratings:initiative.ratings,status: OK)
    }
}
