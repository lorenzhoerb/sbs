import com.hoerb.exception.InsufficientFundsException;
import com.hoerb.model.Depot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepotTest {

    @Test
    void testWithdraw_shouldReduceBalance_whenSufficientFundsAvailable() {
        Depot d = new Depot(10000);
        d.withdraw(5000);
        assertEquals(5000, d.getBalance());
    }

    @Test
    void testWithdraw_shouldZeroBalance_whenWithdrawingEverything() {
        Depot d = new Depot(100);
        d.withdraw(100);
        assertEquals(0, d.getBalance());
    }

    @Test
    void testWithdraw_shouldThrowInsufficientFunds_whenInsufficientFundsAvailable() {
        Depot d = new Depot(0.01);
        assertThrows(InsufficientFundsException.class, () -> d.withdraw(0.02));
    }
}
