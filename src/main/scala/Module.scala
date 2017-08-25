import com.google.inject.{AbstractModule, Scopes}
import eu.rekawek.toxiproxy.ToxiproxyClient
import mongo.ReactiveMongoApi
import net.codingwell.scalaguice.ScalaModule
import providers.{ReactiveMongoApiProvider, ToxiproxyClientProvider}

import scala.concurrent.ExecutionContext

object Module extends AbstractModule with ScalaModule {

  def configure(): Unit = {
    bind[ExecutionContext].toInstance(ExecutionContext.global)

    bind[ReactiveMongoApi].toProvider[ReactiveMongoApiProvider].in(Scopes.SINGLETON)
    bind[ToxiproxyClient].toProvider[ToxiproxyClientProvider].in(Scopes.SINGLETON)
  }

}