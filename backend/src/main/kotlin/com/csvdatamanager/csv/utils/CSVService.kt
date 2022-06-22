package com.csvdatamanager.csv.utils

import com.csvdatamanager.csv.models.CsvRow
import com.csvdatamanager.csv.repositories.CsvRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.IOException

@Service
class CSVService {
    @Autowired
    var repository: CsvRepository? = null
    fun save(file: MultipartFile) {
        try {
            val rows: List<CsvRow> = CSVHelper.csvToData(file.inputStream)
            println(111111)
            println(rows[0].invoiceNo)
            repository?.saveAll(rows)
        } catch (e: IOException) {
            throw RuntimeException("fail to store csv data: " + e.message)
        }
    }
    val allTutorials: MutableList<CsvRow?>
        get() = repository?.findAll() as MutableList<CsvRow?>
}