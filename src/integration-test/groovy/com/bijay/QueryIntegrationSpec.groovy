package com.bijay


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class QueryIntegrationSpec extends Specification {

    void "Simple property comparision"() {
        when: "Users are selected by the simple password match"
        def users = User.where {
            password == "testing"
        }.list(sort: "loginId")

        then: "The users with that password are returned"
        users*.loginId == ["frankie"]
    }

    void "Multiple criteria"() {
        when: "a user is selected by multiple criteria"
        def users = User.where {
            loginId == "frankie" || password == "crikey"
        }.list(sort: "loginId")

        then: "The matching loginIds are returned"
        users*.loginId == ['dillion', 'frankie', 'sara']
    }

    void "Query on association"() {
        when: "The 'following' collection is queried"
        def users = User.where {
            following.loginId == 'sara'
        }.list(sort: "loginId")

        then: "A list of followers of the given user is returned."
        users*.loginId == ['phil']
    }

    void "Query against a range value"() {
        given: "The current date and time"
        def now = new Date()

        when: "The 'dateCreated' property is required"
        def users = User.where {
            dateCreated in (now - 1)..now
        }.list(sort: loginId, order: "desc")

        then: "The users created within the specified date range are returned"
        users*.loginId == ["phil", "peter", "glen", "frankie", "chuck_norris", "admin"]
    }

    void "Retrieve a single instance"() {
        when: "A specific user is queried with get()"
        def user = User.where {
            loginId == "phil"
        }.get()

        then: "A single instance is returned"
        user.password == "thomas"
    }

//    def setup() {
//    }
//
//    def cleanup() {
//    }
//
//    void "test something"() {
//        expect:"fix me"
//            true == false
//    }
}
