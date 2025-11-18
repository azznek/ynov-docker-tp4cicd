package fr.digicrafters.simple.rest.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void classIsAnnotatedWithEntity() {
        assertTrue(Item.class.isAnnotationPresent(Entity.class),
            "La classe Item doit être annotée avec @Entity");
    }

    @Test
    void idFieldIsAnnotatedWithIdAndGeneratedValueIdentity() throws NoSuchFieldException {
        Field idField = Item.class.getDeclaredField("id");

        assertTrue(idField.isAnnotationPresent(Id.class),
            "Le champ id doit être annoté avec @Id");

        assertTrue(idField.isAnnotationPresent(GeneratedValue.class),
            "Le champ id doit être annoté avec @GeneratedValue");

        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.IDENTITY, generatedValue.strategy(),
            "La stratégie de génération doit être GenerationType.IDENTITY");

        assertEquals(Long.class, idField.getType(),
            "Le champ id doit être de type Long");
    }

    @Test
    void nameFieldIsAnnotatedWithNonNullColumn() throws NoSuchFieldException {
        Field nameField = Item.class.getDeclaredField("name");

        assertTrue(nameField.isAnnotationPresent(Column.class),
            "Le champ name doit être annoté avec @Column");

        Column column = nameField.getAnnotation(Column.class);
        assertFalse(column.nullable(), "Le champ name doit être non nullable");
        assertEquals(String.class, nameField.getType(),
            "Le champ name doit être de type String");
    }

    @Test
    void hasPublicNoArgsConstructor() throws NoSuchMethodException {
        Constructor<Item> ctor = Item.class.getDeclaredConstructor();
        assertTrue(Modifier.isPublic(ctor.getModifiers()),
            "Le constructeur sans argument doit être public");
    }

    @Test
    void hasPublicStringConstructor() throws NoSuchMethodException {
        Constructor<Item> ctor = Item.class.getDeclaredConstructor(String.class);
        assertTrue(Modifier.isPublic(ctor.getModifiers()),
            "Le constructeur avec String doit être public");
    }

    @Test
    void gettersAndSettersWork() {
        Item item = new Item();
        item.setName("TestName");

        assertNull(item.getId(), "L'id doit être null tant qu'il n'est pas persistant");
        assertEquals("TestName", item.getName(), "Le getter/setter de name doit fonctionner");
    }

    @Test
    void toStringContainsFields() {
        Item item = new Item("TestName");

        String toString = item.toString();

        assertNotNull(toString, "toString ne doit pas retourner null");
        assertTrue(toString.contains("Item"), "toString doit contenir le nom de la classe");
        assertTrue(toString.contains("name='TestName'"),
            "toString doit contenir la valeur du champ name");
    }
}
