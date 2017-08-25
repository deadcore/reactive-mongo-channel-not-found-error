import com.google.inject.ImplementedBy

@ImplementedBy(classOf[ApplicationImpl])
trait Application {
  def start: Unit
}