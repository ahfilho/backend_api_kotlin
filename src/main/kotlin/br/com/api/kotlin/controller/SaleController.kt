package br.com.api.kotlin.controller

import br.com.api.kotlin.code.GeneratorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import br.com.api.kotlin.entity.Sale
import br.com.api.kotlin.service.SaleService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/sale")
@RestController
class SaleController(
    private val saleService: SaleService, private val generatorCode: GeneratorCode,
) {

    @PostMapping
    fun save(@RequestBody sale: Sale): ResponseEntity<Any> {

        val codeSize = 10
        val codeFinal = generatorCode.generateCode(codeSize)
        println(codeFinal)
        val existingSaleCode = saleService.findByClientName(sale.saleCode)

        if (existingSaleCode == codeFinal) {
            ResponseEntity.status(HttpStatus.FOUND)
            println("Código encontrado na base de dados.")

        }
        try {
            sale.saleCode = codeFinal
            println("======= Nota Fiscal =======")
            if (sale.clientName.equals("")) {
                println("Nome: Não informado na venda.")
            } else {
                println("Nome: ${sale.clientName}")
            }
            println("Tipo de Cartão: ${sale.typeCard} ")
            println("Peso: ${sale.weight} kg")
            println("Data da Venda: ${sale.saleDate} ")
            println("Valor da Venda: R$${sale.saleValue}")
            println("Código da Venda:$codeFinal")
            println("==========================")
            saleService.save(sale)
            return ResponseEntity.status(HttpStatus.OK).body("Venda realizada com sucesso.")
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Venda não realizada.")
        }
    }

    @GetMapping
    fun list(): List<Sale> {
        return saleService.List()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long?): HttpStatus? {
        val del = saleService.deleteSale(id!!)
        if (del != 0) {
            saleService.deleteSale(id)
            return HttpStatus.OK
        }
        return HttpStatus.BAD_REQUEST

    }
}