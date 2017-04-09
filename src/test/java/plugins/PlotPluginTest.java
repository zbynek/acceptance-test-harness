package plugins;

import org.jenkinsci.test.acceptance.junit.AbstractJUnitTest;
import org.jenkinsci.test.acceptance.plugins.plot.CsvDataSeries;
import org.jenkinsci.test.acceptance.plugins.plot.Plot;
import org.jenkinsci.test.acceptance.po.JenkinsLogger;
import org.jenkinsci.test.acceptance.po.PageObject;
import org.jvnet.hudson.test.Issue;
import org.jenkinsci.test.acceptance.junit.WithPlugins;
import org.jenkinsci.test.acceptance.plugins.plot.PlotPublisher;
import org.jenkinsci.test.acceptance.po.FreeStyleJob;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

@WithPlugins({
        "matrix-project", // JENKINS-37545
        "plot"
})
public class PlotPluginTest extends AbstractJUnitTest {

    private FreeStyleJob job;

    @Before
    public void setUp() {
        job = jenkins.jobs.create();
    }


    @Test
    public void generate_simple_plot(){
        job.configure();
        job.copyResource(resource("/plot_plugin/plot.csv"));
        PlotPublisher pub = job.addPublisher(PlotPublisher.class);

        pub.getPlot(1).setGroup("My group");
        pub.getPlot(1).setTitle("My plot");

        pub.getPlot(1).addDataSeries(CsvDataSeries.class);
        pub.getPlot(1).getSeries(1).setFile("plot.csv");

        Plot p2 = pub.addPlot();
        p2.setGroup("My group2");
        p2.setTitle("My plot2");

        p2.addDataSeries(CsvDataSeries.class);
        p2.getSeries(1).setFile("plot2.csv");

        System.out.println("stop");

    }

/*
    @Test
    public void generate_simple_plot() {
        job.configure();
        job.copyResource(resource("/plot_plugin/plot.csv"));
        PlotPublisher pub = job.addPublisher(PlotPublisher.class);

        pub.group.set("My group");
        pub.title.set("My plot");

        pub.source("csv", "plot.csv");
        job.save();

        job.startBuild().shouldSucceed();
        assertThatBuildHasPlot("My plot","My group");
    }

    @Test @Issue({"JENKINS-18585","JENKINS-18674"})
    public void postbuild_rendering_should_work() {
        job.configure();
        PlotPublisher pub = job.addPublisher(PlotPublisher.class);

        pub.usePlot(1).

        pub.group.set("Plots");
        pub.title.set("Some plot");
        job.save();

        job.configure();
        pub.source("csv", "plot.csv");
        job.save();

        job.startBuild().shouldSucceed();
        assertThatBuildHasPlot("Some plot","Plots");
    }

    @Test
    public void no_exception_visit_plot_page(){
        job.configure();
        job.copyResource(resource("/plot_plugin/plot.csv"));
        PlotPublisher pub = job.addPublisher(PlotPublisher.class);
        pub.group.set("My group");
        pub.title.set("My plot");

        pub.source("csv", "plot.csv");
        job.save();

        job.startBuild().shouldSucceed();
        job.visit("plot");

        JenkinsLogger jLog = job.getJenkins().getLogger("all");

        Pattern p = Pattern.compile(".*java.lang.NumberFormatException.*", Pattern.DOTALL);

        assertTrue(jLog.hasLogged(p));

    }


    private void assertThatBuildHasPlot(String title, String group) {
        job.visit("plot");
        find(by.xpath("//h1[contains(text(), '%s')]", group));
        find(by.xpath("//select[@name='choice']/option[contains(text(), '%s')]",title));
    }
*/

}
