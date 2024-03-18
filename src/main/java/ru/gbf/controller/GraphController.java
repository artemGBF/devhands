package ru.gbf.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

@Controller("/api/graph")
public class GraphController {

    @Post("/{title}")
    public void build(@PathVariable String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line = reader.readLine();
            while (!line.contains("1/(1-Percentile)")) {
                line = reader.readLine();
            }
            line = reader.readLine();
            line = reader.readLine();
            while (!line.contains("#[Mean")) {
                String[] parts = line.split(" ");
                line = reader.readLine();
                double X = 0;
                double Y = 0D;
                int count = 0, i = 0;
                while (true) {
                    if (parts[i].equals("")) {
                        i++;
                    } else {
                        if (count == 0) {
                            Y = Double.parseDouble(parts[i]);
                            i++;
                            count++;
                        } else {
                            X = Double.parseDouble(parts[i]);
                            break;
                        }
                    }
                }
                double finalY = Y;
                dataset.setValue(new Number() {
                    @Override
                    public int intValue() {
                        return 0;
                    }

                    @Override
                    public long longValue() {
                        return 0;
                    }

                    @Override
                    public float floatValue() {
                        return 0;
                    }

                    @Override
                    public double doubleValue() {
                        return finalY;
                    }
                }, "Latency,ms", (int)(X * 100));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JFreeChart chart = ChartFactory.createLineChart(
            title,
            "Percentile",
            "Latency,ms",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        JFrame frame = new JFrame("Throughput diagram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        ChartPanel chartPanel = new ChartPanel(chart);
        frame.add(chartPanel);

        frame.setVisible(true);
    }
}
