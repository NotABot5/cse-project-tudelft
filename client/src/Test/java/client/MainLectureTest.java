package client;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MainLectureTest {

    private Main controller;

    @BeforeAll
    static void initJFX() {
        Platform.startup(()-> {});
            }

    @BeforeEach
    void setUp() {
        controller = new Main();

        controller.fieldName = new TextField();
        controller.fieldFat = new TextField();
        controller.fieldCarbs = new TextField();
        controller.fieldProtein = new TextField();
    }

    @Test
    void testSetEditable() {
        controller.setEditable(true);
        assertTrue(controller.fieldName.isEditable());
        assertTrue(controller.fieldFat.isEditable());
        assertTrue(controller.fieldCarbs.isEditable());
        assertTrue(controller.fieldProtein.isEditable());

        controller.setEditable(false);
        assertFalse(controller.fieldName.isEditable());
        assertFalse(controller.fieldFat.isEditable());
        assertFalse(controller.fieldCarbs.isEditable());
        assertFalse(controller.fieldProtein.isEditable());
    }

    @Test
    void testEditAndCancel() {
        // Set some values
        controller.fieldName.setText("Apple");
        controller.fieldFat.setText("0.2");
        controller.fieldCarbs.setText("14");
        controller.fieldProtein.setText("0.3");

        controller.onEdit();

        // Change values
        controller.fieldName.setText("Banana");
        controller.fieldFat.setText("0.1");
        controller.fieldCarbs.setText("23");
        controller.fieldProtein.setText("1.1");

        // Revert it
        controller.onCancel();

        assertEquals("Apple", controller.fieldName.getText());
        assertEquals("0.2", controller.fieldFat.getText());
        assertEquals("14", controller.fieldCarbs.getText());
        assertEquals("0.3", controller.fieldProtein.getText());
    }

    @Test
    void testClear() {
        controller.fieldName.setText("Orange");
        controller.fieldFat.setText("0.1");
        controller.fieldCarbs.setText("12");
        controller.fieldProtein.setText("0.5");

        controller.onClear();

        assertEquals("", controller.fieldName.getText());
        assertEquals("", controller.fieldFat.getText());
        assertEquals("", controller.fieldCarbs.getText());
        assertEquals("", controller.fieldProtein.getText());
    }
}
