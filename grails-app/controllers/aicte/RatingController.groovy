package aicte

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
class RatingController {

    RatingService ratingService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

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
                def paramValue = new ParamValues(name: entry.getKey(),value: entry.getValue()[0])
                paramValue.rating = rating
                paramValues.add(paramValue)
            }
            rating.parameters = paramValues
            ratingService.save(rating)
        } catch (ValidationException e) {
            respond rating.errors, view:'create'
            return
        }

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

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        ratingService.delete(id)

        render status: NO_CONTENT
    }
}
