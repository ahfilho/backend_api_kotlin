package br.com.api.kotlin.controller

import br.com.api.kotlin.dto.AddressDto
import br.com.api.kotlin.dto.ClientDto
import br.com.api.kotlin.entity.Address
import br.com.api.kotlin.entity.Client
import br.com.api.kotlin.service.ClientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client")
class ClientController(private val clientService: ClientService) {

    @GetMapping
    fun list(): List<Client> {
        return clientService.list()
    }

    @PostMapping
    fun save(@RequestBody clientDto: ClientDto): ResponseEntity<Any> {
        clientService.saveClient(clientDto)
        return ResponseEntity.status(HttpStatus.OK).body(/* body = */ "Cliente " + clientDto.name + " salvo com sucesso!")
    }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable id: Long?, @RequestBody client: Client, address: Address): ResponseEntity<Client?>? {
        client.id
        return ResponseEntity.ok().body(client.address?.let { clientService.updateClient(id!!, client, it) })
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Long?): HttpStatus? {
        this.clientService.deleteClient(id!!)
        return HttpStatus.OK
    }


}