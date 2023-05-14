package example

import zio._
import zio.stream._

object ZStreamLearn extends ZIOAppDefault {

  /**
   * A ZStream represents the source of data in your work flow.
   * A ZSink represents the terminating endpoint of data in your workflow.
   * We should mention that ZIO uses pull-based streams, meaning that elements are processed by being “pulled through the stream” by the sink. In push-based systems, elements would be “pushed through the stream” to the sink.
   */

  /**
   * ZSink[R, E, I, L, Z]. R, and E are as described above. It will consume elements of type I, and produce a value of type Z along with any elements of type L that may be left over.
   * Any: environment type
   * Nothing: error type
   * Int: the type of elements that will be processed
   * Nothing: the leftover values, describes values that have not been processed by the ZSink. Nothing here means you will consume all the entire stream
   * Int: the value returned from the process
   */
  val sum: ZSink[Any, Nothing, Int, Nothing, Int] = ZSink.sum[Int]
  val take5: ZSink[Any, Nothing, Int, Int, Chunk[Int]] = ZSink.take[Int](5)
  val take5Map: ZSink[Any, Nothing, Int, Int, Chunk[String]] = take5.map(chunk => chunk.map(_.toString))
  val take5Leftovers: ZSink[Any, Nothing, Int, Nothing, (Chunk[String], Chunk[Int])] = take5Map.collectLeftover
  val take5NoLeftovers: ZSink[Any, Nothing, Int, Nothing, Chunk[String]] = take5Map.ignoreLeftover
  val take5Strings: ZSink[Any, Nothing, String, Int, Chunk[String]] = take5Map.contramap[String](_.toInt)

  val intStream: ZStream[Any, Nothing, Int] = ZStream(1, 2, 3, 4, 5, 6, 7, 8)
  val stringStream: ZStream[Any, Nothing, String] = ZStream("1", "2", "3", "4", "5", "6", "7", "8")
  val zio: ZIO[Any, Nothing, Int] = intStream.run(sum)
  val zioTake5: ZIO[Any, Nothing, Chunk[Int]] = intStream.run(take5)
  val zioTake5Leftovers: ZIO[Any, Nothing, (Chunk[String], Chunk[Int])] = intStream.run(take5Leftovers)
  val zioTake5Strings: ZIO[Any, Nothing, Chunk[String]] = stringStream.run(take5Strings)

  val businessLogic: ZPipeline[Any, Nothing, String, Int] = ZPipeline.map[String, Int](_.toInt)
  val filterLogic: ZPipeline[Any, Nothing, Int, Int] = ZPipeline.filter[Int](_ > 3)
  val appLogic: ZPipeline[Any, Nothing, String, Int] = businessLogic >>> filterLogic
  val convertedStream: ZStream[Any, Nothing, Int] = stringStream.via(appLogic)
  val zioOnConvertedStream: ZIO[Any, Nothing, Int] = convertedStream.run(sum)

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = zioTake5Strings.debug
}