package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        File source = new File("C:\\Users\\Heavy Arms\\Documents\\GitHub\\Project-Erectus\\Raw Data\\SingleSample1");
        File[] fList = source.listFiles();

        for (File file : fList){

            ArrayList<String> input = new ArrayList<>();
            ArrayList<String[]> output = new ArrayList<>();
            ArrayList<double[]> finsihed = new ArrayList<>();
            BufferedReader inputFile = new BufferedReader(new FileReader(file));
            String line = null;

            while((line = inputFile.readLine()) != null)
            {
                String[] values = line.split(",");
                output.add(values);
            }
            inputFile.close();

            double[] calculated = new double[10];
            String[] temp1 = output.get(0);
            double starttime = Double.parseDouble(temp1[1]);
            for (String[] temp : output){
                //modified start time
                calculated[0] = Double.parseDouble(temp[1])-starttime;
                //raw x
                calculated[1] = Double.parseDouble(temp[2]);
                //raw y
                calculated[2] = Double.parseDouble(temp[3]);
                //raw z
                calculated[3] = Double.parseDouble(temp[4]);
                //accuracy
                calculated[4] = Double.parseDouble(temp[5]);
                //dataType
                calculated[5] = Double.parseDouble(temp[7]);
                //time since phase 1
                calculated[6] = 0.0;
                //time since phase 2
                calculated[7] = 0.0;
                //time since phase 3
                calculated[8] = 0.0;
                //time since phase 4
                calculated[9] = 0.0;
                //further calculations or toroidal interference could be calculated here based on
                //comparison of radians and force

            }

        }
}
