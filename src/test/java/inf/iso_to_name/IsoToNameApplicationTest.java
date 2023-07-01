package inf.iso_to_name;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class IsoToNameApplicationTest {
    @Test
    void contextLoads() {
        // Prüft, ob die Anwendung ohne Fehler gestartet werden kann
        IsoToNameApplication.main(new String[]{});
    }
}

