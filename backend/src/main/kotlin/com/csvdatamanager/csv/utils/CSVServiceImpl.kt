//package com.csvdatamanager.csv.utils
//
//import com.csvdatamanager.csv.models.CsvRow
//import com.csvdatamanager.csv.repositories.CsvRepository
//import com.csvdatamanager.csv.utils.CSVService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.domain.*
//import org.springframework.stereotype.Service
//import org.springframework.web.multipart.MultipartFile
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
//import java.io.IOException
//
//
//@Service
//class CSVServiceImpl(repository: CsvRepository): CSVService {
//    @Autowired
//    var repository: CsvRepository? = null
//    init {
//        this.repository = repository
//    }
//    fun save(file: MultipartFile, sseEmitter: SseEmitter?, guid: String) {
//        try {
//            val rows: List<CsvRow> = CSVHelper.csvToData(file.inputStream)
//            val size = rows.size;
//            sseEmitter?.send(SseEmitter.event().name(guid).data(0));
//
//            for ((index, item) in rows.withIndex()) {
//                repository?.save(item);
//                println(size);
//                val uploadPercentage: Float = (index.toFloat() * 100 / size).toFloat()
//                println(index);
//                println(uploadPercentage);
//                sseEmitter?.send(SseEmitter.event().name(guid).data(uploadPercentage));
//            }
//            // repository?.saveAll(rows)
//            sseEmitter?.send(SseEmitter.event().name(guid).data(100));
//        } catch (e: IOException) {
//            throw RuntimeException("fail to store csv data: " + e.message)
//        }
//    }
////    val allRecords: MutableList<CsvRow?>
////        get() = repository?.findAll() as MutableList<CsvRow?>
//
//    fun findByPagingCriteria(page: String, filterParam: MutableMap<String, String?>, sortParam: MutableMap<String, String?>): Page<CsvRow?>? {
//        try {
//            val csvRow = CsvRow(
//                filterParam["invoiceNo"] ?: null,
//                filterParam["stockCode"]?: null,
//                filterParam["description"]?: null,
//                filterParam["quantity"]?.toIntOrNull() ?: null,
//                filterParam["invoiceDate"]?: null,
//                filterParam["unitPrice"]?.toDoubleOrNull() ?: null,
//                filterParam["customerId"]?.toIntOrNull() ?: null,
//                filterParam["country"]?: null
//            );
//
//            val matcher: ExampleMatcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//            val example: Example<CsvRow> = Example.of(csvRow, matcher)
//            val checkFilterParams = (
//                    filterParam["invoiceNo"].isNullOrEmpty() &&
//                            filterParam["stockCode"].isNullOrEmpty() &&
//                            filterParam["description"].isNullOrEmpty() &&
//                            filterParam["quantity"].isNullOrEmpty() &&
//                            filterParam["invoiceDate"].isNullOrEmpty() &&
//                            filterParam["unitPrice"].isNullOrEmpty() &&
//                            filterParam["customerId"].isNullOrEmpty() &&
//                            filterParam["country"].isNullOrEmpty()
//                    )
//
//            val checkSortParams = (
//                    sortParam["sort"].isNullOrEmpty()
//                    )
//
//            val limit: Pageable = if(!checkSortParams) {
//                if (sortParam["order"] == "asc") {
//                    PageRequest.of(page.toInt(), 10, Sort.by(sortParam["sort"]).ascending())
//                } else {
//                    PageRequest.of(page.toInt(), 10, Sort.by(sortParam["sort"]).descending())
//                }
//            } else {
//                PageRequest.of(page.toInt(), 10)
//            }
//            val result: Page<CsvRow?>? = if(!checkFilterParams){
//                repository?.findAll(example, limit );
//            } else{
//                repository?.findAll( limit );
//            }
//            return result
//        } catch (e: IOException) {
//            println(e)
//            throw RuntimeException("fail to get csv data: " + e.message)
//        }
//    }
//}
