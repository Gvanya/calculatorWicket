package org.gvanya.calculator;

import org.apache.wicket.protocol.http.WebApplication;

public class CalculatorApplication extends WebApplication {
    /**
     * Constructor.
     */
    public CalculatorApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class getHomePage() {
        return Calculator.class;
    }
}