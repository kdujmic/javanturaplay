import models.Speaker.SpeakerService
import org.scalatestplus.play._

class ModelSpec extends PlaySpec with OneAppPerSuite {

  var speakerService: SpeakerService = app.injector.instanceOf(classOf[SpeakerService])

  import models._

  // -- Date helpers

  def dateIs(date: java.util.Date, str: String): Boolean = {
    new java.text.SimpleDateFormat("yyyy-MM-dd").format(date) == str
  }

  // --

  "Speaker model" should {

    "be retrieved by id" in {
      val speaker = speakerService.findById(1).get

      speaker.name must equal("Kresimir Dujmic")
      speaker.registrated.value must matchPattern {
        case date:java.util.Date if dateIs(date, "2017-01-01") =>
      }
    }

    "be listed along its sepeakers" in {

      val speakers = speakerService.list()

      speakers.total must equal(9)
      speakers.items must have length 9
    }

    "be updated if needed" in {

      speakerService.update(1, Speaker(name="Krešimir Dujmić",
        session="Java test session",
        registrated=None,
        companyId=Some(1)))

      val kresoBean = speakerService.findById(1).get

      kresoBean.name must equal("Krešimir Dujmić")
      kresoBean.registrated mustBe None
    }

  }

}