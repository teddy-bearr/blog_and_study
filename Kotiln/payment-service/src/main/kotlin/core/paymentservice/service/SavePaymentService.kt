package core.paymentservice.service

import core.paymentservice.domain.PaymentEvent
import core.paymentservice.repository.PaymentEventRepository
import core.paymentservice.repository.PaymentOrderRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SavePaymentService(
    private val paymentOrderRepository: PaymentOrderRepository,
    private val paymentEventRepository: PaymentEventRepository
) {
    fun save(paymentEvent: PaymentEvent): Mono<PaymentEvent> {
        return paymentEventRepository.save(paymentEvent)
            .flatMap { savedPaymentEvent ->
                val updatedPaymentOrders = savedPaymentEvent.paymentOrders.map { it.updatePaymentEventId(savedPaymentEvent.id) }
                paymentOrderRepository.saveAll(updatedPaymentOrders)
                    .collectList()
                    .map { savedPaymentEvent }
            }
    }
}
