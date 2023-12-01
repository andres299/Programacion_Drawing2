package com.esliceu.PracticaDrawing2.Services;

import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Repos.DrawRepo;
import com.esliceu.PracticaDrawing2.Repos.VersionRepo;
import com.esliceu.PracticaDrawing2.utils.ObjectCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

    @Autowired
    VersionRepo versionRepo;
    @Autowired
    ObjectCounter objectCounter;

    //Metodo para guardar el dibujo
    public void saveVersion(int drawId, String figures, int owner_id) {
        Version version = new Version(drawId,figures,objectCounter.countFiguresInJson(figures),owner_id);
        versionRepo.saveVersion(version);
    }

    //Metodo para obtener las figuras de la version del dibujo
    public Version getVersionById(int drawId) {
        return versionRepo.getVersionById(drawId);
    }
}
