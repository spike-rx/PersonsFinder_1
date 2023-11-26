package com.persons.finder.domain.services

import com.persons.finder.data.Location
import com.persons.finder.data.Person
import com.persons.finder.respository.LocationRepository
import com.persons.finder.respository.PersonRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.transaction.TransactionManager

/**
 * Asynchronously store big data for testing
 */

const val size = 1000
@Service
class SaveDataServiceImpl(
    private val personRepository: PersonRepository,
    private val locationsRepository: LocationRepository,
) : SaveDataService {
    @Async
    @Transactional
    override fun saveMillionData() {

        val locationList: MutableList<Location> = mutableListOf()
        for (i in 3..1000000) {
            val location = Location(null, i.toLong(), 9999.9999, 99999.9999)
            locationList.add(location);
            if(locationList.size == 1000) {
                locationsRepository.saveAll(locationList)
                locationsRepository.flush()
                locationList.clear()
            }
        }

    }

    @Async
    @Transactional
    override fun saveTenMillionData() {

        val personList: MutableList<Person> = mutableListOf()
        for (i in 1..10000000) {
            val person = Person(null, "xu")
            personList.add(person)
            if (personList.size == 1000) {
                personRepository.saveAll(personList)
                personRepository.flush()
                personList.clear()
            }

        }
    }

    @Async
    @Transactional
    override fun saveHundredMillionData() {
        val personList: MutableList<Person> = mutableListOf()
        for (i in 1..100000000) {
            val person = Person(null, "xu")
            personList.add(person);
            if (personList.size == 1000) {
                personRepository.saveAll(personList)
                personRepository.flush()
                personList.clear()
            }
        }
    }
}