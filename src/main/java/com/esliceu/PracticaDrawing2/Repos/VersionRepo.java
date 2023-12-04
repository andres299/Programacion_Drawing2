package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Version;

import java.util.List;

public interface VersionRepo {
    void saveVersion(Version version);

    Version getVersionById(int drawId);

    List<Version> getAllVersionById(int drawId);
}
