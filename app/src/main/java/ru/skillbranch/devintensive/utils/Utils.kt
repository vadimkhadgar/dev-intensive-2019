package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")

        if (fullName.isNullOrBlank())
            return Pair(null, null)

        return Pair(parts?.getOrNull(0), parts?.getOrNull(1))
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
        if (firstName.isNullOrBlank() && lastName.isNullOrBlank())
            return null

        val fNameInitial = firstName?.first()
        val lNameInitial = lastName?.first()

        return (when {
            fNameInitial == null -> "$lNameInitial"
            lNameInitial == null -> "$fNameInitial"
            else -> "$fNameInitial$lNameInitial"
        }).toUpperCase()
    }
    }
