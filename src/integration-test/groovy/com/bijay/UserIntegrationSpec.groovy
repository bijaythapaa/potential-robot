package com.bijay

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

@Integration
@Rollback
class UserIntegrationSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect: "fix me"
        true == false
    }

    def "Saving our first user to database"() {
        given: "A brand new user"
        def joe = new User(loginId: 'bijay', password: 'secret')

        when: "the user is saved"
        joe.save()

        then: "it saved successfully and can be found in database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId == joe.loginId
        User.get(joe.id).password == joe.password
    }

    def "Updating a saved user changes its properties"() {
        given: "An existing user"
        def existingUser = new User(loginId: 'bijay', password: 'secret')
        existingUser.save(failOnError: true)

        when: "A property is changed"
        def foundUser = User.get(existingUser.id)
        foundUser.password = "more-secret"
        foundUser.save(failOnError: true)

        then: "The change is reflected in database"
        User.get(existingUser.id).password == "more-secret"
    }

    def "Deleting an existing user removes it from database"() {
        given: "An existing user"
        def existingUser = new User(loginId: 'bijay', password: 'secret')
        existingUser.save(failOnError: true)

        when: "A property is deleted"
        def foundUser = User.get(existingUser.id)
        foundUser.delete(flush: true)

        then: "The user is removed from the database"
        !User.exists(foundUser.id)
    }

    def "Saving a user with invalid properties causes an error"() {
        given: "A user which fails several field validations"
        def user = new User(loginId: 'bijay', password: 'tiny')

        when: "The user is validated"
        // Grails checks whether or not the constraints have been satisfied
        // and provides errors object, which can be useful to see which fields failed.
        user.validate()

        then:
        user.hasErrors()

        "size.toosmall" == user.errors.getFieldError("password").code
        "tiny" == user.errors.getFieldError("password").rejectedValue
        !user.errors.getFieldError("loginId")
    }

    def "Recovering from a failed save by fixing invalid properties"() {
        given: "A user that has invalid properties"
        def invalidUser = new User(loginId: 'bijay', password: "tiny")
        assert invalidUser.save() == null
        assert invalidUser.hasErrors()
//        assert 4 * ( 2 + 3 ) - 5 == 14 : "test failed"

        when: "We fix the invalid properties"
        invalidUser.password = "tinypass"
        invalidUser.validate()

        then: "The user saves and validates fine"
        !invalidUser.hasErrors()
        invalidUser.save()
    }

    // simple test case for adding followers
    def "Ensure a user can follow other users"() {
        given: "A set of baseline users"
        def bijay = new User(loginId: "bijay", password: "secret").save()
        def amar = new User(loginId: "amar", password: "amarsecret").save()
        def lok = new User(loginId: "lok", password: "loksecret").save()

        when: "bijay can follow amar and lok, and lok follows amar"
        bijay.addToFollowing(amar)
        bijay.addToFollowing(lok)
        lok.addToFollowing(amar)

        then: "Followers count should match following people"
        2 == bijay.following.size()
        1 == lok.following.size()
    }
}
