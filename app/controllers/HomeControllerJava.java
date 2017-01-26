/*
package controllers;

import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;

*/
/**
 * This controller shows example of mixing Java & Scala controllers within application.
 *//*

public class HomeControllerJava  extends Controller {

    private FormFactory formFactory;

    @Inject
    public HomeControllerJava(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    */
/**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     *//*



    */
/**
     * This result directly redirect to application home.
     *//*

    public Result GO_HOME = Results.redirect(
            routes.HomeController.list(0, 1, "")
    );

    */
/**
     * Handle default path requests, redirect to computers list
     *//*


    public Result index() {
        return ok("Got request " + request() + "!");
    }
}
*/
