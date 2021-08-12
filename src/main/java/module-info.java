module ec.edu.espol.chanton_proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    //requires java.base;

    opens ec.edu.espol.chanton_proyecto to javafx.fxml;
    exports ec.edu.espol.chanton_proyecto;
}
