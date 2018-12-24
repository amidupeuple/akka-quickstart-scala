package com.example

import akka.actor.{Actor, ActorSystem, Props}

import scala.io.StdIn

class StartStopActor1 extends Actor {
  override def preStart(): Unit = {
    println("first started")
    context.actorOf(StartStopActor2.props, "second")
  }
  override def postStop(): Unit = println("first stopped")

  override def receive: Receive = {
    case "stop" ⇒ context.stop(self)
  }
}

object StartStopActor2 {
  def props: Props =
    Props(new StartStopActor2)
}

class StartStopActor2 extends Actor {
  override def preStart(): Unit = println("second started")
  override def postStop(): Unit = println("second stopped")

  // Actor.emptyBehavior is a useful placeholder when we don't
  // want to handle any messages in the actor.
  override def receive: Receive = Actor.emptyBehavior
}

object StartStopRunner extends App {
  val actorSystem = ActorSystem("testSystem")
  val actor1Ref = actorSystem.actorOf(Props[StartStopActor1], "actor1")
  actor1Ref ! "stop"
  try StdIn.readLine
  finally actorSystem.terminate()
}