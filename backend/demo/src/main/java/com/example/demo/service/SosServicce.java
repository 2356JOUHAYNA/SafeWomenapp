package com.example.demo.service;




import com.example.demo.model.SosAlert;
import com.example.demo.repository.SosAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class SosServicce {

    private final SosAlertRepository repository;
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/audio/";
    @Autowired
    private SosAlertRepository sosRepository;
    @Autowired
    public SosServicce(SosAlertRepository repository) {
        this.repository = repository;
        new File(uploadDir).mkdirs(); // Ensure folder exists
    }

    public SosAlert saveAlert(double lat, double lng, MultipartFile audioFile) throws IOException {
        String filename = UUID.randomUUID() + "_" + audioFile.getOriginalFilename();
        String filePath = uploadDir + filename;
        audioFile.transferTo(new File(filePath));

        SosAlert alert = new SosAlert();
        alert.setLatitude(lat);
        alert.setLongitude(lng);
        alert.setAudioPath(filePath);

        return repository.save(alert);
    }
    public List<SosAlert> getAllAlerts() {
        return repository.findAll();
    }


    public boolean deleteAlertById(Long id) {
        if (sosRepository.existsById(id)) {
            sosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

