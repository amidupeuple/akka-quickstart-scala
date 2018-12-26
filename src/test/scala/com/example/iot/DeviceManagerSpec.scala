package com.example.iot

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, Matchers, WordSpecLike}

class DeviceManagerSpec(_system: ActorSystem) extends TestKit(_system) with Matchers with WordSpecLike with BeforeAndAfterAll{
  def this() = this(ActorSystem("TestSpec"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "be able to create group" in {
    val probe = TestProbe()
    val devManagerActor = system.actorOf(Props[DeviceManager], "device-manager")

    devManagerActor.tell(DeviceManager.RequestTrackDevice("group1", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
  }
}
