package com.example.flight.data

import kotlinx.coroutines.flow.Flow

class OfflineFlightRepository(private val flightDao: FlightDao) : FlightRepository {
    override fun getAirportsByQuery(query: String): Flow<List<Airport>> = flightDao.getAirportsByQuery(query)
    // ... triển khai tất cả các phương thức khác bằng cách gọi DAO tương ứng
}