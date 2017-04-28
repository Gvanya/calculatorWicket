package org.gvanya.calculator;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class Calculator extends WebPage {
    /**
     * Constructor
     */
    public Calculator() {
        add(new Label("message", "Hello World!"));
    }
}