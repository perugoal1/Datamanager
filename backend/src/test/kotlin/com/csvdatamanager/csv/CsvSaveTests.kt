package com.csvdatamanager.csv

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

import com.csvdatamanager.csv.repositories.CsvRepository
import com.csvdatamanager.csv.models.CsvRow
import org.assertj.core.api.Assertions.assertThat

@ExtendWith(MockitoExtension::class)
class CsvSaveTests {

    @Mock
    private var repository: CsvRepository? = null
    @Test
    fun saveSuccess() {
        val csvRow = CsvRow("536365", "84406B", "CREAM CUPID HEARTS COAT HANGER", 8, "12/1/2010 8:26", 2.75, 17850, "United Kingdom")

        Mockito.`when`(repository!!.save(ArgumentMatchers.any(CsvRow::class.java))).thenReturn(csvRow)
        val savedData: CsvRow = repository!!.save(csvRow)

        assertThat(savedData.invoiceNo).isEqualTo("536365")
    }
}