package com.example.jetpackcompose.messaging.model

class Contact(val name: String) {


    companion object ContactFactory {
        fun makeContacts(): List<Contact> {
            return listOf(
                Contact("Joe"),
                Contact("Ellie"),
                Contact("Anna"),
                Contact("Rachel"),
                Contact("Ross"),
                Contact("Mark"),
                Contact("Jake")
            )
        }
    }
}
