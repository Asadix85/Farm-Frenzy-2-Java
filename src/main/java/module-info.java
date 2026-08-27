module org.example.game_farmfrenzy2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;

    opens org.example.game_farmfrenzy2.view to javafx.fxml;
    exports org.example.game_farmfrenzy2;
    exports org.example.game_farmfrenzy2.model.entities;
    exports org.example.game_farmfrenzy2.model.game;
    exports org.example.game_farmfrenzy2.controller;
    exports org.example.game_farmfrenzy2.model.animal;
    exports org.example.game_farmfrenzy2.model.product;
    exports org.example.game_farmfrenzy2.model.machine;
    exports org.example.game_farmfrenzy2.model.structure;
}