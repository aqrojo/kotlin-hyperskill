package machine

import java.util.Scanner

data class Supplies(val water: Int, val milk: Int, val coffee: Int, val cups: Int, val money: Int) {
    fun getMoney(): String {
        return "\$${money.toString()}"
    }

    fun print(): Unit {
        println("The coffee machine has:")
        println("$water of water")
        println("$milk of milk")
        println("$coffee of coffee beans")
        println("$cups of disposable cups")
        println("${getMoney()} of money")
    }
}

fun buy(scanner: Scanner, supplies: Supplies): Supplies {
    println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
    val currentAction = scanner.next()

    if (currentAction == "back") return supplies

    val selectedCoffee = when (currentAction.toInt()) {
        1 -> Supplies(250, 0, 16, 1, 4)
        2 -> Supplies(350, 75, 20, 1, 7)
        else -> Supplies(200, 100, 12, 1, 6)
    }

    val currentSupplies = Supplies(
            water = supplies.water - selectedCoffee.water,
            milk = supplies.milk - selectedCoffee.milk,
            coffee = supplies.coffee - selectedCoffee.coffee,
            cups = supplies.cups - selectedCoffee.cups,
            money = supplies.money + selectedCoffee.money
    )

    val insufficientResource = when {
        currentSupplies.water < 0 -> "water"
        currentSupplies.milk < 0 -> "milk"
        currentSupplies.coffee < 0 -> "coffee"
        currentSupplies.cups < 0 -> "cups"
        else -> ""
    }

    return if (insufficientResource.isNotEmpty()) {
        println("Sorry, not enough $insufficientResource!")
        supplies
    } else {
        currentSupplies
    }
}

fun fill(scanner: Scanner, supplies: Supplies): Supplies {
    println("Write how many ml of water do you want to add: ")
    val water = scanner.nextInt() + supplies.water

    println("Write how many ml of milk do you want to add: ")
    val milk = scanner.nextInt() + supplies.milk

    println("Write how many grams of coffee beans do you want to add: ")
    val coffee = scanner.nextInt() + supplies.coffee

    println("Write how many disposable cups of coffee do you want to add: ")
    val cups = scanner.nextInt() + supplies.cups

    return Supplies(water, milk, coffee, cups, supplies.money)

}

fun take(scanner: Scanner, supplies: Supplies): Supplies {
    println("I gave you ${supplies.getMoney()}")
    return supplies.copy(money = 0)
}

fun main() {
    val scanner = Scanner(System.`in`)
    var supplies = Supplies(400, 540, 120, 9, 550)

    while (scanner.hasNext()) {
        val currentAction = scanner.next()
        if (currentAction == "exit") return
        println("Write action (buy, fill, take, remaining, exit): ")
        supplies = when (currentAction) {
            "buy" -> buy(scanner, supplies)
            "fill" -> fill(scanner, supplies)
            "take" -> take(scanner, supplies)
            "remaining" -> {
                supplies.print()
                supplies
            }
            else -> supplies
        }
    }
}