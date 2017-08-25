import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import org.slf4j.LoggerFactory

object Bootstrap extends App {
  private val logger = LoggerFactory.getLogger(this.getClass)
  private val applicationName = "reactive-mongo-bug"

  logger.info(s"Starting up $applicationName")

  try {
    application.start
  } catch {
    case ex: Exception => logger.error(s"Error while starting up $applicationName", ex)
  }

  private def application: Application = {
    logger.info(s"Resolving instance of application")
    val application = injector.instance[Application]
    logger.info(s"Resolved application to: {}", application)
    application
  }

  private def injector: Injector = {
    logger.info(s"Preparing guice injection")
    val injector = Guice.createInjector(Module)
    logger.info(s"Guice injection completed successfully")
    injector
  }

}
