package com.csvdatamanager.csv

import com.csvdatamanager.csv.models.CsvRow
import com.csvdatamanager.csv.repositories.CsvRepository
import com.csvdatamanager.csv.utils.CSVService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.mock.web.MockMultipartFile


@SpringBootTest
@ExtendWith(MockitoExtension::class)
class CsvAddDataTests {
    @Mock
    private var repository: CsvRepository? = null
    @Autowired
    var csvService: CSVService? = null

    @Autowired
    private val resourceLoader: ResourceLoader? = null
    @Test
    fun addData() {
        try {
            val resource: Resource = resourceLoader!!.getResource("classpath:sample.csv")
            val csvFile = MockMultipartFile("data", "sample.csv", "text/csv", resource.inputStream)
            csvService?.save(csvFile, null, "e6454fa4-530d-4e2c-83df-b223a4041f48")
            assert(true)
        } catch (e: Exception) {
            fail("Should not have thrown any exception")
        }

    }
}