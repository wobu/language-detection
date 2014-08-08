package com.cybozu.labs.langdetect

import org.scalatest.{Matchers, FlatSpec}

class DetectorFactoryTest extends FlatSpec with Matchers {
  "DetectorFactory" should "load bundled language profiles" in {
    // given
    val factory = new DetectorFactory

    // when
    factory.loadBundledProfiles()

    // then
    factory.getLangList.size() should be > 0
  }

  "DetectorFactory" should "load bundled short text language profiles" in {
    // given
    val factory = new DetectorFactory

    // when
    factory.loadBundledShortTextProfiles()

    // then
    factory.getLangList.size() should be > 0
  }
}
