package com.csvdatamanager.csv.controllers

import com.csvdatamanager.csv.models.CsvRow
import com.csvdatamanager.csv.utils.CSVHelper.hasCSVFormat
import com.csvdatamanager.csv.utils.CSVService
import com.csvdatamanager.csv.dtos.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

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

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/csv")
class CSVController {
    @Autowired
    var fileService: CSVService? = null
    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ResponseMessage> {
        var message = ""
        if (hasCSVFormat(file)) {
            return try {
                fileService!!.save(file)
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
    fun getAllCsv(): ResponseEntity<List<CsvRow?>?>? {
        return try {
            val rows: List<CsvRow?> = fileService!!.allTutorials

            if (rows.isEmpty()) {
                ResponseEntity<List<CsvRow?>?>(HttpStatus.NO_CONTENT)
            } else ResponseEntity<List<CsvRow?>?>(rows, HttpStatus.OK)
        } catch (e: java.lang.Exception) {
            ResponseEntity<List<CsvRow?>?>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
