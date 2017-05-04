package org.gvanya.calculator;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Common Apache Wicket loader class.
 * 
 * @author Gerasymchuk Ivan
 *
 */
public class CalculatorApplication extends WebApplication {
    /**
     * Constructor.
     */
    public CalculatorApplication() {
    }

    /**
     * @return {@code Calculator.class} as homepage processor. 
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return Calculator.class;
    }
}