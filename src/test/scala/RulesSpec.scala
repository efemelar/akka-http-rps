package rps


class RulesSpec extends org.specs2.mutable.Specification {
  import Rules._

  "Rock" >> {
    "beats scissors" >> {
      ("scissors" == beatenBy("rock")) === false
    }
  }
}
