package aicte

class BootStrap {

    def init = { servletContext ->

//        grails.converters.JSON.registerObjectMarshaller(User){
//            def output = [:]
//            output['id'] = it.id
//            output['username'] = it.username
//            output['name'] = it.name
//            output['email'] = it.email
//            output['age'] = it.age
//            return output
//        }
//        grails.converters.JSON.registerObjectMarshaller(Initiative){
//            def output = [:]
//            output['id'] = it.id
//            output['ownerID'] = it.owner.id
//            output['ownerName'] = it.owner.name
//            output['parameters'] = it.parameters
//            return output
//        }
//        grails.converters.JSON.registerObjectMarshaller(Parameter){
//            def output = [:]
//            output['id'] = it.id
//            output['name']= it.name
//            output['initiative'] = it.initiative.id
//            return output
//        }

//        grails.converters.JSON.registerObjectMarshaller(Initiative){
//            def output = [:]
//            output['id'] = it.id
//            return output
//        }
        def userRole =new Authority(authority:"ROLE_USER").save(true)
        def initUser = new User(username:"user",password: "pwd",name: "name",age: 19,email: "test@gmail.com").save(true)
        UserAuthority.create(initUser,userRole)

    }
    def destroy = {
    }
}
