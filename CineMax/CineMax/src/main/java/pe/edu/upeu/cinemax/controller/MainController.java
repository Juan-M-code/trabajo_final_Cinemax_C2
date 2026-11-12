package pe.edu.upeu.cinemax.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pe.edu.upeu.cinemax.App;
import pe.edu.upeu.cinemax.components.Dialogos;
import pe.edu.upeu.cinemax.config.AppContext;
import pe.edu.upeu.cinemax.util.Sesion;

import java.util.Map;

public class MainController {

    @FXML private TabPane tabPane;

    private record Vista(String archivo, String titulo) {
    }

    private static final Map<String, Vista> VISTAS = Map.of(
            "peliculas", new Vista("peliculas.fxml", "Películas"),
            "salas", new Vista("salas.fxml", "Salas"),
            "funciones", new Vista("funciones.fxml", "Funciones"),
            "productos", new Vista("productos.fxml", "Productos"),
            "clientes", new Vista("clientes.fxml", "Clientes"),
            "ventas", new Vista("ventas.fxml", "Ventas"),
            "historial", new Vista("historial.fxml", "Historial")
    );

    @FXML
    public void initialize() {
        abrirTab("peliculas");
    }

    @FXML
    private void irPeliculas() {
        abrirTab("peliculas");
    }

    @FXML
    private void irSalas() {
        abrirTab("salas");
    }

    @FXML
    private void irFunciones() {
        abrirTab("funciones");
    }

    @FXML
    private void irProductos() {
        abrirTab("productos");
    }

    @FXML
    private void irClientes() {
        abrirTab("clientes");
    }

    @FXML
    private void irVentas() {
        abrirTab("ventas");
    }

    @FXML
    private void irHistorial() {
        abrirTab("historial");
    }

    private void abrirTab(String clave) {
        for (Tab tab : tabPane.getTabs()) {
            if (clave.equals(tab.getUserData())) {
                tabPane.getSelectionModel().select(tab);
                return;
            }
        }

        Vista vista = VISTAS.get(clave);
        if (vista == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + vista.archivo()));
            loader.setControllerFactory(AppContext.getInstance()::getBean);
            Parent root = loader.load();

            Tab tab = new Tab(vista.titulo(), root);
            tab.setUserData(clave);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (Exception e) {
            e.printStackTrace();
            Dialogos.informar("No se pudo abrir el módulo",
                    "Ocurrió un problema al cargar la pantalla: " + e.getMessage());
        }
    }

    @FXML
    private void onCerrarSesion() {
        boolean confirmado = Dialogos.confirmar("Cerrar sesión",
                "¿Deseas cerrar la sesión y volver al login?");
        if (!confirmado) return;
        Sesion.cerrar();
        App.mostrarLogin();
    }

    @FXML
    private void onSalir() {
        Platform.exit();
    }

    @FXML
    private void onAcercaDe() {
        Dialogos.informar("Acerca de CineMax",
                "Sistema de ventas de entradas y dulcería.\n"
                        + "Módulos: Películas, Salas, Funciones, Productos, Clientes, Ventas e Historial.\n"
                        + "POO 2026 · UPeU Juliaca");
    }
}
