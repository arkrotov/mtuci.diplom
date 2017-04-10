package ru.krotov.classifiers.weka.threeway;

import info.semanticanalyzer.classifiers.weka.SentimentClass;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by dmitrykan on 27.04.2014.
 */
public class KaggleCSVWriterThreeWay {
    public static final String CSV_HEADER = "PhraseId,Sentiment";
    BufferedWriter bw;

    public KaggleCSVWriterThreeWay(String csvFile) throws IOException {
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "utf8"));
        bw.write(CSV_HEADER);
        bw.write("\n");
    }

    public void writeKaggleCSV(KaggleCSVReaderThreeWay.CSVInstanceThreeWay csvInstanceThreeWay, SentimentClass.FiveWayClazz sentiment) throws IOException {
        try {
            bw.write(String.valueOf(csvInstanceThreeWay.phraseID));
            bw.write(",");
            bw.write(String.valueOf(sentiment.ordinal()));
            bw.write("\n");
        } catch (IOException e) {
            close();
            throw e;
        }
    }

    public void close() throws IOException {
        bw.close();
    }

}
