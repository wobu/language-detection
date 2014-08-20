package com.cybozu.labs.langdetect.profiles

import scala.io._

object LanguageProfile {
  private val languages = Seq("af", "ar", "bg", "bn", "cs", "da", "de", "el", "en", "es", "et", "fa", "fi", "fr", "gu", "he", "hi", "hr", "hu", "id", "it", "ja", "kn", "ko", "lt", "lv", "mk", "ml", "mr", "ne", "nl", "no", "pa", "pl", "pt", "ro", "ru", "sk", "sl", "so", "sq", "sv", "sw", "ta", "te", "th", "tl", "tr", "uk", "ur", "vi", "zh-cn", "zh-tw")
  private val shortTextLanguages = Seq("ar", "bg", "bn", "ca", "cs", "da", "de", "el", "en", "es", "et", "fa", "fi", "fr", "gu", "he", "hi", "hr", "hu", "id", "it", "ja", "ko", "lt", "lv", "mk", "ml", "nl", "no", "pa", "pl", "pt", "ro", "ru", "si", "sq", "sv", "ta", "te", "th", "tl", "tr", "uk", "ur", "vi", "zh-cn", "zh-tw")

  import scala.collection.JavaConversions._

  lazy val profiles : java.util.Map[String, String] = {
    mapAsJavaMap(languages.map(lang => lang -> Source.fromInputStream(LanguageProfile.getClass.getResourceAsStream(lang))(Codec.UTF8).mkString).toMap)
  }

  lazy val shortTextProfiles : java.util.Map[String, String] = {
    mapAsJavaMap(shortTextLanguages.map(lang => lang -> Source.fromInputStream(LanguageProfile.getClass.getResourceAsStream("short/" + lang))(Codec.UTF8).mkString).toMap)
  }
}
