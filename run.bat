set PATH = "C:/Users/konik/java/openjfx-22_windows-x64_bin-sdk/javafx-sdk-22/lib\"
javac --module-path %PATH% --add-modules javafx.controls,javafx.fxml %1.java
java --module-path %PATH% --add-modules javafx.controls,javafx.fxml %1
