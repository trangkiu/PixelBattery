module org.example.pixelbattery {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires com.sun.jna;

    exports org.example.pixelbattery.Service;
    opens org.example.pixelbattery to javafx.fxml;
    exports org.example.pixelbattery;
}