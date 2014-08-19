package com.cybozu.labs.langdetect.profiles

object RegexMatcher {
  /**
   * URL matching regex of '@gruber' from https://mathiasbynens.be/demo/url-regex
   */
  val url = """\b(([\w-]+://?|www[.])[^\s()<>]+(?:\([\w\d]+\)|([^\p{Punct}\s]|/)))""".r

  /**
   *  E-Mail matching regex
   */
  val email = """[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\.[a-zA-Z]{2,4}""".r
}
