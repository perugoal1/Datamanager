package com.csvdatamanager.csv.utils

import com.csvdatamanager.csv.models.CsvRow
import org.apache.commons.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.springframework.web.multipart.MultipartFile
import java.io.*

object CSVHelper {
    private var TYPE = "text/csv"
    fun hasCSVFormat(file: MultipartFile): Boolean {
        return TYPE == file.contentType || file.contentType == "application/vnd.ms-excel"
    }

    fun csvToData(`is`: InputStream?): List<CsvRow> {
        try {
            BufferedReader(InputStreamReader(`is`, "UTF-8")).use { fileReader ->
                CSVParser(
                    fileReader,
                    CSVFormat.RFC4180.withFirstRecordAsHeader()
                ).use { csvParser ->
                    val rowList: MutableList<CsvRow> =
                        ArrayList<CsvRow>()
                    val csvRecords: Iterable<CSVRecord> = csvParser.records
                    for (csvRecord in csvRecords) {
//                        if (index == 0) continue;
//                        val rec= csvRecord.get("values")
                        println(csvRecord.get("InvoiceNo"))
                        println(csvRecord.get("CustomerID")?.toDoubleOrNull() ?: 0)
                        val row = CsvRow(
                            csvRecord.get("InvoiceNo"),//InvoiceNo
                            csvRecord.get("StockCode"), //StockCode
                            csvRecord.get("Description") ?: "", //Description
                            csvRecord.get("Quantity")?.toIntOrNull() ?: 0, //Quantity
                            csvRecord.get("InvoiceDate"), //InvoiceDate
                            csvRecord.get("UnitPrice")?.toDoubleOrNull() ?: 0.00, //UnitPrice
                            csvRecord.get("CustomerID")?.toIntOrNull() ?: 0, //CustomerID
                            csvRecord.get("Country"), //Country
                        )
                        rowList.add(row)
                    }
                    println(rowList)
                    return rowList
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("fail to parse CSV file: " + e.message)
        }
    }
}