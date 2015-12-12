package controllers

import org.json4s._
import org.json4s.jackson.Serialization
import play.api.mvc._
import scalaj.http._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Here you can search some staff from StackOverflow."))
  }

  def search(query: String = "") = Action {
    val url = "https://api.stackexchange.com/2.2/search?order=desc&sort=activity&site=stackoverflow&intitle=" +
      java.net.URLEncoder.encode(query, "UTF-8")

    val response: HttpResponse[String] = Http(url).asString
    // Требуется для корректной сериализации JSON.
    implicit val formats = Serialization.formats(NoTypeHints)

    val output = org.json4s.jackson.Serialization.read[Questions](response.body)
    Ok(views.html.search(output.items.map(q =>
      new models.QuestionModel(q.title, q.link, q.is_answered, q.owner.display_name, q.owner.link))))
  }
}

case class Questions(items: List[Question])
case class Question(answer_count: Int,
                    creation_date: String,
                    is_answered: Boolean,
                    last_activity_date: Long,
                    link: String,
                    owner: User,
                    question_id: Long,
                    score: Integer,
                    tags: List[String],
                    title: String,
                    view_count: Integer)
case class User (display_name: String,
                 link: String,
                 profile_image: String,
                 reputation: Integer,
                 user_id: Long,
                 user_type: String)