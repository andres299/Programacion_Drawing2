package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Version;

public interface VersionRepo {
    void saveVersion(Version version);

    Version getVersionById(int drawId);
}
