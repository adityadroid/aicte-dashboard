package aicte

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class BeneficiaryController {

    BeneficiaryService beneficiaryService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond beneficiaryService.list(params), model:[beneficiaryCount: beneficiaryService.count()]
    }

    def show(Long id) {
        respond beneficiaryService.get(id)
    }

    def save(Beneficiary beneficiary) {
        if (beneficiary == null) {
            render status: NOT_FOUND
            return
        }

        try {
            beneficiaryService.save(beneficiary)
        } catch (ValidationException e) {
            respond beneficiary.errors, view:'create'
            return
        }

        respond beneficiary, [status: CREATED, view:"show"]
    }

    def update(Beneficiary beneficiary) {
        if (beneficiary == null) {
            render status: NOT_FOUND
            return
        }

        try {
            beneficiaryService.save(beneficiary)
        } catch (ValidationException e) {
            respond beneficiary.errors, view:'edit'
            return
        }

        respond beneficiary, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        beneficiaryService.delete(id)

        render status: NO_CONTENT
    }
}
