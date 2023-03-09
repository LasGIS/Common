package ru.russianpost.tracking.portal.admin.model.operation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HistoryRecordIdTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testDeserialization() throws Exception {
        HistoryRecordId id = from("{\"operAttr\":null,\"indexOper\":null}");
        assertEquals(HistoryRecordId.NULL_OPER_ATTR_VALUE, id.getOperAttr());
        assertEquals(HistoryRecordId.NULL_INDEX_OPER_VALUE, id.getIndexOper());
    }

    private HistoryRecordId from(String json) throws Exception {
        return mapper.readValue(json, HistoryRecordId.class);
    }

}
