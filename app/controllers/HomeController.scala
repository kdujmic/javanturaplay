package controllers

import javax.inject._

import models.{Company, Speaker}
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import views.html

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  /**
    * This result directly redirect to the application home.
    */
  val Home = Redirect(routes.HomeController.list(0, 2, ""))

  /**
    * Describe the speaker form (used in both edit and create screens).
    */
  val speakerForm = Form(
    mapping(
      "id" -> ignored(None:Option[Long]),
      "name" -> nonEmptyText,
      "session" -> nonEmptyText,
      "registrated" -> optional(date("yyyy-MM-dd")),
      "company" -> optional(longNumber)
    )(Speaker.apply)(Speaker.unapply)
  )

  // -- Actions

  /**
    * Handle default path requests, redirect to speakers list
    */
  def index = Action { Home }

  /**
    * Display the paginated list of speakers.
    *
    * @param page Current page number (starts from 0)
    * @param orderBy Column to be sorted
    * @param filter Filter applied on speaker names
    */
  def list(page: Int, orderBy: Int, filter: String) = Action { implicit request =>
    Ok(html.list(
      Speaker.list(page = page, orderBy = orderBy, filter = ("%"+filter+"%")),
      orderBy, filter
    ))
  }

  /**
    * Display the 'edit form' of a existing Speaker.
    *
    * @param id Id of the speaker to edit
    */
  def edit(id: Long) = Action {
    Speaker.findById(id).map { speaker =>
      Ok(html.editForm(id, speakerForm.fill(speaker), Company.options))
    }.getOrElse(NotFound)
  }

  /**
    * Handle the 'edit form' submission
    *
    * @param id Id of the speaker to edit
    */
  def update(id: Long) = Action { implicit request =>
    speakerForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.editForm(id, formWithErrors, Company.options)),
      speaker => {
        Speaker.update(id, speaker)
        Home.flashing("success" -> "Speaker %s has been updated".format(speaker.name))
      }
    )
  }

  /**
    * Display the 'new speaker form'.
    */
  def create = Action {
    Ok(html.createForm(speakerForm, Company.options))
  }

  /**
    * Handle the 'new speaker form' submission.
    */
  def save = Action { implicit request =>
    speakerForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.createForm(formWithErrors, Company.options)),
      speaker => {
        Speaker.insert(speaker)
        Home.flashing("success" -> "Speaker %s has been created".format(speaker.name))
      }
    )
  }

  /**
    * Handle speaker deletion.
    */
  def delete(id: Long) = Action {
    Speaker.delete(id)
    Home.flashing("success" -> "Speaker has been deleted")
  }



}
