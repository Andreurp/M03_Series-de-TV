package net.andreu.SeriesTV;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

public class SeriesTVController {
	@FXML
	private TextField nomSeria;
	@FXML
	private Button buscar;
	@FXML
	private ListView<String> llistaCapitols;
	@FXML
	private Label capCas;
	@FXML
	private Label capSub;
	@FXML
	private Label capAng;
	@FXML
	private ImageView imgSeria;
	@FXML
	private ComboBox<String> llistaTemporades;

	// Event Listener on Button[#buscar].onMouseClicked
	@FXML
	public void buscarSeria(MouseEvent event) {

		String nom=nomSeria.getText();
		llistaTemporades.getItems().clear();
		
		WebDriver navegador = new FirefoxDriver();
        navegador.navigate().to("http://seriesblanco.com");
        
        WebElement buscar = navegador.findElement(By.id("buscar-blanco"));
        buscar.sendKeys(nom);
        buscar.submit();
        
        WebElement boxSeries = navegador.findElement(By.className("post-header"));
		WebElement divSeries = boxSeries.findElement(By.cssSelector("div:nth-child(3)"));
		List<WebElement> series = divSeries.findElements(By.tagName("img"));

		if (series.size() > 1 || series.size() == 0) {

			navegador.close();
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Aletra:");
			alert.setHeaderText("Error, el nom de la seria que ha introduit no existeix o correspon a mes d'una sera.");
			alert.showAndWait();

		}else{

			WebElement serie = divSeries.findElement(By.xpath("./div[1]/div/a"));
			serie.click();

			WebDriverWait wait = new WebDriverWait(navegador, 30);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'post-body-')]")));

			for (WebElement temporades : navegador.findElements(By.xpath("//h2/u"))) {
				llistaTemporades.getItems().add(temporades.getText());
			}
			
			/*WebElement img = navegador.findElement(By.id("port_serie"));
	        //String imatge = img.getAttribute("src");
			Image imatge = new Image(img.getAttribute("src"));
	        imgSeria.setImage(imatge);*/
		}
	}

	// Event Listener on ComboBox[#llistaTemporades].onAction
	@FXML
	public void triarTemporada(ActionEvent event) {
		
		int castella=0;
		int subtitulat=0;
		int Angles=0;
		
		llistaCapitols.getItems().clear();
	}
}
