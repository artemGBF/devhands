package ru.gbf.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import ru.gbf.dto.ChartDTO;

@Controller("/api/graph")
public class GraphController {

    @Get
    public MutableHttpResponse<ChartDTO> build() {
        DecimalFormat df = new DecimalFormat("#.##");
        File file = new File("data");
        File[] files = file.listFiles();
        ChartDTO chart = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(files[0]))) {
            chart = new ChartDTO(files[0].getName());
            String line = reader.readLine();
            while (!line.contains("1/(1-Percentile)")) {
                line = reader.readLine();
            }
            line = reader.readLine();
            line = reader.readLine();
            while (!line.contains("#[Mean")) {
                String[] parts = line.split(" ");
                line = reader.readLine();
                double X;
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
                chart.add(Double.parseDouble(df.format(X * 100)), Y);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return HttpResponse.ok(chart).contentType(MediaType.APPLICATION_JSON);
    }
}
