package com.cybozu.labs.langdetect

import com.cybozu.labs.langdetect.profiles.RegexMatcher
import org.scalatest.{Matchers, FlatSpec}

class RegexMatcherTest extends FlatSpec with Matchers {
  "RegexMatcher.url" should "handle dangerous URL's without an ReDoS" in {
    val text = "http://ddos-link.com/[test.......................................]"

    val result = RegexMatcher.url.findAllIn(text)

    result should have size 1
  }
}
