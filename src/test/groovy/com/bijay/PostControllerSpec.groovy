package com.bijay

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */

// Imports controller test artifact
@TestFor(PostController)
// Adds mock save() and find() methods to User
@Mock([User, Post, LameSecurityInterceptor])
@TestMixin(GrailsUnitTestMixin)
class PostControllerSpec extends Specification {

    def "Get a user timeline given their id"() {
        given: "A user with post in the db"
        User chuck = new User(loginId: "chuck_norris", password: "password")
        chuck.addToPosts(new Post(content: "A first post"))
        chuck.addToPosts(new Post(content: "A second post"))
        chuck.save(failOnError: true)

        // params property introduced by @TestFor
        and: "A loginId parameter"
        params.id = chuck.loginId

        // controller property introduced by @TestFor
        when: "the timeline is invoked"
        def model = controller.timeline()

        then: "the user is in the returned model"
        model.user.loginId == "chuck_norris"
        model.user.posts.size() == 2
    }

    def "Check that non-existent users are handled wih an error"() {
        given: "the id of non-existent user"
        params.id = "invalid-id"

        when: "the timeline is invoked"
        controller.timeline()

        then: "a 404 is sent to the browser"
        response.status == 404
    }

//    def "Adding the valid new post to the timeline"() {
//        given: "A user with posts in the db"
//        User chuck = new User(loginId: "chuck_norris", password: "password").save(failOnError: true)
//
//        and: "A loginId parameter"
//        params.id = chuck.loginId
//
//        and: "Some content for the post"
//        params.content = "Chuck Norris can unit test entire applications with a single assert."
//
//        when: "addPost is invoked"
//        def model = controller.addPost()
//
//        then: "our flash message and redirect confirms the success"
//        flash.message == "Successfully created the post"
//        response.redirectedUrl == "/post/timeline/${chuck.loginId}"
//        Post.countByUser(chuck) == 1
//    }

    // test after adding service layer, using Spock mock
    def "Adding the valid new post to the timeline"() {
        given: "a mock post service"
        def mockPostService = Mock(PostService)
        1 * mockPostService.createPost(_, _) >> new Post(content: "Mock Post")
        controller.postService = mockPostService

        when: "controller is invoked"
        def result = controller.addPost("joe_cool", "Posting up a storm")

        then: "redirected to timeline, flash message tells us all is well"
        flash.message ==~ /Added new Post: Mock.*/
        response.redirectedUrl == "/users/joe_cool"

        // the test, without the custom url mapping
        // response.redirectedUrl == '/post/timeline/joe_cool'
    }

//    def "Check if the invalid User Id provided is handled with an error"() {
//        given: "A user with the posts in the db"
//        User chuck = new User(loginId: "chuck_norris", password: "password").save(failOnError: true)
//
//        and: "the invalid User Id"
//        params.id = "invalid-id"
//
//        and: "Some content for the post"
//        params.content = "Chuck Norris can unit test entire applications with a single assert."
//
//        when: "addPost is invoked"
//        def model = controller.addPost()
//
//        then: "redirect with the error flash message"
//        flash.message == "Invalid User Id"
//        response.redirectedUrl == "/post/timeline/${chuck.loginId}"
//        Post.countByUser(chuck) == 0
//    }

    def "Check if the invalid User Id provided is handled with an error"() {
        given: "A user with the posts in the db"
        User chuck = new User(loginId: "chuck_norris", password: "password").save(failOnError: true)

        and: "A post service that throws an exception with the given data"
        def errorMsg = "Invalid or Empty Post"
        def mockPostService = Mock(PostService)
        controller.postService = mockPostService
        1 * mockPostService.createPost("invalid-id", "Chuck Norris can unit test") >> {
            throw new PostException(message: errorMsg)
        }

//        and: "the invalid User Id"
//        params.id = "invalid-id"
//
//        and: "Some content for the post"
//        params.content = "Chuck Norris can unit test entire applications with a single assert."

        when: "addPost is invoked"
        def model = controller.addPost("invalid-id", "Chuck Norris can unit test")

        then: "our flash message and redirect confirms the success"
        flash.message == errorMsg
        response.redirectedUrl == "/post/timeline/${chuck.loginId}"
        Post.countByUser(chuck) == 0
    }

//    def "Check if the Post with null content is handled with an error"() {
//        given: "A user with the posts in the db"
//        User chuck = new User(loginId: "chuck_norris", password: "password").save(failOnError: true)
//
//        and: "A loginId parameter"
//        params.id = chuck.loginId
//
//        and: "the content of a post is null"
//        params.content = ""
//
//        when: "addPost is invoked"
//        def model = controller.addPost()
//
//        then: "redirect confirms with the error flash message"
//        flash.message == "Invalid or empty post"
//        response.redirectedUrl == "/post/timeline/${chuck.loginId}"
//        Post.countByUser(chuck) == 0
//}

    def "Check if the Post with null content is handled with an error"() {
        given: "A user with the posts in the db"
        User chuck = new User(loginId: "chuck_norris", password: "password").save(failOnError: true)

        and: "A post service that throws an exception with the given data"
        def errorMsg = "Invalid or Empty Post"
        def mockPostService = Mock(PostService)
        controller.postService = mockPostService
        1 * mockPostService.createPost(chuck.id, null) >> { throw new PostException(message: errorMsg) }

//        and: "A loginId parameter"
//        params.id = chuck.id
//
//        and: "A content parameter"
//        params.content = null

        when: "addPost is invoked"
        def model = controller.addPost(chuck.loginId, null)

        then: "our flash message and redirect confirms the success"
        flash.message == errorMsg
        response.redirectedUrl == "/post/timeline/${chuck.loginId}"
        Post.countByUser(chuck)
    }

    @Unroll
    def "Testing id of #suppliedId redirects to #expectedUrl"() {
        given:
        params.id = suppliedId

        when: "controller is invoked"
        controller.home()

        then:
        response.redirectedUrl == expectedUrl

        where:
        suppliedId | expectedUrl
        'joe_cool' | '/post/timeline/joe_cool'
        null       | '/post/timeline/chuck_norris'
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