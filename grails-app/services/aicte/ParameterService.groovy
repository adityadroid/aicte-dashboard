package aicte

import grails.gorm.services.Service

@Service(Parameter)
interface ParameterService {

    Parameter get(Serializable id)

    List<Parameter> list(Map args)

    Long count()

    void delete(Serializable id)

    Parameter save(Parameter parameter)

}