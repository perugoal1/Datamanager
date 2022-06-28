package com.csvdatamanager.csv

import com.csvdatamanager.csv.models.CsvRow
import com.csvdatamanager.csv.repositories.CsvRepository
import com.csvdatamanager.csv.utils.CSVService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable


@SpringBootTest
@ExtendWith(MockitoExtension::class)
class CsvListDataTests {

    @Mock
    private var repository: CsvRepository? = null
    @Autowired
    var csvService: CSVService? = null
    @Test
    fun listData() {
        val result = csvService?.findByPagingCriteria(
            "0",
            mutableMapOf("invoiceNo" to null,
                "stockCode" to null ,
                "description" to null,
                "quantity" to null,
                "invoiceDate" to null,
                "unitPrice" to null,
                "customerId" to null,
                "country" to null
            ),
            mutableMapOf("sort" to null,
                "order" to null
            )
        );
        if (result != null) {
            println(result.content)
        }
        assertThat(result).isNotNull
    }
}