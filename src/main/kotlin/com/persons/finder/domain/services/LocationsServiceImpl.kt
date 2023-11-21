package com.persons.finder.domain.services

import com.persons.finder.data.Location
import com.persons.finder.dto.SearchLocation
import com.persons.finder.respository.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import kotlin.math.*


// earth radius
const val RADIUS: Double = 6371.0
const val radiusInKm: Double = 10.0

/**
 * location service
 */
@Service
@Transactional
class LocationsServiceImpl(
    @Autowired
    private val locationRepository: LocationRepository

) : LocationsService {

    /**
     * add or update location
     * @param location
     */
    @Transactional
    override fun addLocation(location: Location): Location {
        return locationRepository.save(location);
    }

    /**
     * delete location
     * @param id  location id
     */
    @Transactional
    override fun removeLocation(locationReferenceId: Long): Long{
        return locationRepository.deleteByReferenceId(locationReferenceId)
    }

    /**
     * It's better to use redis and make ur life easier
     * calculate people around 50km
     * use square (min longitude - max longitude) (min latitude - max latitude)
     *  @param list - reference id list
     *
     */
    override fun findAround(referenceId: Long): List<Long> {
        val location = locationRepository.findFirstByReferenceId(referenceId)?: throw NoSuchElementException("No such data with $referenceId")

        // use Haversine calculate scope
        var dLongitude: Double = 2*asin(sin( radiusInKm/(2* RADIUS)) / cos(location.latitude* PI / 180))
        dLongitude = dLongitude * 180 / PI
        var dLatitude: Double = radiusInKm / RADIUS;

        // Convert angles to radians
        dLatitude = dLatitude * 180 / Math.PI;

        var minLatitude: Double = location.latitude - dLatitude;
        var maxLatitude = location.latitude + dLatitude;
        if (minLatitude > maxLatitude) {
            val temp = minLatitude
            minLatitude = maxLatitude
            maxLatitude = temp
        }
        var minLongitude: Double = location.longitude - dLongitude;
        var maxLongitude: Double = location.longitude + dLongitude
        if (minLongitude > maxLongitude) {
            val temp = minLongitude
            minLongitude = maxLongitude
            maxLongitude = temp

        }
        val searchLocation = SearchLocation(location.longitude, location.latitude, maxLongitude, minLongitude, maxLatitude, minLatitude)
        // find all location in minLatitude - maxLatitude and minLongitude and maxLongitude
        return locationRepository.findReferenceIdByLatitudeBetweenAndLongitudeBetween(searchLocation)
    }



}