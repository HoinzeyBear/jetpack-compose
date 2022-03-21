package com.example.jetpackcompose.messaging.model

import com.example.jetpackcompose.R
import java.util.*

object MessageFactory {

    fun makeMessages(): List<Message> {
        return listOf(
            Message(
                "0",
                MessageDirection.SENT,
                Calendar.getInstance().also {
                    it.add(Calendar.DAY_OF_YEAR, -5)
                    it.set(Calendar.HOUR_OF_DAY, 5)
                },
                "Joe Birch",
                "Hey!"
            ),
            Message(
                "1",
                MessageDirection.RECEIVED,
                Calendar.getInstance().also {
                    it.add(Calendar.DAY_OF_YEAR, -5)
                    it.set(Calendar.HOUR_OF_DAY, 5)
                },
                "Joe Birch",
                "Hey!"
            ),
            Message(
                "2",
                MessageDirection.RECEIVED,
                Calendar.getInstance().also {
                    it.add(Calendar.DAY_OF_YEAR, -4)
                    it.set(Calendar.HOUR_OF_DAY, 4)
                },
                "Joe Birch",
                "How is Roxy? � "
            ),
            Message(
                "4",
                MessageDirection.SENT,
                Calendar.getInstance().also {
                    it.add(Calendar.DAY_OF_YEAR, -2)
                },
                "Joe Birch",
                "She is doing great!"
            ),
            Message(
                "5",
                MessageDirection.SENT,
                Calendar.getInstance().also {
                    it.add(Calendar.DAY_OF_YEAR, -2)
                },
                "Joe Birch",
                image = R.drawable.roxy
            )
        )
    }
}