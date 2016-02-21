package rps

import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server._, Directives._
import akka.stream.ActorMaterializer


object Rps {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("rps-system")
    implicit val materializer = ActorMaterializer()
    implicit val ec = system.dispatcher

    val route =
      path("rps" / AChoice) { choice =>
        get {
          complete {
            val botChoice = nextBotChoice
            val result = gameResult(choice, botChoice)
            s"""|My choice is $botChoice
                |$result
            """.stripMargin
          }
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println("Server online at http://localhost:8080/\nPress RETURN to stop...")

    scala.io.StdIn.readLine()

    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ ⇒ system.terminate()) // and shutdown when done
  }

  import Rules._

  val options = beatenBy.keys

  def nextBotChoice = options.drop(scala.util.Random.nextInt(options.size)).head

  def gameResult(human: String, bot: String) = {
    if (bot == beatenBy(human)) "You have won"
    else if (human == beatenBy(bot)) "You have lost"
    else "It's a draw"
  }

  val AChoice = PathMatcher.valueMap2PathMatcher(options.map(a => a -> a).toMap)
}

object Rules {
  val beatenBy = Map(
    "rock" -> "scissors",
    "paper" -> "rock",
    "scissors" -> "paper"
  )
}


