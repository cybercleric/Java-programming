package account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsManagerTest {
    private Account accountFirst, accountSecond, accountThird;
    private AnalyticsManager analyticsManager;

    @BeforeEach
    void setUp() {
        TransactionManager transactionManager = new TransactionManager();
        accountFirst = new Account(1, transactionManager);
        accountSecond = new Account(2, transactionManager);
        accountThird = new Account(3, transactionManager);
        accountFirst.addCash(1000);
        accountFirst.withdraw(100, accountSecond);
        accountFirst.withdraw(200, accountSecond);
        accountFirst.withdraw(300, accountThird);
        analyticsManager = new AnalyticsManager(transactionManager);
    }

    @Test
    void mostFrequentBeneficiaryOfAccount_whenAccountIsNull() {
        assertThrows(IllegalArgumentException.class, () -> analyticsManager.mostFrequentBeneficiaryOfAccount(null));
    }

    @Test
    void mostFrequentBeneficiaryOfAccount_whenAccountIsValid() {
        // when
        Account account = analyticsManager.mostFrequentBeneficiaryOfAccount(accountFirst);
        boolean linksIsEqual = account == accountSecond;
        // then
        assertTrue(linksIsEqual);
        // when
        account = analyticsManager.mostFrequentBeneficiaryOfAccount(accountSecond);
        linksIsEqual = account == accountSecond;
        // then
        assertTrue(linksIsEqual);
        // when
        account = analyticsManager.mostFrequentBeneficiaryOfAccount(accountThird);
        linksIsEqual = account == accountThird;
        // then
        assertTrue(linksIsEqual);
    }

    @Test
    void topTenExpensivePurchases_whenAccountIsNull() {
        assertThrows(IllegalArgumentException.class, () -> analyticsManager.topTenExpensivePurchases(null));
    }

    @Test
    void topTenExpensivePurchases_whenAccountDontHavePurchases() {
        // when
        ArrayList<Transaction> resultList = new ArrayList<>(analyticsManager.topTenExpensivePurchases(accountSecond));
        int sizeOfResultList = resultList.size();
        // then
        assertEquals(0, sizeOfResultList);
    }

    @Test
    void topTenExpensivePurchases_whenAccountIsValid() {
        // when
        ArrayList<Transaction> resultList = new ArrayList<>(analyticsManager.topTenExpensivePurchases(accountFirst));
        int sizeOfResultList = resultList.size();
        double transaction1Amount = resultList.get(0).getAmount();
        double transaction2Amount = resultList.get(1).getAmount();
        double transaction3Amount = resultList.get(2).getAmount();
        // then
        assertEquals(3, sizeOfResultList);
        assertEquals(300, transaction1Amount);
        assertEquals(200, transaction2Amount);
        assertEquals(100, transaction3Amount);
    }
}