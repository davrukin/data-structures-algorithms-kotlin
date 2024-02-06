package problems.airlineTickets

import kotlin.math.max

enum class SeatingClass(
    // operating cost
    val base: Double = 0.0,
    val perMile: Double = 0.0,
) {
    None,
    Economy,
    Premium(perMile = 0.10), // maximum of $25
    Business(base = 50.0, perMile = 0.25);

    fun calculateOperatingCost(milesTraveled: Double): Double {
        val pm = perMile * milesTraveled

        return if (this == Premium) {
            if (pm <= 25.0) {
                pm
            } else {
                25.0
            }
        } else {
            base + pm
        }
    }
}

enum class Airline {
    United,
    Delta,
    Southwest,
    American,
}

abstract class AirlineTicket {
    abstract val milesTraveled: Double
    abstract val basePerMile: Double
    open val seatingClass: SeatingClass = SeatingClass.None

    abstract fun calculateTotalPrice(): Double
}

class UnitedTicket(
    override val seatingClass: SeatingClass,
    override val milesTraveled: Double,
) : AirlineTicket() {

    override val basePerMile: Double = 0.50

    override fun calculateTotalPrice(): Double {
        return (milesTraveled * basePerMile) + seatingClass.base + (seatingClass.perMile * milesTraveled)
    }
}

class DeltaTicket(
    override val milesTraveled: Double,
) : AirlineTicket() {
    override val basePerMile: Double = 0.90

    override fun calculateTotalPrice(): Double {
        return milesTraveled * basePerMile
    }
}

class SouthwestTicket(
    override val seatingClass: SeatingClass,
    override val milesTraveled: Double,
) : AirlineTicket() {

    override val basePerMile: Double = 0.75

    override fun calculateTotalPrice(): Double {
        val premium = if (seatingClass == SeatingClass.Premium) {
            0.10 * milesTraveled
        } else {
            0.0
        }
        return (milesTraveled * basePerMile) + seatingClass.calculateOperatingCost(milesTraveled) + premium
    }
}

class AmericanTicket(
    override val seatingClass: SeatingClass,
    override val milesTraveled: Double,
) : AirlineTicket() {

    override val basePerMile: Double = 0.50

    override fun calculateTotalPrice(): Double {
        return max(2 * seatingClass.calculateOperatingCost(milesTraveled), milesTraveled)
    }
}