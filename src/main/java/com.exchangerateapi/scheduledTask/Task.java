package com.exchangerateapi.cronJob;


import java.net.URL;
import java.net.MalformedURLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import com.exchangerateapi.entity.FXData;
import com.exchangerateapi.repository.*;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;



@Component
public class Task {


    private final fxRepository fXdataRepository;

    public Task(fxRepository fXdataRepository) {
        this.fXdataRepository = fXdataRepository;
    }

    @Scheduled(fixedRate = 3600000)
    public void scheduleTaskWithCronExpression() throws MalformedURLException{
        RestClient restClient = RestClient.create();
        String[] sources = {"marketdata.tradermade.com", "api.freecurrencyapi.com"};

        for (String src : sources) {
            String[] fxTypes = {"USD", "EUR"};
            for (String fxt : fxTypes) {
                String urlparam = null;
                if (src.contains("marketdata")) {
                    urlparam = "API_KEY" + fxt + ;
                }
                if (src.contains("freecurrencyapi")) {
                    urlparam = "API_KEY" + fxt;
                }
                URL fxUrl = new URL("https", src, urlparam);
                String result = restClient.get()
                        .uri(fxUrl.toString())
                        .retrieve()
                        .body(String.class);
                JSONObject json = new JSONObject(result);
                FXData fXdata = new FXData();
                if (src.contains("marketdata")) {
                    fXdata.setFxSource(src);
                    fXdata.setFxType(json.getString("base_currency"));
                    fXdata.setFxPurchasePrice(json.getFloat("quote"));
                    fXdata.setFxSalePrice(json.getFloat("total"));
                }
                if (src.contains("freecurrencyapi")) {
                    fXdata.setFxSource(src);
                    fXdata.setFxType(fxt);
                    fXdata.setFxPurchasePrice(json.getJSONObject("data").getFloat("TRY"));
                    fXdata.setFxSalePrice(json.getJSONObject("data").getFloat("TRY"));
                }
                fXdataRepository.save(fXdata);
                System.out.println(fxUrl.toString());
                System.out.println(json.toString());
            }
        }
    }
}


