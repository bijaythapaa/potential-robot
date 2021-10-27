package com.bijay

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([User, Profile])
@TestMixin(GrailsUnitTestMixin)
class UserControllerSpec extends Specification {

    def "registering a user with known good parameters"() {
        given: "a set of user parameters"
        params.with {
            loginId = "lok_dev"
            password = "winning"
            homepage = "http://bijaythapa.com.np/lok"
        }

        and: "a set of profile parameters"
        params['profile.fullName'] = "Lok Dev"
        params['profile.email'] = "info@bijaythapa.com.np"
        params['profile.homepage'] = "http://bijaythapa.com.np/lok"

        when: "the user is registered"
        request.method = "POST"
        controller.register()

        then: "the user is created and browser redirected"
        response.redirectedUrl == "/"
        User.count() == 1
        Profile.count() == 1
    }

    @Unroll
    def "registration command object for #loginId validate correctly"() {
        given: "a mock command object"
        def urc = mockCommandObject(UserRegistrationCommand)

        and: "a set of initial values from the spock test"
        urc.loginId = loginId
        urc.password = password
        urc.passwordRepeat = passwordRepeat
        urc.fullName = "Your Name Here"
        urc.email = "someone@nowhere.net"

        when: "the validator is invoked"
        def isValidRegistration = urc.validate()

        then: "the appropriate fields are flagged as errors"
        isValidRegistration == anticipatedValid
        urc.errors.getFieldError(fieldInError)?.code == errorCode

        where:
        loginId | password   | passwordRepeat | anticipatedValid | fieldInError     | errorCode
        "glen"  | "password" | "no-match"     | false            | "passwordRepeat" | "validator.invalid"
        "peter" | "password" | "password"     | true             | null             | null
        "a"     | "password" | "password"     | false            | "loginId"        | "size.tooSmall"

    }

    def "Invoking the new register action via a command object"() {
        given: "A configured command object"
        def urc = mockCommandObject(UserRegistrationCommand)
        urc.with {
            loginId = "dev_lok"
            fullName = "Lok Dev"
            email = "lok@hubbub.com.np"
            password = "password"
            passwordRepeat = "password"
        }

        and: "which has been validated"
        urc.validate()

        when: "the register action invoked"
        controller.register2(urc)

        then: "the user is registered and browser is redirected"
        !urc.hasErrors()
        response.redirectedUrl == "/"
        User.count() == 1
        Profile.count() == 1
    }


    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect: "fix me"
        true == false
    }
}