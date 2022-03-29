package example

import zio.clock._
import zio.clock.Clock
import zio.{ExitCode, Fiber, IO, RIO, Schedule, Task, UIO, URIO, ZIO}
import zio.console._
import zio.duration.durationInt

import java.io.IOException
import java.util.Random
import scala.annotation.tailrec
import scala.util.{Random, Try}

case class User(name: String, teamId: String)

case class Team(name: String)

object Hello extends zio.App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = x.exitCode

  def makeRequest: Task[Int] = Task.effect {
    val x = scala.util.Random.nextInt(10)
    println(x)
    x
  }

  val x = {
    for {
      name <- makeRequest.repeat(
        Schedule.recurUntil[Int](y => Try(y > 7).getOrElse(false))
          .zipLeft(Schedule.exponential(1.seconds) && Schedule.recurs(3))
      )
      _ <- putStrLn(s"the word you inputted is: $name")
    } yield name
  }

  val myAppLogic: ZIO[Console, IOException, Unit] =
    for {
      _ <- putStrLn("Hello! What is your name?")
      name <- getStrLn
      _ <- putStrLn(s"Hello, ${name}, welcome to ZIO!")
    } yield ()

}



//trait UserID
//trait UserProfile
//
//object Database {
//  trait Service {
//    def lookup(id: UserID): Task[UserProfile]
//    def update(id: UserID, profile: UserProfile): Task[Unit]
//  }
//}
//trait Database {
//  def database: Database.Service
//}
//
//object db {
//  def lookup(id: UserID): RIO[Database, UserProfile] =
//    ZIO.accessM(_.database.lookup(id))
//
//  def update(id: UserID, profile: UserProfile): RIO[Database, Unit] =
//    ZIO.accessM(_.database.update(id, profile))
//}
//
//sealed trait Console[+A]
//final case class Return[A](value: () => A) extends Console[A]
//final case class PrintLine[A](line: String, rest: Console[A]) extends Console[A]
//final case class ReadLine[A](rest: String => Console[A]) extends Console[A]

//object X {

//def show(): Unit = {
//
//  val x: UIO[String] = IO.succeed("hello")
//  val xx = IO.succeed(println("hello world"))
//  val y: IO[String, Nothing] = IO.fail("hello")
//  val z: ZIO[Any, Exception, Nothing] = y.mapError(msg => new Exception(msg))
//
//  val zoption: IO[Option[Nothing], Int] = ZIO.fromOption(Some(2))
//  val xz: IO[String, Int] = zoption.orElseFail("It was not there")
//
//  val maybeId: IO[Option[Nothing], String] = ZIO.fromOption(Some("abc123"))
//  def getUser(userId: String): IO[Throwable, Option[User]] = ???
//  def getTeam(teamId: String): IO[Throwable, Team] = ???
//
//
//  val result: IO[Throwable, Option[(User, Team)]] = (for {
//  id   <- maybeId
//  user <- getUser(id).some
//  team <- getTeam(user.teamId).asSomeError
//  } yield (user, team)).optional
//
//  def fib(n: Long): UIO[Long] =
//  if (n <= 1) UIO.succeed(n)
//  else fib(n - 1).zipWith(fib(n - 2))(_ + _)
//
//  val fib100Fiber: UIO[Fiber[Nothing, Long]] = fib(100).fork
//  }


//  val example1: Console[Unit] =
//    PrintLine("Hello, what is your name?",
//      ReadLine(name =>
//        PrintLine(s"Good to meet you, ${name}", Return(() => ())))
//    )
//
//  /**
//   * can translate the model into procedural effects quite simply using an interpreter,
//   * which recurses on the data structure,
//   * translating every instruction into the side-effect that it describes:
//   */
//  @tailrec
//  def interpret[A](program: Console[A]): A = program match {
//    case Return(value) =>
//      value()
//    case PrintLine(line, next) =>
//      println(line)
//      interpret(next)
//    case ReadLine(next) =>
//      interpret(next(scala.io.StdIn.readLine()))
//  }
//
//  def succeed[A](a: => A): Console[A] = Return(() => a)
//  def printLine(line: String): Console[Unit] =
//    PrintLine(line, succeed(()))
//  val readLine: Console[String] =
//    ReadLine(line => succeed(line))
//
//  implicit class ConsoleSyntax[+A](self: Console[A]) {
//    def map[B](f: A => B): Console[B] =
//      flatMap(a => succeed(f(a)))
//
//    def flatMap[B](f: A => Console[B]): Console[B] =
//      self match {
//        case Return(value) => f(value())
//        case PrintLine(line, next) =>
//          PrintLine(line, next.flatMap(f))
//        case ReadLine(next) =>
//          ReadLine(line => next(line).flatMap(f))
//      }
//  }
//}
