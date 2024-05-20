package com.example.convoconvert.Service;

import com.example.convoconvert.Service.Interface.SpeachToTextInterface;
import org.springframework.stereotype.Service;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
@Service
public class SpeachToTextService implements SpeachToTextInterface {

    private String apiKey="P54CdayyiQYCFZ6NkGCFV6doXtk4v6pe";
    private static final String URL = "https://api.speechmatics.com/v2/jobs/";

    public String transcribe(File audioFile) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("audio/wav");
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("data_file", audioFile.getName(),
                        RequestBody.create(mediaType, audioFile))
                .addFormDataPart("transcription_config", "{\"language\": \"en\"}")
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body().string();
    }
}
