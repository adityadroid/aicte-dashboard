package aicte

import grails.gorm.services.Service

@Service(ParamValues)
interface ParamValuesService {

    ParamValues get(Serializable id)

    List<ParamValues> list(Map args)

    Long count()

    void delete(Serializable id)

    ParamValues save(ParamValues paramValues)

}