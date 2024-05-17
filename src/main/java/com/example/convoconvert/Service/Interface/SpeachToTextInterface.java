package com.example.convoconvert.Service.Interface;

import java.io.File;
import java.io.IOException;

public interface SpeachToTextInterface {
    public String transcribe(File audioFile) throws IOException;
}
