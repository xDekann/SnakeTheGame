module org.openjfx.snakefx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.snakefx to javafx.fxml;
    exports org.openjfx.snakefx;
    opens org.openjfx.snakefx.entities to javafx.fxml;
    exports org.openjfx.snakefx.entities;
    opens org.openjfx.snakefx.game to javafx.fxml;
    exports org.openjfx.snakefx.game;
    opens org.openjfx.snakefx.resources to javafx.fxml;
    exports org.openjfx.snakefx.resources;
}
