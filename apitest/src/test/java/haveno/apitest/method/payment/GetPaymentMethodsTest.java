package haveno.apitest.method.payment;

import haveno.apitest.method.MethodTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import protobuf.PaymentMethod;

import java.util.List;
import java.util.stream.Collectors;

import static haveno.apitest.Scaffold.BitcoinCoreApp.bitcoind;
import static haveno.apitest.config.HavenoAppConfig.alicedaemon;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetPaymentMethodsTest extends MethodTest {

    @BeforeAll
    public static void setUp() {
        try {
            setUpScaffold(bitcoind, alicedaemon);
        } catch (Exception ex) {
            fail(ex);
        }
    }

    @Test
    @Order(1)
    public void testGetPaymentMethods() {
        List<String> paymentMethodIds = aliceClient.getPaymentMethods()
                .stream()
                .map(PaymentMethod::getId)
                .collect(Collectors.toList());

        // Verify count: all 56 active payment methods now returned
        assertTrue(paymentMethodIds.size() >= 56,
                "Expected >= 56 payment methods, got " + paymentMethodIds.size());

        // Per-ID assertions — must contain every active PaymentMethod
        List<String> expectedIds = List.of(
                "ACH_TRANSFER",
                "ADVANCED_CASH",
                "ALI_PAY",
                "AMAZON_GIFT_CARD",
                "AUSTRALIA_PAYID",
                "BIZUM",
                "BLOCK_CHAINS",
                "BLOCK_CHAINS_INSTANT",
                "CAPITUAL",
                "CASH_APP",
                "CASH_AT_ATM",
                "CASH_DEPOSIT",
                "CELPAY",
                "DOMESTIC_WIRE_TRANSFER",
                "F2F",
                "FASTER_PAYMENTS",
                "HAL_CASH",
                "IMPS",
                "INTERAC_E_TRANSFER",
                "JAPAN_BANK",
                "MONESE",
                "MONEY_BEAM",
                "MONEY_GRAM",
                "NATIONAL_BANK",
                "NEFT",
                "NEQUI",
                "PAXUM",
                "PAY_BY_MAIL",
                "PAYPAL",
                "PAYSAFE",
                "PAYSERA",
                "PAYTM",
                "PERFECT_MONEY",
                "PIX",
                "POPMONEY",
                "PROMPT_PAY",
                "REVOLUT",
                "RTGS",
                "SAME_BANK",
                "SATISPAY",
                "SEPA",
                "SEPA_INSTANT",
                "SPECIFIC_BANKS",
                "STRIKE",
                "SWIFT",
                "SWISH",
                "TIKKIE",
                "TRANSFERWISE",
                "TRANSFERWISE_USD",
                "UPHOLD",
                "UPI",
                "US_POSTAL_MONEY_ORDER",
                "VENMO",
                "VERSE",
                "WECHAT_PAY",
                "WESTERN_UNION",
                "ZELLE"
        );

        assertEquals(expectedIds.size(), paymentMethodIds.size(),
                "Payment method count mismatch");
        for (String id : expectedIds) {
            assertTrue(paymentMethodIds.contains(id), "Missing payment method: " + id);
        }
    }

    @AfterAll
    public static void tearDown() {
        tearDownScaffold();
    }
}
