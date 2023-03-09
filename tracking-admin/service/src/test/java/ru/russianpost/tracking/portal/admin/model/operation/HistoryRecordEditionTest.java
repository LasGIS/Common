package ru.russianpost.tracking.portal.admin.model.operation;

import org.junit.Test;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordEdition;

import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigInteger;

import static java.lang.System.currentTimeMillis;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;

public class HistoryRecordEditionTest {

    final Validator validator = validator();

    @Test
    public void validateIndexToShouldBeSuccess() {
        assertThat(this.validator.validateProperty(createHistoryRecordEdition(null), "indexTo"), empty());
        assertThat(this.validator.validateProperty(createHistoryRecordEdition("123456"), "indexTo"), empty());
        assertThat(this.validator.validateProperty(createHistoryRecordEdition(""), "indexTo"), empty());
    }

    @Test
    public void validateIndexToShouldBeFailure() {
        assertThat(this.validator.validateProperty(createHistoryRecordEdition("1234"), "indexTo"), hasSize(1));
        assertThat(this.validator.validateProperty(createHistoryRecordEdition("qwerty"), "indexTo"), hasSize(1));
        assertThat(this.validator.validateProperty(createHistoryRecordEdition("1qaz2wsx"), "indexTo"), hasSize(1));
    }

    private HistoryRecordEdition createHistoryRecordEdition(final String indexTo) {
        final String indexOper = "2";
        return new HistoryRecordEdition(
            createHistoryRecordId(1, indexOper),
            1234567890L,
            indexOper,
            1234567890,
            indexTo,
            "initiator",
            "comment",
            "rcpn",
            "sndr",
            new Money(BigInteger.valueOf(10L), "USD"),
            new Money(BigInteger.valueOf(15L), "USD"),
            1,
            1
        );
    }

    private static HistoryRecordId createHistoryRecordId(final Integer operAttr, final String indexOper) {
        return HistoryRecordId.safeCreate("shipmentId", currentTimeMillis(), 1, operAttr, indexOper);
    }

    private static Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
