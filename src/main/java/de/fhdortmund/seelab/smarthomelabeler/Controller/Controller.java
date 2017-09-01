package de.fhdortmund.seelab.smarthomelabeler.Controller;

import com.google.gson.Gson;
import de.fhdortmund.seelab.smarthomelabeler.PDFCreator;
import de.fhdortmund.seelab.smarthomelabeler.Smarthomeitem;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hildan.fxgson.FxGson;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


/**
 * Created by jonas on 14.08.17.
 */
public class Controller {
    @FXML
    TableView itemView;
    @FXML
    TextField txtUrl;


    @FXML
    public void initialize() {
        System.out.println("Test");
        itemView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void onLoadAction() {
        itemView.getItems().clear();
        try {
            itemView.getItems().addAll(fetchItemsFromBaseUrl(txtUrl.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGenerateAction() {
        try {
            File pdfPath = new File(System.getProperty("java.io.tmpdir") + "/smarthomeLabels.pdf");
            for(Object obj : itemView.getSelectionModel().getSelectedItems()) {
                System.out.println(obj.getClass().getName());
            }
            Object[] objItems = itemView.getSelectionModel().getSelectedItems().toArray();
            PDFCreator.createPDF(pdfPath, Arrays.copyOf(objItems, objItems.length, Smarthomeitem[].class));
            Desktop.getDesktop().open(pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Smarthomeitem[] fetchItemsFromBaseUrl(String base) throws IOException {
        String url = base + "/rest/items";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result);
        Gson gson = FxGson.create();
        Smarthomeitem[] items = gson.fromJson(result.toString(), Smarthomeitem[].class);
        return items;
    }

}
