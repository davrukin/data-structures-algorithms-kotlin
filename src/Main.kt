import problems.airlineTickets.*
import kotlin.math.max


fun main() {
    val input = listOf(
        "United 150.0 Business",
        "Delta 60.0 Economy",
        "Southwest 1000.0 Premium",
        "American 95.0 Business"
    )
    // Implement your ticket calculator here

    input
        .forEach { ticket ->
            ticket
                .split(" ")
                .let { ticketInfo -> // array inside
                    val airline = Airline.valueOf(ticketInfo[0])
                    val milesTraveled = ticketInfo[1].toDouble()
                    val seatingClass = SeatingClass.valueOf(ticketInfo[2])

                    val ticketPrice = when (airline) {
                        Airline.United -> UnitedTicket(seatingClass, milesTraveled)
                        Airline.Delta -> DeltaTicket(milesTraveled)
                        Airline.Southwest -> SouthwestTicket(seatingClass, milesTraveled)
                        Airline.American -> AmericanTicket(seatingClass, milesTraveled)
                    }

                    // can be optimized, not the best way to check

                    println(ticketPrice.calculateTotalPrice())
                }
        }
}