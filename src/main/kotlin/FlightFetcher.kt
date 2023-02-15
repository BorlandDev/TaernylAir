import io.ktor.client.*
import io.ktor.client.request.get
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.*

private const val BASE_URL = "http://kotlin-book.bignerdranch.com/2e"
private const val FLIGHT_ENDPOINT = "$BASE_URL/flight"
private const val LOYALTY_ENDPOINT = "$BASE_URL/loyalty"

suspend fun fetchFlight(passengerName: String): FlightStatus = coroutineScope {
    val client = HttpClient(CIO)

    val flightResponse = async {
        println("Starting fetching flight info")
        client.get<String>(FLIGHT_ENDPOINT).also {
            println("Finish fetching flight info")
        }
    }

    val loyaltyResponse = async {
        println("Starting fetching loyalty info")
        client.get<String>(LOYALTY_ENDPOINT).also {
            println("Finish fetching loyalty info")
        }
    }

    delay(500)
    println("Combining flight data")
    FlightStatus.parse(
        passengerName = passengerName,
        flightResponse = flightResponse.await(),
        loyaltyResponse = loyaltyResponse.await()
    )
}