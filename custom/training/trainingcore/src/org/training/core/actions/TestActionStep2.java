package org.training.core.actions;

import de.hybris.platform.commerceservices.model.process.TestActionProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import org.apache.log4j.Logger;

public class TestActionStep2 extends AbstractSimpleDecisionAction<TestActionProcessModel> {

    public static final Logger LOGGER = Logger.getLogger(TestActionStep2.class);
    @Override
    public Transition executeAction(TestActionProcessModel testActionProcessModel) {
        //Do Something Here

//        If everything goes well return OK, else return NOK
        if(Boolean.TRUE) {
            LOGGER.info("Test Action Step 2 OK!");
            return Transition.OK;
        }
        else return Transition.NOK;
    }
}
