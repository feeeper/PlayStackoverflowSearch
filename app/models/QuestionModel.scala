package models

class QuestionModel(t: String, l: String, a: Boolean, on: String, ol: String) {
  var title: String = t
  var link: String = l
  var is_answered: Boolean = a
  var owner_name: String = on
  var owner_link: String = ol
}