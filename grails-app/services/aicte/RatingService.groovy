package aicte

import grails.gorm.services.Service
import org.springframework.web.multipart.MultipartFile

@Service(Rating)
interface RatingService {

    Rating get(Serializable id)

    List<Rating> list(Map args)

    Long count()

    void delete(Serializable id)

    Rating save(Rating rating)

}