//package java;
//
///*
// * Copyright (C) 2009-2016 Lightbend Inc. <https://www.lightbend.com>
// */
//
////#test-controller-test
//
//import org.junit.Test;
//import play.Application;
//import play.inject.guice.GuiceApplicationBuilder;
//import play.mvc.Result;
//import play.test.WithApplication;
//import play.twirl.api.Content;
//
//import static controllers.routes.HomeController;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static play.mvc.Http.Status.OK;
//import static play.test.Helpers.contentAsString;
//
//public class ApplicationTest extends WithApplication {
//
//    @Override
//    protected Application provideApplication() {
//        return new GuiceApplicationBuilder()
//                .configure("play.http.router", "javaguide.tests.Routes")
//                .build();
//    }
//
//    @Test
//    public void testIndex() {
//        Result result = new HomeController().index();
//        assertEquals(OK, result.status());
//        assertEquals("text/html", result.contentType().get());
//        assertEquals("utf-8", result.charset().get());
//        assertTrue(contentAsString(result).contains("Welcome"));
//    }
//
//    //###replace: }
////#test-controller-test
//
//    //#test-controller-routes
//    @Test
//    public void testCallIndex() {
//        Result result = route(
//                //###replace:     controllers.routes.HomeController.index(),
//                javaguide.tests.controllers.routes.HomeController.index()
//        );
//        assertEquals(OK, result.status());
//    }
//    //#test-controller-routes
//
//    //#test-template
//    @Test
//    public void renderTemplate() {
//        //###replace:     Content html = views.html.index.render("Welcome to Play!");
//        Content html = javaguide.tests.html.index.render("Welcome to Play!");
//        assertEquals("text/html", html.contentType());
//        assertTrue(contentAsString(html).contains("Welcome to Play!"));
//    }
//    //#test-template
//
//}
