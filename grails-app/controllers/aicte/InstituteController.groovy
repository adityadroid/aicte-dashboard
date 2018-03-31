package aicte

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class InstituteController {

    InstituteService instituteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond instituteService.list(params), model:[instituteCount: instituteService.count()]
    }

    def show(Long id) {
        respond instituteService.get(id)
    }

    def save(Institute institute) {
        if (institute == null) {
            render status: NOT_FOUND
            return
        }

        try {
            instituteService.save(institute)
        } catch (ValidationException e) {
            respond institute.errors, view:'create'
            return
        }

        respond institute, [status: CREATED, view:"show"]
    }

    def update(Institute institute) {
        if (institute == null) {
            render status: NOT_FOUND
            return
        }

        try {
            instituteService.save(institute)
        } catch (ValidationException e) {
            respond institute.errors, view:'edit'
            return
        }

        respond institute, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        instituteService.delete(id)

        render status: NO_CONTENT
    }

}
