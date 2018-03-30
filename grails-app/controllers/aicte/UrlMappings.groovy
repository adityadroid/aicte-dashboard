package aicte

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
        "/initiative/$id/ratings/"(controller: "initiative", parseRequest: true) {
            action = [GET: "getRating"]
        }
//        "/initiative/$id/picture/"(controller: "initiative", parseRequest: true) {
//            action = [GET: "showPicture"]
//        }
        "/initiative/$id/ratings/add"(controller: "rating", parseRequest: true) {
            action = [POST: "addRating"]
        }
        "/initiative/$id/batch"(controller: "rating", parseRequest: true) {
            action = [POST: "uploadFile"]
        }
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
