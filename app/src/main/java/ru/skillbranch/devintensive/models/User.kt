package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = null,
    val isOnline: Boolean = false
) {

    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String) : this(id, "John", "Doe")

    init {
        println(
            "It's Alive!!! \n ${if (lastName === "Doe") "His name is $firstName $lastName." else "And his name is $firstName $lastName!!!"}\n"
        )
    }

    companion object Factory {
        private var lastId: Int = -1
        fun makeUser(fullname: String?): User {
            if (fullname.isNullOrEmpty() || fullname === " ") {
                return User(id = "$lastId", firstName = null, lastName = null)

            } else if (!fullname.contains(" ")) {
                return User(id = "$lastId", firstName = fullname, lastName = null)

            } else {
                lastId++
                val (firstName, lastName) = Utils.parseFullName(fullname)
                return User(id = "$lastId", firstName = firstName, lastName = lastName)
            }
        }
    }
}