package ru.russianpost.tracking.portal.admin.validation.validators;

import static java.lang.System.currentTimeMillis;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.function.Consumer;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;

import ru.russianpost.tracking.portal.admin.model.operation.HistoryRecordId;

public class HistoryRecordIdValidatorTest {

    @Test
    public void emptyIndexOperShouldBeSuccess() {
        validateHistoryRecordId(createHistoryRecordId(1, ""), SUCCESS);
    }

    @Test
    public void nullIndexOperShouldBeSuccess() {
        validateHistoryRecordId(createHistoryRecordId(1, null), SUCCESS);
    }

    @Test
    public void sixDigitIndexOperShouldBeSuccess() {
        validateHistoryRecordId(createHistoryRecordId(1, "123456"), SUCCESS);
    }
    @Test
    public void sevenDigitIndexOperShouldBeSuccess() {
        validateHistoryRecordId(createHistoryRecordId(1, "1234567"), SUCCESS);
    }

    @Test
    public void sixCharsIndexOperShouldBeFailure() {
        validateHistoryRecordId(createHistoryRecordId(1, "1q2w3e"), FAILURE);
    }

    @Test
    public void nullOperAttrShouldBeSuccess() {
        validateHistoryRecordId(createHistoryRecordId(null, "123456"), SUCCESS);
    }

    private static final Consumer<Collection<?>> SUCCESS = collection -> assertThat(collection, empty());
    private static final Consumer<Collection<?>> FAILURE = collection -> assertThat(collection, not(empty()));

    private static HistoryRecordId createHistoryRecordId(final Integer operAttr, final String indexOper) {
        return HistoryRecordId.safeCreate("shipmentId", currentTimeMillis(), 1, operAttr, indexOper);
    }

    private static <T> void validateHistoryRecordId(final T id, final Consumer<Collection<?>> assertConsumer) {
        assertConsumer.accept(validator().validate(id));
    }

    private static Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

}
