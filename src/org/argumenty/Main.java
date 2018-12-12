package org.argumenty;

import java.lang.annotation.*;
import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        UseCollaborator useCollaborator = new UseCollaborator();
        System.out.println(useCollaborator);

        Kontener.wstrzyknij(useCollaborator,"Hello Hello siema siema ");
        System.out.println(useCollaborator);
    }
}

// ToDo 1 Własna Adnotacja

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Wstrzyknij {

}


// todo 2 klasa obiektu do wstrzykiwania

class Collaborator {
    private String name;

    public Collaborator(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Collaborator{" +
                "name='" + name + '\'' +
                '}';
    }
}
// todo 2 Klasa z polami typu Collaborator

class UseCollaborator {
    @Wstrzyknij
    private Collaborator collaboratorWithAnnptatiaon;

    private Collaborator collaborator;

    @Override
    public String toString() {
        return "UseCollaborator{" +
                "collaborator=" + collaborator +
                ", collaboratorWithAnnptatiaon=" + collaboratorWithAnnptatiaon +
                '}';
    }
}

class Kontener {
    static void wstrzyknij(Object target, String name) throws IllegalAccessException {
        // todo 4 pobierz pola obiektu i przeliteruj po nich

        Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            // todo 5 pobierz adnotacje pola i przeiteruj po nich
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                // todo utwórz instancję Collabolaor i przypisz do pola z adnotacją
                if ("Wstrzyknij".equals(annotation.annotationType().getSimpleName())) {
                    field.setAccessible(true);
                    field.set(target, new Collaborator(name));
                }
            }
        }
    }
}
