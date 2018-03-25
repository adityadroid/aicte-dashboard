package aicte

import grails.gorm.services.Service

@Service(Beneficiary)
interface BeneficiaryService {

    Beneficiary get(Serializable id)

    List<Beneficiary> list(Map args)

    Long count()

    void delete(Serializable id)

    Beneficiary save(Beneficiary beneficiary)

}