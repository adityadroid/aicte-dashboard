package aicte

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class InstituteServiceSpec extends Specification {

    InstituteService instituteService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Institute(...).save(flush: true, failOnError: true)
        //new Institute(...).save(flush: true, failOnError: true)
        //Institute institute = new Institute(...).save(flush: true, failOnError: true)
        //new Institute(...).save(flush: true, failOnError: true)
        //new Institute(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //institute.id
    }

    void "test get"() {
        setupData()

        expect:
        instituteService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Institute> instituteList = instituteService.list(max: 2, offset: 2)

        then:
        instituteList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        instituteService.count() == 5
    }

    void "test delete"() {
        Long instituteId = setupData()

        expect:
        instituteService.count() == 5

        when:
        instituteService.delete(instituteId)
        sessionFactory.currentSession.flush()

        then:
        instituteService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Institute institute = new Institute()
        instituteService.save(institute)

        then:
        institute.id != null
    }
}
