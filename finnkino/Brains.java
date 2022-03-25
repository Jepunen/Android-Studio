package com.example.finnkino;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Brains {

    ArrayList<Theatre> theatres = new ArrayList<Theatre>();
    ArrayList<Movie> movies = new ArrayList<Movie>();

    private static final Brains instance = new Brains();
    public static Brains getInstance() {return instance;}

    public Brains () {

    }

    public void downloadXML(String URL) {

        try {

            DocumentBuilder docB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDoc = docB.parse(URL);
            xmlDoc.getDocumentElement().normalize();

            NodeList locations = xmlDoc.getDocumentElement().getElementsByTagName("TheatreArea");

            for ( int i=0; i < locations.getLength(); i++ ) {
                Node node = locations.item(i);
                Element element = (Element) node;

                int id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                String name = element.getElementsByTagName("Name").item(0).getTextContent();

                theatres.add(new Theatre(id, name));

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public int getID(String name) {

        int ID = -1;

        for ( Theatre temp : theatres ) {
            if ( temp.getName() == name ) {
                ID = temp.getID();
                break;
            }
        }

        return ID;
    }

    public ArrayList getArray() {return theatres;}
    public ArrayList getArrayMovies() {return movies;}

    public void getMovies(int ID, int y, int m, int d) {

        movies.clear();

        String URL = String.format(Locale.getDefault(),"https://www.finnkino.fi/xml/Schedule/?area=%d&dt=%02d.%02d.%d", ID, d, m + 1, y);

        try {

            DocumentBuilder docB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDoc = docB.parse(URL);
            xmlDoc.getDocumentElement().normalize();

            NodeList shows = xmlDoc.getDocumentElement().getElementsByTagName("Show");

            for ( int i=0; i < shows.getLength(); i++ ) {
                Node node = shows.item(i);
                Element element = (Element) node;

                String name = element.getElementsByTagName("Title").item(0).getTextContent();
                String start = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();

                movies.add(new Movie(name, start));

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
}
