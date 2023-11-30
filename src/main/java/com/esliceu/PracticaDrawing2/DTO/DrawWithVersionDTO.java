package com.esliceu.PracticaDrawing2.DTO;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Entities.Version;

public class DrawWithVersionDTO {
    private Draw draw;

    private Version version;

    public DrawWithVersionDTO() {
    }

    public Draw getDraw() {
        return draw;
    }

    public void setDraw(Draw draw) {
        this.draw = draw;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}
