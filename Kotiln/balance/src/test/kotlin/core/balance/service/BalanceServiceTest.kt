package core.balance.service

import core.balance.database.InMemoryDatabase
import core.balance.domain.Account
import core.balance.repository.AccountRepository
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class BalanceServiceTest {
    private val inMemoryDatabase: InMemoryDatabase = InMemoryDatabase()

    private val accountRepository: AccountRepository = AccountRepository(inMemoryDatabase)

    private val balanceService: BalanceService = BalanceService(accountRepository)

    @Nested
    inner class `입금 처리를 할 때` {
        private val numberOfThreads = 20
        private val repeatCount = 1000

        @Test
        fun `동시에 입금 처리를 할 수 있다`() {
            // given
            val (id, balance) = 1L to 1000L
            val account = Account(id, balance)
            inMemoryDatabase.save(account)

            val executor = Executors.newFixedThreadPool(numberOfThreads)
            val latch = CountDownLatch(numberOfThreads)

            // when
            repeat(repeatCount) {
                executor.submit {
                    try {
                        balanceService.balance(id, 1L)
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
            executor.shutdown()

            // then
            val expectBalance = 2000L
            SoftAssertions.assertSoftly { softly ->
                softly.assertThat(account.id).isEqualTo(id)
                softly.assertThat(account.balance).isEqualTo(expectBalance)
            }
        }
    }

    @Nested
    inner class `출금 처리를 할 때` {
        private val numberOfThreads = 20
        private val repeatCount = 1000

        @Test
        fun `동시에 출금 처리를 할 수 있다`() {
            // given
            val (id, balance) = 1L to 1000L
            val account = Account(id, balance)
            inMemoryDatabase.save(account)

            val executor = Executors.newFixedThreadPool(numberOfThreads)
            val latch = CountDownLatch(numberOfThreads)

            // when
            repeat(repeatCount) {
                executor.submit {
                    try {
                        balanceService.withdraw(id, 1L)
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
            executor.shutdown()

            // then
            SoftAssertions.assertSoftly { softly ->
                softly.assertThat(account.id).isEqualTo(id)
                softly.assertThat(account.balance).isZero
            }
        }
    }

    @Nested
    inner class `돈을 옮길 때` {
        private val numberOfThreads = 20
        private val repeatCount = 1000

        @Test
        fun `동시에 입금,출금 처리를 할 수 있다`() {
            // given
            val (fromId, fromBalance) = 1L to 1000L
            val (toId, toBalance) = 2L to 1000L

            val fromAccount = Account(fromId, fromBalance)
            val toAccount = Account(toId, toBalance)

            inMemoryDatabase.save(fromAccount)
            inMemoryDatabase.save(toAccount)

            val executor = Executors.newFixedThreadPool(numberOfThreads)
            val latch = CountDownLatch(numberOfThreads)

            // when
            repeat(repeatCount) {
                executor.submit {
                    try {
                        balanceService.transfer(fromId, toId, 1L)
                    } finally {
                        latch.countDown()
                    }
                }
            }
            latch.await()
            executor.shutdown()

            // then
            val expectFromBalance = 0L
            val expectToBalance = 2000L
            SoftAssertions.assertSoftly { softly ->
                softly.assertThat(fromAccount.balance).isEqualTo(expectFromBalance)
                softly.assertThat(toAccount.balance).isEqualTo(expectToBalance)
            }
        }
    }
}
