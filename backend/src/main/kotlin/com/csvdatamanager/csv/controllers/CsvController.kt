package com.csvdatamanager.csv.controllers

import com.csvdatamanager.csv.dtos.ResponseMessage
import com.csvdatamanager.csv.models.CsvRow
import com.csvdatamanager.csv.utils.CSVHelper.hasCSVFormat
import com.csvdatamanager.csv.utils.CSVService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@RestController
class MessageResource {
    @GetMapping
    fun index(): List<Message> = listOf(
        Message("1", "Hello!"),
        Message("2", "Bonjour!"),
        Message("3", "Privet!"),
    )
}
data class Message(val id: String?, val text: String)
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
class CSVController {
    @Autowired
    var fileService: CSVService? = null

    private val sseEmitters: MutableMap<String, SseEmitter> = ConcurrentHashMap()

    @GetMapping("/progress")
    @Throws(IOException::class)
    fun eventEmitter(): SseEmitter? {
        val sseEmitter = SseEmitter(Long.MAX_VALUE)
        val guid = UUID.randomUUID()
        sseEmitters[guid.toString()] = sseEmitter
        sseEmitter.send(SseEmitter.event().name("GUI_ID").data(guid))
        sseEmitter.onCompletion { sseEmitters.remove(guid.toString()) }
        sseEmitter.onTimeout { sseEmitters.remove(guid.toString()) }
        return sseEmitter
    }
    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile, @RequestParam("guid") guid: String): ResponseEntity<ResponseMessage> {
        var message = ""
        if (hasCSVFormat(file)) {
            return try {
                fileService!!.save(file, sseEmitters[guid], guid);
                sseEmitters.remove(guid);
                message = "Uploaded the file successfully: " + file.originalFilename
                val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/csv/download/")
                    .path(file.originalFilename!!)
                    .toUriString()
                ResponseEntity.status(HttpStatus.OK).body(ResponseMessage(message, fileDownloadUri))
            } catch (e: Exception) {
                message = "Could not upload the file: " + file.originalFilename + "!" + e.message
                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseMessage(message, ""))
            }
        }
        message = "Please upload a csv file!"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessage(message, ""))
    }

    @GetMapping("/getData")
    fun getAllCsv( @RequestParam("page", required=false) page: String,
                   @RequestParam("invoiceNo", required=false) invoiceNo: String?,
                   @RequestParam("stockCode", required=false) stockCode: String?,
                   @RequestParam("description", required=false) description: String?,
                   @RequestParam("quantity", required=false) quantity: String?,
                   @RequestParam("invoiceDate", required=false) invoiceDate: String?,
                   @RequestParam("unitPrice", required=false) unitPrice: String?,
                   @RequestParam("customerId", required=false) customerId: String?,
                   @RequestParam("country", required=false) country: String?,
                   @RequestParam("sort", required=false) sort: String?,
                   @RequestParam("order", required=false) order: String?
                   ): ResponseEntity<Page<CsvRow?>?>? {
        return try {
            println(page)
            val filterParam: MutableMap<String, String?> = mutableMapOf<String, String?>()
            filterParam["invoiceNo"] = invoiceNo
            filterParam["stockCode"] = stockCode
            filterParam["description"] = description
            filterParam["quantity"] = quantity
            filterParam["invoiceDate"] = invoiceDate
            filterParam["unitPrice"] = unitPrice
            filterParam["customerId"] = customerId
            filterParam["country"] = country

            val sortParam: MutableMap<String, String?> = mutableMapOf<String, String?>()
            sortParam["sort"] = sort
            sortParam["order"] = order

            val rows: Page<CsvRow?>? = fileService?.findByPagingCriteria(page, filterParam, sortParam)
            println(rows)
            if (rows?.isEmpty == true) {
                ResponseEntity<Page<CsvRow?>?>(rows, HttpStatus.OK)
            } else ResponseEntity<Page<CsvRow?>?>(rows, HttpStatus.OK)
        } catch (e: java.lang.Exception) {
            ResponseEntity<Page<CsvRow?>?>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}