package me.dio.creditapplicationsystem.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.dio.creditapplicationsystem.dto.CreditDto
import me.dio.creditapplicationsystem.dto.CustomerDto
import me.dio.creditapplicationsystem.entity.Address
import me.dio.creditapplicationsystem.entity.Credit
import me.dio.creditapplicationsystem.entity.Customer
import me.dio.creditapplicationsystem.repository.CreditRepository
import me.dio.creditapplicationsystem.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditControllerTest {
    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var creditRepository: CreditRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL1: String = "/api/credits"
        const val URL2: String = "/api/customers"
    }

//    @BeforeEach
//    fun setup() = creditRepository.deleteAll()
//
//    @AfterEach
//    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should create a credit and return 201 status`() {
        //given
        val creditDto: CreditDto = builderCreditDto()
        val valueCreditAsString: String = objectMapper.writeValueAsString(creditDto)
        val customerDto: CustomerDto = builderCustomerDto()
        val valueCustomerAsString: String = objectMapper.writeValueAsString(customerDto)
        //when
        //then
        mockMvc.perform(
                MockMvcRequestBuilders.post(URL2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueCustomerAsString)
        )
        mockMvc.perform(
                MockMvcRequestBuilders.post(URL1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueCreditAsString)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andDo(MockMvcResultHandlers.print())

    }


    @Test
    fun `should not save a credit with empty creditValue and return 400 status`() {
        //given
        val creditDto: CreditDto = builderCreditDto(creditValue = BigDecimal.valueOf(0))
        val valueAsString: String = objectMapper.writeValueAsString(creditDto)
        //when
        //then
        mockMvc.perform(
                MockMvcRequestBuilders.post(URL1)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request. Consult the documentation"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should find credits by customerId and return 200 status`() {
        //given
        val customer: Customer = customerRepository.save(builderCustomerDto().toEntity())
        //when
        //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("${URL1}?customerId=${customer.id}")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
    }

//    @Test
//    fun `should find creditCode by customerId and return 200 status`() {
//        //given
//        val credit: Credit = buildCredit()
//        val valueCreditAsString: String = objectMapper.writeValueAsString(credit)
//        val customer: Customer = buildCustomer()
//        val valueCustomerAsString: String = objectMapper.writeValueAsString(customer)
//        val creditCode: Credit = buildCredit(creditCode = UUID.fromString("a3fbe3b0-d9ee-4b31-ad83-303d913ffccd"))
//        //when
//        //then
//        mockMvc.perform(
//                MockMvcRequestBuilders.post(URL2)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(valueCustomerAsString)
//        )
//        mockMvc.perform(
//                MockMvcRequestBuilders.post(URL1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(valueCreditAsString)
//        )
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("$URL1/$creditCode?customerId=${customer.id}")
//                        .accept(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(MockMvcResultMatchers.status().isOk)
//                .andDo(MockMvcResultHandlers.print())
//    }




    private fun builderCreditDto(
            creditValue: BigDecimal = BigDecimal.valueOf(1000),
            dayFirstOfInstallment: LocalDate = LocalDate.of(2024, Month.APRIL, 2),
            numberOfInstallments: Int = 5,
            customerId: Long = 1L,
    ): CreditDto = CreditDto(
            creditValue = creditValue,
            dayFirstOfInstallment = dayFirstOfInstallment,
            numberOfInstallments = numberOfInstallments,
            customerId = customerId,

    )

    private fun buildCredit(
            creditValue: BigDecimal = BigDecimal.valueOf(100.0),
            dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(2L),
            numberOfInstallments: Int = 15,
            customer: Customer = buildCustomer(),
            creditCode: UUID = UUID.fromString("a3fbe3b0-d9ee-4b31-ad83-303d913ffccd")
    ): Credit = Credit(
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallments = numberOfInstallments,
            customer = customer,
            creditCode = creditCode
    )

    private fun builderCustomerDto(
            firstName: String = "Bruna",
            lastName: String = "Floriano",
            cpf: String = "83364275033",
            email: String = "bruna@email.com",
            income: BigDecimal = BigDecimal.valueOf(9000.0),
            password: String = "123456",
            zipCode: String = "1234",
            street: String = "street 1",
    ) = CustomerDto(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            income = income,
            password = password,
            zipCode = zipCode,
            street = street
    )

    private fun buildCustomer(
            firstName: String = "Bruna",
            lastName: String = "Floriano",
            cpf: String = "83364275033",
            email: String = "bruna@email.com",
            password: String = "123456",
            zipCode: String = "12345",
            street: String = "Street 1",
            income: BigDecimal = BigDecimal.valueOf(9000.0),
            id: Long = 1L
    ) = Customer(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            address = Address(
                    zipCode = zipCode,
                    street = street,
            ),
            income = income,
            id = id
    )

}