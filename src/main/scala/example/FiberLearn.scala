package example

import zio.{Scope, ZIO, ZIOAppArgs, durationInt}

object FiberLearn extends zio.ZIOAppDefault {

  val bathTime = ZIO.succeed("Going to the bathroom")
  val boilingWater = ZIO.succeed("Boiling some water")
  val preparingCoffee = ZIO.succeed("Preparing the coffee")

  def printThread = s"[${Thread.currentThread().getName}]"

  def sequentialWakeUpRoutine(): ZIO[Any, Nothing, Unit] = for {
    _ <- bathTime.debug(printThread)
    _ <- boilingWater.debug(printThread)
    _ <- preparingCoffee.debug(printThread)

  } yield ()

  def concurrentBathroomTimeAndBoilingWater(): ZIO[Any, Nothing, Unit] = for {
    _ <- bathTime.debug(printThread).fork
    _ <- boilingWater.debug(printThread)
  } yield ()

  def concurrentWakeUpRoutine(): ZIO[Any, Nothing, Unit] = for {
    bathFiber <- bathTime.debug(printThread).fork
    boilingFiber <- boilingWater.debug(printThread).fork
    zippedFiber = bathFiber.zip(boilingFiber)
    result <- zippedFiber.join.debug(printThread)
    _ <- ZIO.succeed(s"$result...done").debug(printThread) zipRight preparingCoffee.debug(printThread)
  } yield ()

  def test() = for {
    _ <- ZIO.unit
    _ <- ZIO.logInfo("hi")
    _ <- ZIO.attempt(
      (for {
        _ <- ZIO.logInfo(printThread)
        _ <- ZIO.sleep(4.seconds)
        _ <- ZIO.logInfo("attempt done")
      } yield ()).exitCode
    ) // why not run?

    _ <- ZIO.blocking(
      (for {
        _ <- ZIO.logInfo(printThread)
        _ <- ZIO.sleep(4.seconds)
        _ <- ZIO.logInfo("blocking done")
      } yield ()).exitCode
    )
  } yield ()

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = test().exitCode
}
