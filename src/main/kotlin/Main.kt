import java.math.RoundingMode
import java.lang.IndexOutOfBoundsException

fun main(args: Array<String>) {

    val cinema = Cinema()

    // First get the set Information
    cinema.getSetInformation()

    // Display the menu for user
    cinema.displayMenu()

}


class Cinema{

    private val spaces = mutableListOf(
        mutableListOf("S","S","S","S","S","S","S","S","S"),
        mutableListOf("S","S","S","S","S","S","S","S","S"),
        mutableListOf("S","S","S","S","S","S","S","S","S"),

        mutableListOf("S","S","S","S","S","S","S","S","S"),
        mutableListOf("S","S","S","S","S","S","S","S","S"),
        mutableListOf("S","S","S","S","S","S","S","S","S"),

        mutableListOf("S","S","S","S","S","S","S","S","S"),
        mutableListOf("S","S","S","S","S","S","S","S","S"),
        mutableListOf("S","S","S","S","S","S","S","S","S"),
    )

    private var numberOfRows = 0
    private var numberOfSetPerRow = 0
    private val ticketPrice1 = 10
    private val ticketPrice2 = 8

    fun displayMenu() {

        while (true) {
            println("\n1. Show the seats \n2. Buy a ticket \n3. Statistics \n0. Exit")
            when (readln().toInt()) {
                1 -> displaySetsInformation()
                2 -> buyTicket()
                3 -> statistics()
                0 -> break
                else -> println("Invalid Operation! Please chose a correct option")
            }
        }

    }

    /**
     * the function statistics is responsible to print the following
     * 1. The number of purchased tickets;
     * 2. The number of purchased tickets represented as a percentage. Percentages should be rounded to 2 decimal places;
     * 3. Current income;
     * 4. The total income that shows how much money the theatre will get if all the tickets are sold.
     */
    private fun statistics() {

        println("Number of purchased tickets: ${numberOfPurchasedTickets()}")
        println("Percentage: ${percentage()}")
        println("Current income: $${calculateCurrentIncome()}")
        println("Total income: $${calculateTotalIncome()}")

    }

    private fun numberOfPurchasedTickets() : Int {
        var total = 0
        spaces.forEach { row -> row.forEach { col -> if (col == "B") total++ } }
        return total
    }

    private fun totalSets() = numberOfRows * numberOfSetPerRow

    private fun percentage(): String {
        val percentage = numberOfPurchasedTickets().toDouble() / totalSets().toDouble() * 100
        return "%.2f".format(percentage) + "%"
    }

    fun getSetInformation(){
        println("Enter the number of rows:")
        numberOfRows = readln().toInt()
        println("Enter the number of seats in each row:")
        numberOfSetPerRow = readln().toInt()
    }

    private fun displaySetsInformation() {

        println("Cinema:")

        for (row in 0..numberOfSetPerRow) if (row==0) print("  ") else print(" $row")
        println()

        for (i in 0 until numberOfRows) {
            print(" ${i+1}")
            for (j in 0 until numberOfSetPerRow){
                print(" ${spaces[i][j]}")
            }
            println()
        }

    }


    private fun buyTicket(){

        println("Enter a row number:")
        val rowNumber = readln().toInt()
        println("Enter a seat number in that row:")
        val setNumber = readln().toInt()

        try {

            if (spaces[rowNumber-1][setNumber-1] == "B") {
                println("That ticket has already been purchased!")
                buyTicket()
            }
            else {
                spaces[rowNumber-1][setNumber-1] = "B"
                if (numberOfRows * numberOfSetPerRow <= 60) {
                    println("Ticket price: $10")
                }
                else if (numberOfRows/2 >= rowNumber) {
                    println("Ticket price: $10")
                } else {
                    println("Ticket price: $8")
                }
            }

        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
            buyTicket()
        }

    }

    private fun calculateCurrentIncome() : Int {

        var currentIncome = 0

        for (rowIndex in spaces.indices){
            for (colIndex in spaces[rowIndex].indices) {
                if (spaces[rowIndex][colIndex] == "B") currentIncome += ticketPrice(rowIndex + 1)
            }
        }

        return currentIncome
    }

    private fun ticketPrice(row: Int) : Int {
        return if (numberOfRows * numberOfSetPerRow <= 60) {
            ticketPrice1
        }
        else if (row <= numberOfRows / 2) {
            ticketPrice1
        }
        else {
            ticketPrice2
        }
    }

    private fun calculateTotalIncome() : Int {

        return if (numberOfRows * numberOfSetPerRow <= 60) {
            ticketPrice1 * numberOfRows * numberOfSetPerRow
        }
        else {

            val frontSetsPrice = floor(numberOfRows.toDouble(),2) * numberOfSetPerRow * ticketPrice1
            val backSetsPrice = ceil(numberOfRows.toDouble(),2)* numberOfSetPerRow * ticketPrice2

            frontSetsPrice + backSetsPrice
        }
    }

    private fun ceil(number: Double, dividend: Int): Int = (number / dividend).toBigDecimal().setScale(0,RoundingMode.CEILING).toInt()
    private fun floor(number: Double, dividend: Int): Int = (number / dividend).toBigDecimal().setScale(0,RoundingMode.FLOOR).toInt()

}