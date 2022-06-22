package com.csvdatamanager.csv.utils

import com.csvdatamanager.csv.models.CsvRow
import org.apache.commons.csv.*
import org.springframework.web.multipart.MultipartFile
import java.io.*

object CSVHelper {
    var TYPE = "text/csv"
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
                        val row = CsvRow(
                            csvRecord.get("InvoiceNo").toLong(),//InvoiceNo
                            csvRecord.get("StockCode"), //StockCode
                            csvRecord.get("Description"), //Description
                            csvRecord.get("Quantity").toInt(), //Quantity
                            csvRecord.get("InvoiceDate"), //InvoiceDate
                            csvRecord.get("UnitPrice").toDouble(), //UnitPrice
                            csvRecord.get("CustomerID").toLong(), //CustomerID
                            csvRecord.get("Country"), //Country
                        )
                        println(3333)
                        println(row)
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

    fun dataToCSV(rowList: List<CSVRecord?>): ByteArrayInputStream {
        val format: CSVFormat = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL)
        try {
            ByteArrayOutputStream().use { out ->
                CSVPrinter(PrintWriter(out), format).use { csvPrinter ->
                    for (row in rowList) {
                        val data: List<CSVRecord?> = listOf(row)
                        csvPrinter.printRecord(data)
                    }
                    csvPrinter.flush()
                    return ByteArrayInputStream(out.toByteArray())
                }
            }
        } catch (e: IOException) {
            throw RuntimeException("fail to import data to CSV file: " + e.message)
        }
    }
}