package com.cybozu.labs.langdetect.profiles

object RegexMatcher {
  /**
   * URL matching regex from http://daringfireball.net/2010/07/improved_regex_for_matching_urls
   */
  val url = """(?i)\b((?:[a-z][\w-]+:(?:\/{1,3}|[a-z0-9%])|www\d{0,3}[.]|[a-z0-9.\-]+[.][a-z]{2,4}\/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'".,<>?«»“”‘’]))""".r

  /**
   *  E-Mail matching regex
   */
  val email = """[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\.[a-zA-Z]{2,4}""".r
}
