package aicte

import grails.gorm.services.Service

@Service(Institute)
interface InstituteService {

    Institute get(Serializable id)

    List<Institute> list(Map args)

    Long count()

    void delete(Serializable id)

    Institute save(Institute institute)

}