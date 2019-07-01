package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullname: String?): Pair<String?, String?> {
        val parts: List<String>? = fullname?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payLoad: String, divider: String = " "): String {

        val nickNameLowerCase = payLoad.toLowerCase()

        return nickNameLowerCase.replace(Regex("[абвгдеёжзийклмнопрстуфхцчшщъыьэюя ]")) {
              when (it.value) {
                  "а" -> "a"
                  "б" -> "b"
                  "в" -> "v"
                  "г" -> "g"
                  "д" -> "d"
                  "е" -> "e"
                  "ё" -> "e"
                  "ж" -> "zh"
                  "з" -> "z"
                  "и" -> "i"
                  "й" -> "i"
                  "к" -> "k"
                  "л" -> "l"
                  "м" -> "m"
                  "н" -> "n"
                  "о" -> "o"
                  "п" -> "p"
                  "р" -> "r"
                  "с" -> "s"
                  "т" -> "t"
                  "у" -> "u"
                  "ф" -> "f"
                  "х" -> "h"
                  "ц" -> "c"
                  "ч" -> "ch"
                  "ш" -> "sh"
                  "щ" -> "sh'"
                  "ъ" -> ""
                  "ы" -> "i"
                  "ь" -> ""
                  "э" -> "e"
                  "ю" -> "yu"
                  "я" -> "ya"
                  " " -> "_"
                  else -> it.value
              }
          }
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName.isNullOrEmpty() && lastName.isNullOrEmpty() || firstName === " " && lastName === " ") {
            return null
        } else if (firstName === " " && lastName.isNullOrEmpty() || firstName.isNullOrEmpty() && lastName === " ") {
            return null
        } else if (firstName!!.isNotEmpty() && lastName.isNullOrEmpty()) {
            val character = firstName[0]
            if (character.isLowerCase()) {
                character.toUpperCase()
            }
            return character.toString()
        } else if (firstName.isNullOrEmpty() && lastName!!.isNotEmpty()) {
            val character = lastName[0]
            if (character.isLowerCase()) {
                character.toUpperCase()
            }
            return character.toString()
        } else if (firstName.isNotEmpty() && lastName!!.isNotEmpty()) {
            val characterFirstName = firstName[0]
            if (characterFirstName.isLowerCase()) {
                characterFirstName.toUpperCase()
            }
            val characterLastName = lastName[0]
            if (characterLastName.isLowerCase()) {
                characterLastName.toUpperCase()
            }
            val nickName = characterFirstName.toString() + characterLastName.toString()
            return nickName
        }
        return this.toString()
    }
}