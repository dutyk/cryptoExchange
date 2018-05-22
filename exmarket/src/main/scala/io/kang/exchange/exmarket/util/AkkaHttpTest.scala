package io.kang.exchange.exmarket.util

import akka.actor.{ActorSystem, Props}
import io.kang.exchange.exmarket.exchange.zb.ZbExchange

object AkkaHttpTest {
  def main(args: Array[String]) = {
    val system = ActorSystem("exMarket")
    val zbExchangeActor = system.actorOf(ZbExchange.props("http://api.bitkk.com/data/v1/markets"), "zbExchange")
  }

}
