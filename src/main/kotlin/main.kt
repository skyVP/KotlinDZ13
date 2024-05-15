package ru.netology

import kotlin.math.max

fun main() {
    println("--- Задача №1. Когда собеседник был онлайн")
    println(agoToText(60))
    println(agoToText(61))
    println(agoToText(1320))
    println(agoToText(3599))
    println(agoToText(3600))
    println(agoToText(18_000))
    println(agoToText(86_399))
    println(agoToText(86_400))
    println(agoToText(172_800))
    println(agoToText(1_000_000))
    println()

    println("--- Задача №2. Разная комиссия")
    println(printCommissionResult(cardType = "Мир", monthlyAmount = 300_000, transferAmount = 140_000))
    println(printCommissionResult(cardType = "Visa", monthlyAmount = 300_000, transferAmount = 140_000))
    println(printCommissionResult(cardType = "MasterCard", monthlyAmount = 50_000, transferAmount = 50_000))
    println(printCommissionResult(cardType = "MasterCard", monthlyAmount = 300_000, transferAmount = 140_000))
    println(printCommissionResult(cardType = "Мир", monthlyAmount = 550_000, transferAmount = 140_000))
    println(printCommissionResult(cardType = "Мир", dailyAmount = 100_000, transferAmount = 60_000))
}


// Задача №1. Когда собеседник был онлайн

fun agoToText(time: Int): String {
    return when {
        time in 0..60 -> "был(а) только что"
        time in 61..60 * 60 - 1 -> "был(а) " + minuteNounForm(time) + " назад"
        time in 60 * 60..24 * 60 * 60 - 1 -> "был(а) " + hoursNounForm(time) + " назад"
        time in 24 * 60 * 60..48 * 60 * 60 - 1 -> "был(а) вчера"
        time in 48 * 60 * 60..72 * 60 * 60 - 1 -> "был(а) позавчера"
        else -> "был(а) давно"
    }
}

fun minuteNounForm(time: Int): String {
    val minutes = time / 60
    val lastChar = minutes.toString()[minutes.toString().length - 1]
    return when {
        minutes == 1 || (minutes > 20 && lastChar == '1') -> "$minutes минуту"
        (minutes in 2..4) || (minutes > 21 && lastChar in '2'..'4') -> "$minutes минуты"
        else -> "$minutes минут"
    }
}

fun hoursNounForm(time: Int): String {
    val hours = time / 3600
    return when {
        hours % 20 == 1 -> "$hours час"
        hours in 5..20 -> "$hours часов"
        else -> "$hours часа"
    }
}

// Задача №2. Разная комиссия

fun calculateCommission(
    cardType: String = "Мир",
    dailyAmount: Int = 0,
    monthlyAmount: Int = 0,
    transferAmount: Int
): Int {
    val dailyLimit = 150_000
    val monthlyLimit = 600_000
    val freeMasterCardLimit = 75_000

    return if (dailyAmount + transferAmount <= dailyLimit && monthlyAmount + transferAmount <= monthlyLimit) {
        when {
            cardType == "Visa" -> max(35, (transferAmount * 0.0075).toInt())
            cardType == "MasterCard" && monthlyAmount > freeMasterCardLimit -> (transferAmount * 0.006 + 20).toInt()
            cardType == "MasterCard" && monthlyAmount <= freeMasterCardLimit && monthlyAmount + transferAmount > freeMasterCardLimit -> ((monthlyAmount + transferAmount - freeMasterCardLimit) * 0.006 + 20).toInt()
            else -> 0
        }
    } else -1
}

fun printCommissionResult(cardType: String, dailyAmount: Int = 0, monthlyAmount: Int = 0, transferAmount: Int): String {
    val commission = calculateCommission(cardType, dailyAmount, monthlyAmount, transferAmount)
    return if (commission >= 0) {
        "$cardType, за сегодня $dailyAmount, за месяц $monthlyAmount + перевод $transferAmount -> $commission"
    } else "Операция заблокирована."
}
