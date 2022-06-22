package com.csvdatamanager.csv.models

import javax.persistence.*

@Entity
@Table(name = "csv")
class CsvRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "invoiceNo")
    var invoiceNo: Long = 0

    @Column(name = "stockCode")
    var stockCode: String? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "quantity")
    var quantity: Int = 0

    @Column(name = "invoiceDate")
    var invoiceDate: String? = null

    @Column(name = "unitPrice")
    var unitPrice: Double = 0.00

    @Column(name = "customerId")
    var customerId: Long = 0

    @Column(name = "country")
    var country: String? = null

    constructor() {}
    constructor(invoiceNo: Long, stockCode: String?, description: String?, quantity: Int, invoiceDate: String?, unitPrice: Double, customerId: Long, country: String?) {
        this.invoiceNo = invoiceNo
        this.stockCode = stockCode
        this.description = description
        this.quantity = quantity
        this.invoiceDate = invoiceDate
        this.unitPrice = unitPrice
        this.customerId = customerId
        this.country = country
    }
}