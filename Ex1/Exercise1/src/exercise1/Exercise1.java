/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.*;

/**
 *
 * @author TQM
 */
public class Exercise1 {

    private static String USER_AGENT = "Mozilla/5.0";
    private static String googleAPI = "AIzaSyAKDJNWAvaDE3QjKfX1Jkd4azMnpr9K8z0";
    static Scanner reader = new Scanner(System.in);  // Reading from System.in

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        System.out.println("Hello!");
        askUserToChooseExercise();

        reader.close();
    }

    private static void askUserToChooseExercise() throws IOException {
        boolean intFound = false;
        while (!intFound) {
            System.out.println("Enter number:\n 1. To find address for given coordinate(exercise 1) \n 2. To calculate distance from 2 coordinates(exercise 2) ");
            if (reader.hasNextInt()) {
                intFound = true;
                int option = reader.nextInt();
                if (option == 1) {
                    receiveEx1CoordinateFromUserInput();
                } else if (option == 2) {
                    receiveEx2LocationsFromUserInput();
                } else {
                    System.out.println("Please enter only 1 or 2");
                    reader.next();
                }
            } else {
                System.out.println("Invalid input. Please enter an integer!");
                reader.next();
            }

        }
    }

    private static void receiveEx1CoordinateFromUserInput() {
        double longitude = receiveLongitudeFromUserInput();
        double latitude = receiveLatitudeFromUserInput();
        try {
            generateAddress(longitude, latitude);
        } catch (IOException ex) {
            Logger.getLogger(Exercise1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static double receiveLongitudeFromUserInput() {
        boolean doubleFound = false;
        while (!doubleFound) {
            System.out.println("Enter longitude: ");
            if (reader.hasNextDouble() || reader.hasNextInt()) {
                doubleFound = true;
                double longitude = reader.nextDouble();
                return longitude;
            } else {
                System.out.println("Please enter the correct type for longitude!");
                reader.next();
            }
        }
        return 0.0;
    }

    private static double receiveLatitudeFromUserInput() {
        boolean doubleFound = false;
        System.out.println("Enter latitude: ");
        while (!doubleFound) {
            if (reader.hasNextDouble() || reader.hasNextInt()) {
                doubleFound = true;
                double latitude = reader.nextDouble();
                return latitude;
            } else {
                System.out.println("Please enter the correct type for longitude!");
                reader.next();
            }
        }
        return 0.0;
    }

    private static void generateDistance(double sourceLongitude, double sourceLatitude, double destLongitude, double destLatitude) throws IOException {
        String url = "https://mobile-dev-subject-exercise1.herokuapp.com/users?lat1=" + sourceLatitude + "&lon1=" + sourceLongitude + "&lat2=" + destLatitude + "&lon2=" + destLongitude;
        StringBuffer response = getDataFromApi(url);
        try {
            JSONObject jsonObj = new JSONObject(response.toString());
            double distance = jsonObj.getDouble("distance");
            if (distance != 0) {
                System.out.printf("Distance: %.2f km \n", distance);
            } else {
                System.out.println("Could not calculate distance!");
            }

        } catch (JSONException ex) {
            Logger.getLogger(Exercise1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void generateAddress(double longitude, double latitude) throws IOException {

        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=" + googleAPI;

        StringBuffer response = getDataFromApi(url);
        try {
            JSONObject jsonObj = new JSONObject(response.toString());

            JSONArray results = jsonObj.getJSONArray("results");
            if (results.length() == 0) {
                System.out.print("Could not find the address!");
            } else {
                String address = results.getJSONObject(0).getString("formatted_address");
                System.out.println("Address: " + address);
            }

        } catch (JSONException ex) {
            Logger.getLogger(Exercise1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static StringBuffer getDataFromApi(String url) throws MalformedURLException, IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println(response.toString());
        return response;
    }

    private static void receiveEx2LocationsFromUserInput() throws IOException {
        System.out.println("PLEASE ENTER SOURCE COORDINATE");
        double lon1 = receiveLongitudeFromUserInput();
        double lat1 = receiveLatitudeFromUserInput();
        System.out.println("PLEASE ENTER DESTINATION COORDINATE");
        double lon2 = receiveLongitudeFromUserInput();
        double lat2 = receiveLatitudeFromUserInput();
        generateDistance(lon1, lat1, lon2, lat2);
    }
}
