package aicte

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.*
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class ParameterController {

    ParameterService parameterService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond parameterService.list(params), model:[parameterCount: parameterService.count()]
    }

    def show(Long id) {
        respond parameterService.get(id)
    }

    def save(Parameter parameter) {
        if (parameter == null) {
            render status: NOT_FOUND
            return
        }
        def initiative = parameter.initiative
        def parameters = initiative.parameters
        for(Parameter par:parameters){
            if(par.name.equals(parameter.name)){
                render([message:"parameter ${parameter.name} already exists!"] as JSON,status: BAD_REQUEST)
                return
            }

        }
        try {
            parameterService.save(parameter)
        } catch (ValidationException e) {
            respond parameter.errors, view:'create'
            return
        }

        respond parameter, [status: CREATED, view:"show"]
    }

    def update(Parameter parameter) {
        if (parameter == null) {
            render status: NOT_FOUND
            return
        }

        try {
            parameterService.save(parameter)
        } catch (ValidationException e) {
            respond parameter.errors, view:'edit'
            return
        }

        respond parameter, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        parameterService.delete(id)

        render status: NO_CONTENT
    }
}
