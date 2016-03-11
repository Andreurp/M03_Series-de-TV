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
import javafx.scene.image.Image;
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
	WebDriver navegador;

	// Event Listener on Button[#buscar].onMouseClicked
	@FXML
	public void buscarSeria(MouseEvent event) {

		String nom=nomSeria.getText();
		llistaTemporades.getItems().clear();
		
		navegador = new FirefoxDriver();
        navegador.navigate().to("http://seriesblanco.com");
        
        WebElement buscar = navegador.findElement(By.id("buscar-blanco"));
        buscar.sendKeys(nom);
        buscar.submit();
        
        WebElement contenidorSeries = navegador.findElement(By.className("post-header"));
		WebElement divisioSeria = contenidorSeries.findElement(By.cssSelector("div:nth-child(3)"));
		List<WebElement> series = divisioSeria.findElements(By.tagName("img"));

		if(series.size()>1 || series.size()==0) {
			navegador.close();
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Aletra:");
			alert.setHeaderText("Error, el nom de la seria que ha introduit no existeix o correspon a mes d'una sera.");
			alert.showAndWait();

		}else{
			WebElement serie = divisioSeria.findElement(By.xpath("./div[1]/div/a"));
			serie.click();

			WebDriverWait wait = new WebDriverWait(navegador, 30);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@id, 'post-body-')]")));

			for(WebElement temporades : navegador.findElements(By.xpath("//h2/u"))) {
				llistaTemporades.getItems().add(temporades.getText());
			}
			
			WebElement img = navegador.findElement(By.id("port_serie"));
	        String url = img.getAttribute("src");
	        System.out.println(url);
			Image imatge = new Image(url);
	        imgSeria.setImage(imatge);
		}
	}

	// Event Listener on ComboBox[#llistaTemporades].onAction
	@FXML
	public void triarTemporada(ActionEvent event) {
		
		int castella=0;
		int subtitulat=0;
		int Angles=0;
		
		llistaCapitols.getItems().clear();
		
		WebElement taula = navegador.findElement(By.xpath("//u[text='"+llistaTemporades.getItems()+"']/following-sibling::table"));
		
		List<WebElement> capitols = taula.findElements(By.xpath("./tbody/tr/td[1]/a"));
		List<WebElement> imgIdiomes = taula.findElements(By.xpath("./tbody/tr/td[2]/img"));
		 
		for(WebElement cap : capitols) {	
			String hrefCap = cap.getAttribute("href");
			String [] nomCap = hrefCap.split("/");
			llistaCapitols.getItems().add(nomCap[nomCap.length - 2]);
		}
		
		for(WebElement i : imgIdiomes){
			String idioma=i.getAttribute("src");
			
			if(idioma.contains("es.png")){
				castella++;
			}else if(idioma.contains("vos.png")){
				subtitulat++;
			}else if(idioma.contains("vo.png")){
				Angles++;
			}
		}
	}
}
