package aicte

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class ParamValuesController {

    ParamValuesService paramValuesService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond paramValuesService.list(params), model:[paramValuesCount: paramValuesService.count()]
    }

    def show(Long id) {
        respond paramValuesService.get(id)
    }

    def save(ParamValues paramValues) {
        if (paramValues == null) {
            render status: NOT_FOUND
            return
        }

        try {
            paramValuesService.save(paramValues)
        } catch (ValidationException e) {
            respond paramValues.errors, view:'create'
            return
        }

        respond paramValues, [status: CREATED, view:"show"]
    }

    def update(ParamValues paramValues) {
        if (paramValues == null) {
            render status: NOT_FOUND
            return
        }

        try {
            paramValuesService.save(paramValues)
        } catch (ValidationException e) {
            respond paramValues.errors, view:'edit'
            return
        }

        respond paramValues, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        paramValuesService.delete(id)

        render status: NO_CONTENT
    }
}
