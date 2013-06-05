package com.comsysto.playground.ui.page.home;

import com.comsysto.playground.ui.page.AbstractBasePage;
import com.comsysto.playground.ui.panel.MovieListPanel;
import org.apache.wicket.Component;

public class HomePage extends AbstractBasePage {


    public HomePage() {
        super();
        setOutputMarkupId(true);

        add(movieListPanel());
    }

    private Component movieListPanel() {
        return new MovieListPanel("movieListPanel");
    }

}
