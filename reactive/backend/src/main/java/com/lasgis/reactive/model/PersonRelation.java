/*
 *  @(#)PersonRelation.java  last: 13.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model;

import com.lasgis.reactive.model.entity.PersonRelationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class PersonRelation definition.
 *
 * @author VLaskin
 * @since 12.09.2023 : 15:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PersonRelation {
    private Long personId;
    private Long personToId;
    private Person personTo;
    private PersonRelationType type;
}
