package aicte

import grails.gorm.services.Service

@Service(Initiative)
interface InitiativeService {

    Initiative get(Serializable id)

    List<Initiative> list(Map args)

    Long count()

    void delete(Serializable id)

    Initiative save(Initiative initiative)

}