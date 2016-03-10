package net.andreu.SeriesTV;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

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
	private ListView llistaCapitols;
	@FXML
	private Label capCas;
	@FXML
	private Label capSub;
	@FXML
	private Label capAng;
	@FXML
	private ImageView imgSeria;
	@FXML
	private ComboBox llistaTemporades;

	// Event Listener on Button[#buscar].onMouseClicked
	@FXML
	public void buscarSeria(MouseEvent event) {

		String nom=nomSeria.getText();
		
		WebDriver navegador = new FirefoxDriver();
        navegador.navigate().to("http://seriesblanco.com/");
        
        WebElement buscar = navegador.findElement(By.id("buscar-blanco"));
        buscar.sendKeys(nom);
        buscar.submit();
        
        WebElement boxSeries = navegador.findElement(By.cssSelector("div.date-outer div.post-header"));
		WebElement divSeries = boxSeries.findElement(By.cssSelector("div:nth-child(3)"));
		
		List<WebElement> series = divSeries.findElements(By.tagName("img"));
		
		if(series.size()>1 || series.size()==0){
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Aletra:");
			alert.setHeaderText("Error en buscar la seria.");
			alert.showAndWait();
			
			navegador.close();
			
		}else{
			
			WebElement serie = divSeries.findElement(By.xpath("./div[1]/div/a"));
			serie.click();
			
			WebElement img = navegador.findElement(By.id("port_serie"));
	        String imatge = img.getAttribute("src");
	        img.sendKeys(imatge);
			
			/*WebElement boxTemp = navegador.findElement(By.xpath("//*[contains(@id, 'post-body-')]"));
			List<WebElement> temp = boxTemp.findElements(By.tagName("h2"));
			for(WebElement t : temp){
				temporadas.getItems().add(t.getText());
			}*/
		}
	}
		
	// Event Listener on ListView[#llistaCapitols].onMouseClicked
	@FXML
	public void triarCapitol(MouseEvent event) {
		// TODO Autogenerated
	}
	
	// Event Listener on ComboBox[#llistaTemporades].onAction
	@FXML
	public void triarTemporada(ActionEvent event) {
		// TODO Autogenerated
	}
}
