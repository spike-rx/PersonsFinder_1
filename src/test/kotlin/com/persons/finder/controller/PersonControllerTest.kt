package com.persons.finder.controller


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put
import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet


/**
 * personController api test
 * @author spike
 */
@AutoConfigureMockMvc
@SpringBootTest
class PersonControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    private val basUrl = "http://localhsot:8080/api/v1/persons"

    @Test
    fun `insert data`() {

        mockMvc.get(basUrl + "/name?ids=1,2,3").andExpect {
            status { isOk() }
            content {
                contentTypeCompatibleWith("text/plain")
                string("Hello Example")
            }
        }
    }
    /*
    * example test
    * */
    @Test
    fun `example test`() {

        mockMvc.get(basUrl + "/example").andExpect {
            status { isOk() }
            content {
                contentTypeCompatibleWith("text/plain")
                string("Hello Example")
            }
        }
    }



    /*
    * location create and update test
    *
    * */
    @Test
    fun `location create test`() {
        mockMvc.put(basUrl + "/location/createOrUpdate") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = """
                {
                  "referenceId": "2",
                  latitude: 2234.24355,
                  longitude: 234354.234355
                }
            """.trimIndent()

        }.andExpect { status { isOk() } }
    }

}