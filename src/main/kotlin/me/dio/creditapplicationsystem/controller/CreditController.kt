package me.dio.creditapplicationsystem.controller

import jakarta.validation.Valid
import me.dio.creditapplicationsystem.dto.CreditDto
import me.dio.creditapplicationsystem.dto.CreditView
import me.dio.creditapplicationsystem.dto.CreditViewList
import me.dio.creditapplicationsystem.entity.Credit
import me.dio.creditapplicationsystem.service.impl.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditController(private val creditService: CreditService) {
    @PostMapping
    fun saveCredit(@RequestBody @Valid creditDto: CreditDto): ResponseEntity<CreditView> {
        val credit: Credit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CreditView(credit))
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long): ResponseEntity<List<CreditViewList>> {
        val creditViewList = this.creditService.findAllByCustomer(customerId).stream().map { credit: Credit -> CreditViewList(credit) }.collect(Collectors.toList())

        return ResponseEntity.status(HttpStatus.OK).body(creditViewList)

    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(@RequestParam(value = "customerId") customerId: Long, @PathVariable creditCode: UUID): ResponseEntity<CreditView> {
        val credit: Credit = this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(CreditView(credit))

    }
}