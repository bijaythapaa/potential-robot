package com.bijay

/*
to use filter in Grails 3, add this dependency in build.gradle
[sourcecode language=”groovy”]
compile ‘org.grails:grails-plugin-filters:3.0.12’
[/sourcecode]
* */

class LameSecurityInterceptor {

    /* using the Filter */
//    def filters = {
//        secureActions(controller: 'post', action: '(addPost|deletePost)') {
//            before = {
//                if (params.imporsonatedId) {
//                    session.user = User.findByLoginId(params.imporsonatedId)
//                }
//                if (!session.user) {
//                    redirect(controller: 'login', action: 'form')
//                    return false
//                }
//            }
//            after = {
//                model ->
//            }
//            afterView = {
//                log.debug("Finished running ${controllerName} - ${actionName}")
//            }
//        }
//    }

    LameSecurityInterceptor() {
//        match(controller: "post", action: "*")
        match(controller: "post", action: "(addPost|deletePost)")
//        .except(action: 'home')
    }

    boolean before() {
        if (!session.user) {
            redirect(controller: 'login', action: 'form')
            return false
        }
        true
    }

    boolean after() { true }

    void afterView() {
        log.debug("Finished running ${controllerName} - ${actionName}")
        // no-op
    }
}
