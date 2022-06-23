package com.csvdatamanager.csv.repositories

import com.csvdatamanager.csv.models.CsvRow
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties
import org.springframework.data.domain.Page
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable


@Repository
interface CsvRepository : JpaRepository<CsvRow?, Int?>, JpaSpecificationExecutor<CsvRow>
