import javax.inject.Inject

import dao.UserDao
import eu.rekawek.toxiproxy.model.{Toxic, ToxicDirection}
import eu.rekawek.toxiproxy.{Proxy, ToxiproxyClient}
import model.User
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import scala.concurrent.duration.{Duration, DurationLong}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Random, Try}

class ApplicationImpl @Inject()(dao: UserDao, toxiproxy: ToxiproxyClient)(implicit executionContext: ExecutionContext) extends Application {

  private val logger = LoggerFactory.getLogger(this.getClass)

  def start: Unit = {

    val nodes: Seq[String] = Seq("rs0-0", "rs0-1", "rs0-2")

    sys.addShutdownHook {
      // Remove all the toxics
      clearToxics()
    }

    1 to 20 foreach { x =>
      test(Random.shuffle(nodes).take(1): _*)
    }

    logger.info("Finished :D")
  }

  private def test(targets: String*) = {
    // Remove all the toxics
    clearToxics()

    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()

    logger.info("Injecting downstream latency into {}", targets)
    targets.foreach { target =>
      logger.info("Disabling mongo instance {}", target)
      toxiproxy.getProxy(target).disable()
      countdown(15 seconds)
    }

    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()
    blockingCreateAndSaveUser()

    clearToxics()
  }

  def countdown(duration: Duration): Unit = {
    0 to duration.toSeconds.asInstanceOf[Int] foreach { elapsed =>
      logger.info("{} seconds to go", duration.toSeconds - elapsed)
      Thread.sleep(1 seconds)
    }
  }

  def clearToxics(): Unit = {
    logger.info("Removing all toxics")
    proxies.flatMap(toxics).foreach(_.remove())
    proxies.foreach(_.enable())
    logger.info("Finished clearing down all toxics")
  }

  def blockingCreateAndSaveUser(): Unit = {
    val user = newUser
    logger.info("Saving user: {}", user)
    val result = wait(dao.save(newUser))
    logger.info("Attempted to save user with result: {}", result)
  }

  def proxies: Seq[Proxy] = toxiproxy.getProxies.asScala

  def toxics(proxy: Proxy): Seq[Toxic] = proxy.toxics.getAll.asScala

  def wait[T](future: Future[T]): Try[T] = Await.ready(future, Duration.Inf).value.get

  def newUser = User(randomString(32))

  def randomString(length: Int): String = scala.util.Random.alphanumeric.take(length).mkString

  private implicit def durationToMilis(duration: Duration): Long = duration.toMillis

}