package com.csvdatamanager.csv.repositories

import com.csvdatamanager.csv.models.CsvRow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


@Repository
interface CsvRepository : JpaRepository<CsvRow?, Int?>, JpaSpecificationExecutor<CsvRow>
