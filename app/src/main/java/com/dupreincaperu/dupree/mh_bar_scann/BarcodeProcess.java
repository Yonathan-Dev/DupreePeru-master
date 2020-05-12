/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dupreincaperu.dupree.mh_bar_scann;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Activity for the multi-tracker app.  This app detects barcodes and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and ID of each barcode.
 */
public final class BarcodeProcess {

    private static final String TAG = "Barcode-reader";

    public InfoTarjeta parseDataCode(String cadenaCedula) {
        Log.e(TAG, "Ingresa a captura de datos");
        InfoTarjeta infoTarjeta = null;
        if (cadenaCedula != null) {

            infoTarjeta = new InfoTarjeta();
            String primerApellido = "", segundoApellido = "", primerNombre = "", segundoNombre = "", cedula = "", rh = "", fechaNacimiento = "", sexo = "";

            String alphaAndDigits = cadenaCedula.replaceAll("[^\\p{Alpha}\\p{Digit}\\+\\_]+", " ");
            String[] splitStr = alphaAndDigits.split("\\s+");
            /*
            for (int i=0; i<splitStr.length;i++){
                Log.d(TAG, i + "valor: " + splitStr[i]);
            }
            */
            if (!alphaAndDigits.contains("PubDSK")) {
                int corrimiento = 0;


                Pattern pat = Pattern.compile("[A-Z]");
                Matcher match = pat.matcher(splitStr[2 + corrimiento]);
                int lastCapitalIndex = -1;
                if (match.find()) {
                    lastCapitalIndex = match.start();
                    Log.d(TAG, "match.start: " + match.start());
                    Log.d(TAG, "match.end: " + match.end());
                    Log.d(TAG, "splitStr: " + splitStr[2 + corrimiento]);
                    Log.d(TAG, "splitStr length: " + splitStr[2 + corrimiento].length());
                    Log.d(TAG, "lastCapitalIndex: " + lastCapitalIndex);
                }
                cedula = splitStr[2 + corrimiento].substring(lastCapitalIndex - 10, lastCapitalIndex);
                primerApellido = splitStr[2 + corrimiento].substring(lastCapitalIndex);
                segundoApellido = splitStr[3 + corrimiento];
                primerNombre = splitStr[4 + corrimiento];
                /**
                 * Se verifica que contenga segundo nombre
                 */
                if (Character.isDigit(splitStr[5 + corrimiento].charAt(0))) {
                    corrimiento--;
                } else {
                    segundoNombre = splitStr[5 + corrimiento];
                }

                sexo = splitStr[6 + corrimiento].contains("M") ? "Masculino" : "Femenino";
                rh = splitStr[6 + corrimiento].substring(splitStr[6 + corrimiento].length() - 2);
                fechaNacimiento = splitStr[6 + corrimiento].substring(2, 10);

            } else {
                int corrimiento = 0;
                Pattern pat = Pattern.compile("[A-Z]");
                if (splitStr[2 + corrimiento].length() > 7) {
                    corrimiento--;
                }


                Matcher match = pat.matcher(splitStr[3 + corrimiento]);
                int lastCapitalIndex = -1;
                if (match.find()) {
                    lastCapitalIndex = match.start();

                }

                cedula = splitStr[3 + corrimiento].substring(lastCapitalIndex - 10, lastCapitalIndex);
                primerApellido = splitStr[3 + corrimiento].substring(lastCapitalIndex);
                segundoApellido = splitStr[4 + corrimiento];
                primerNombre = splitStr[5 + corrimiento];
                segundoNombre = splitStr[6 + corrimiento];
                sexo = splitStr[7 + corrimiento].contains("M") ? "Masculino" : "Femenino";
                rh = splitStr[7 + corrimiento].substring(splitStr[7 + corrimiento].length() - 2);
                fechaNacimiento = splitStr[7 + corrimiento].substring(2, 10);

            }
            /**
             * Se setea el objeto con los datos
             */
            infoTarjeta.setPrimerNombre(primerNombre);
            infoTarjeta.setSegundoNombre(segundoNombre);
            infoTarjeta.setPrimerApellido(primerApellido);
            infoTarjeta.setSegundoApellido(segundoApellido);
            infoTarjeta.setCedula(cedula);
            infoTarjeta.setSexo(sexo);
            infoTarjeta.setFechaNacimiento(fechaNacimiento);
            infoTarjeta.setRh(rh);


        } else {
            Log.d(TAG, "No barcode capturado");
            return infoTarjeta;
        }

        return infoTarjeta;
    }

}















