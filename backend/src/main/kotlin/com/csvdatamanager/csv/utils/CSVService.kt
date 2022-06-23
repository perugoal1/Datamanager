package com.csvdatamanager.csv.utils

import com.csvdatamanager.csv.models.CsvRow
import com.csvdatamanager.csv.repositories.CsvRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException


@Service
class CSVService {
    @Autowired
    var repository: CsvRepository? = null

    fun save(file: MultipartFile, sseEmitter: SseEmitter?, guid: String) {
        try {
            val rows: List<CsvRow> = CSVHelper.csvToData(file.inputStream)
            val size = rows.size;
            sseEmitter?.send(SseEmitter.event().name(guid).data(0));

            for ((index, item) in rows.withIndex()) {
                repository?.save(item);
                println(size);
                val uploadPercentage: Float = (index.toFloat() * 100 / size).toFloat()
                println(index);
                println(uploadPercentage);
                sseEmitter?.send(SseEmitter.event().name(guid).data(uploadPercentage));
            }
            // repository?.saveAll(rows)
            sseEmitter?.send(SseEmitter.event().name(guid).data(100));
        } catch (e: IOException) {
            throw RuntimeException("fail to store csv data: " + e.message)
        }
    }
//    val allRecords: MutableList<CsvRow?>
//        get() = repository?.findAll() as MutableList<CsvRow?>

    fun findByPagingCriteria(): Page<CsvRow?>? {
        try {
                val paging: Pageable = PageRequest.of(0, 10)

                val csvRow = CsvRow("536416", null, null, null, null, null, null, null);
                val matcher: ExampleMatcher = ExampleMatcher.matchingAny().withIgnoreNullValues()

                val example: Example<CsvRow> = Example.of(csvRow, matcher)
                println(55555)
            val limit: Pageable = PageRequest.of(0, 10, Sort.by("quantity").descending())
                val result =  repository?.findAll(example, limit );
                println(6666)
                println(result)
                return result
        } catch (e: IOException) {
            println(777777)
            println(e)
            throw RuntimeException("fail to get csv data: " + e.message)
        }
}
}
