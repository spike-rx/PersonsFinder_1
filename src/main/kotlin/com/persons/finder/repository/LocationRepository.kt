package com.persons.finder.repository

import com.persons.finder.data.Location
import com.persons.finder.dto.SearchLocation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * query location table
 */
interface LocationRepository : JpaRepository<Location, Long> {

    /**
     * delete data
     * @param referenceId person id
     */
    fun deleteByReferenceId(referenceId: Long): Long


    /**
     * find people around and sorted by distance ASC
     * @param searchLocation search people around dto
     * @return people ids around 50km sort by distance
     */
    @Query("select L.reference_id, L.id, L.longitude, L.latitude, (POWER(MOD(ABS(L.longitude - :#{#params.currentLongitude}), 360), 2) + POWER(ABS(L.latitude - :#{#params.currentLatitude}), 2)) AS distance from location L where L.latitude between :#{#params.minLatitude} and :#{#params.maxLatitude} and L.longitude BETWEEN :#{#params.minLongitude} and :#{#params.maxLongitude} ORDER BY distance", nativeQuery = true)
    fun findReferenceIdByLatitudeBetweenAndLongitudeBetween(@Param("params") searchLocation: SearchLocation): List<Long>


    /**
     * find location by reference id
     *  @param referenceId person id
     *  @return location person location details
     */
    fun findFirstByReferenceId(referenceId: Long): Location?
}