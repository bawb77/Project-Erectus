package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String inputPath = "C:\\Users\\Heavy Arms\\Documents\\GitHub\\Project-Erectus\\Raw Data\\RandomWalk";
        String outputPathLinear = "C:\\Users\\Heavy Arms\\Documents\\GitHub\\Project-Erectus\\RandomSample\\Walk\\Linear\\";
        String outputPathNonLinear = "C:\\Users\\Heavy Arms\\Documents\\GitHub\\Project-Erectus\\RandomSample\\Walk\\Non-Linear\\";
        String comma = ",";
        int fileNameStart = 0, fileNameEnd = 6;
        File source = new File(inputPath);
        File[] fList = source.listFiles();

        for (File file : fList) {

            ArrayList<String> input = new ArrayList<>();
            ArrayList<String[]> output = new ArrayList<>();
            ArrayList<double[]> finished = new ArrayList<>();

            BufferedReader inputFile = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = inputFile.readLine()) != null) {
                String[] values = line.split(comma);
                output.add(values);
            }
            inputFile.close();

            //get startTime to refine timestamp
            String[] temp1 = output.get(0);
            double startTime = Double.parseDouble(temp1[1]);

            //create array for holding filtered line entry


            //create linear marker values
            double maxX, minX, avgX, maxY, minY, avgY, maxZ, minZ, avgZ, numAcc0, numAcc1, numAcc2, numAcc3, numDT1, numDT3, numDT4, numDT9, numDT10, numDT15;
            maxX = minX = avgX = maxY = minY = avgY = maxZ = minZ = avgZ = numAcc0 = numAcc1 = numAcc2 = numAcc3 = numDT1 = numDT3 = numDT4 = numDT9 = numDT10 = numDT15 = 0.0;

            double[] nonLinearOutput = new double[19];
            int lineCount = 0;
            //iterate through all line entries
            for (String[] temp : output) {
                double[] calculated = new double[10];
                //modified start time
                calculated[0] = Double.parseDouble(temp[1]) - startTime;
                //raw x
                double temp2 = Double.parseDouble(temp[2]);
                if (temp2 > maxX) {
                    maxX = temp2;
                } else if (temp2 < minX) {
                    minX = temp2;
                }
                avgX += temp2;
                //pass raw data
                calculated[1] = temp2;
                //raw y
                double temp3 = Double.parseDouble(temp[3]);
                if (temp3 > maxY) {
                    maxY = temp3;
                } else if (temp3 < minY) {
                    minY = temp3;
                }
                avgY += temp3;
                //pass raw data
                calculated[2] = temp3;
                //raw z
                double temp4 = Double.parseDouble(temp[4]);
                if (temp4 > maxZ) {
                    maxZ = temp4;
                } else if (temp4 < minZ) {
                    minZ = temp4;
                }
                avgZ += temp4;
                //pass raw data
                calculated[3] = temp4;
                //accuracy
                double temp5 = Double.parseDouble(temp[5]);
                switch((int)temp5){
                    case 0:
                        numAcc0++;
                        break;
                    case 1:
                        numAcc1++;
                        break;
                    case 2:
                        numAcc2++;
                        break;
                    case 3:
                        numAcc3++;
                        break;
                }
                calculated[4] = temp5;
                //dataType
                double temp6 = Double.parseDouble(temp[7]);
                switch ((int)temp6){
                    case 1:
                        numDT1++;
                        break;
                    case 3:
                        numDT3++;
                        break;
                    case 4:
                        numDT4++;
                        break;
                    case 9:
                        numDT9++;
                        break;
                    case 10:
                        numDT10++;
                        break;
                    case 15:
                        numDT15++;
                        break;
                }
                calculated[5] = temp6;
                //time since last phase 1
                calculated[6] = 0.0;
                //time since last phase 2
                calculated[7] = 0.0;
                //time since last phase 3
                calculated[8] = 0.0;
                //time since last phase 4
                calculated[9] = 0.0;
                //further calculations or toroidal interference could be calculated here based on
                //comparison of radians and force
                lineCount++;
                finished.add(calculated);

            }
            nonLinearOutput[0] = maxX;
            nonLinearOutput[1] = minX;
            nonLinearOutput[2] = avgX/lineCount;
            nonLinearOutput[3] = maxY;
            nonLinearOutput[4] = minY;
            nonLinearOutput[5] = avgY/lineCount;
            nonLinearOutput[6] = maxZ;
            nonLinearOutput[7] = minZ;
            nonLinearOutput[8] = avgZ/lineCount;
            nonLinearOutput[9] = numAcc0;
            nonLinearOutput[10] = numAcc1;
            nonLinearOutput[11] = numAcc2;
            nonLinearOutput[12] = numAcc3;
            nonLinearOutput[13] = numDT1;
            nonLinearOutput[14] = numDT3;
            nonLinearOutput[15] = numDT4;
            nonLinearOutput[16] = numDT9;
            nonLinearOutput[17] = numDT10;
            nonLinearOutput[18] = numDT15;

            String fileNameNonLinear = outputPathNonLinear + (file.getName()).substring(fileNameStart,fileNameEnd) + "Linear.csv";
            FileWriter fwNonLinear = new FileWriter(fileNameNonLinear);
            BufferedWriter outputWriterNonLinear = new BufferedWriter(fwNonLinear);
            for (double out : nonLinearOutput)
            {
                outputWriterNonLinear.write(Double.toString(out));
                outputWriterNonLinear.write(comma);
            }
            outputWriterNonLinear.close();


            //parse data into files and write
            String fileNameLinear = outputPathLinear + (file.getName()).substring(fileNameStart,fileNameEnd) + "Filtered.csv";
            FileWriter fwLinear = new FileWriter(fileNameLinear);
            BufferedWriter outputWriterLinear = new BufferedWriter(fwLinear);
            for (double[] out2 : finished)
            {
                for (int i = 0 ; i < out2.length; i++)
                {
                    outputWriterLinear.write(Double.toString(out2[i]));
                    if (i != out2.length-1)
                        outputWriterLinear.write(comma);
                }
                outputWriterLinear.newLine();


            }
            outputWriterLinear.close();


        }
    }
}
