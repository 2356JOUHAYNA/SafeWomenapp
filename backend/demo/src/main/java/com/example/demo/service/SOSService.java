package com.example.demo.service;

import com.example.demo.model.SOS;
import com.example.demo.repository.SOSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class SOSService {

    @Autowired
    private SOSRepository sosRepository;

    public SOS saveSOS(double latitude, double longitude, MultipartFile audioFile) throws IOException {

        String audioFilePath = null;

        // Save the audio file if provided
        if (audioFile != null && !audioFile.isEmpty()) {
            String fileName = "audio_" + System.currentTimeMillis() + ".3gp";
            File targetFile = new File("uploads/" + fileName);
            audioFile.transferTo(targetFile);
            audioFilePath = targetFile.getAbsolutePath();
        }

        SOS sos = new SOS();
        sos.setLatitude(latitude);
        sos.setLongitude(longitude);
        sos.setAudioFilePath(audioFilePath);
        sos.setTimestamp(new Date());

        return sosRepository.save(sos);
    }
}
