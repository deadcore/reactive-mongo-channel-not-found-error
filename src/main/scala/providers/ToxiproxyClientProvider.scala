package providers

import com.google.inject.Provider
import eu.rekawek.toxiproxy.ToxiproxyClient

class ToxiproxyClientProvider extends Provider[ToxiproxyClient] {
  override def get() = new ToxiproxyClient("localhost", 8474)
}
