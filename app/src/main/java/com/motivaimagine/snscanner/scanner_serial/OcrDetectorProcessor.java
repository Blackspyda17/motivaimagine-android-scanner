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
package com.motivaimagine.snscanner.scanner_serial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.motivaimagine.snscanner.scanner_serial.camera.GraphicOverlay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {
    private static final String SERIAL = "serial";
    private static final String VC = "vc";
    private boolean side_l = false;
    private boolean side_r = false;
    private String serial;


    private GraphicOverlay<OcrGraphic> mGraphicOverlay;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, char side_c) {
        mGraphicOverlay = ocrGraphicOverlay;
        if (side_c == 'l') {
            side_l = true;
        } else {
            side_r = true;
        }
    }


    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {
                // Log.d("OcrDetectorProcessor", "Text detected! " + item.getValue());
                if (check_serial(item.getValue().trim())) {
                    OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
                    mGraphicOverlay.add(graphic);
                    Vibrator v = (Vibrator) mGraphicOverlay.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    setEDT();
                }
            }

        }
    }

    private boolean check_serial(String text) {
        if (text.length() >= 13) {
            if (text.contains("SN") && text.contains("-")) {
                int reference = text.indexOf('-');
                if (text.charAt((reference - 8)) == '1') {
                    try {
                        String part_1 = text.substring((reference - 8), reference);
                        String part_2 = text.substring((reference + 1), (reference + 3));
                        if (isNumeric(part_1) && isNumeric(part_2)) {
                            String serial_N = part_1 + "-" + part_2;
                            if (serial_N.length() == 11) {
                                Log.d("OcrDetectorProcessor", "Esteee es el seriaaaaaaaaaaallll intento papiiiiiiiiiiiiii! " + serial_N);
                                setSerial(serial_N);
                                return true;
                            }
                        }

                    } catch (IndexOutOfBoundsException ex) {

                    }

                }
            }

        }
        return false;
    }

    private void setEDT() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(SERIAL, getSerial());
        resultIntent.putExtra(VC, vc_code(md5(getSerial())));
        ((Activity) mGraphicOverlay.getContext()).setResult(Activity.RESULT_OK, resultIntent);
        ((Activity) mGraphicOverlay.getContext()).finish();

    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String vc_code(String hash) {
        String vc = hash.substring((hash.length() - 1));
        if (isNumeric(vc)) {
            return vc;
        }
        return vc.toUpperCase();
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
