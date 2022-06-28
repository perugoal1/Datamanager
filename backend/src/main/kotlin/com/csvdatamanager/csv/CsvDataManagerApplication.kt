package com.csvdatamanager.csv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CsvApplication

fun main(args: Array<String>) {
	runApplication<CsvApplication>(*args)
}
