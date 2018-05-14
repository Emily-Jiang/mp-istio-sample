// tag::comment[]
/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::comment[]
// tag::HealthTest[]
package it;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import javax.json.JsonArray;
import org.junit.After;
import org.junit.Test;

public class HealthTest {

    private JsonArray servicesStates;
    private static HashMap<String, String> dataWhenServiceAUP;
    private static HashMap<String, String> dataWhenInventoryDown;

    static {
        dataWhenServiceAUP = new HashMap<String, String>();

        dataWhenServiceAUP.put("serviceA", "DOWN");
    }

    @Test
    public void testIfServicesAreUp() {
        servicesStates = HealthTestUtil.connectToHealthEnpoint(503);
        checkStates(dataWhenServiceAUP, servicesStates);
    }

    //@Test
    public void testIfInventoryServiceIsDown() {
        servicesStates = HealthTestUtil.connectToHealthEnpoint(200);
        checkStates(dataWhenServiceAUP, servicesStates);
        HealthTestUtil.changeInventoryProperty(HealthTestUtil.INV_MAINTENANCE_FALSE, 
                                               HealthTestUtil.INV_MAINTENANCE_TRUE);
        servicesStates = HealthTestUtil.connectToHealthEnpoint(503);
        checkStates(dataWhenInventoryDown, servicesStates);
    }

    private void checkStates(HashMap<String, String> testData, JsonArray servStates) {
        testData.forEach((service, expectedState) -> {
            assertEquals("The state of " + service + " service is not matching.", 
                         expectedState, 
                         HealthTestUtil.getActualState(service, servStates));
        });
    }

    @After
    public void teardown() {
//        HealthTestUtil.cleanUp();
    }

}
// end::HealthTest[]
