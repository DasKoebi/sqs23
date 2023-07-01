package inf.iso_to_name;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class NameConventionTest {

    @Test
    void some_architecture_rule() {

        classes().that().resideInAPackage("..service..")
                        .should().haveSimpleNameStartingWith("Service");
    }

    @Test
    void controllers_should_not_have_Gui_in_name(){

      classes().that().resideInAPackage("..controller..").should().haveSimpleNameNotContaining("Gui");
    }

    @Test
    void controllers_should_be_suffixed(){

        classes().that().resideInAPackage("..controller..").should().haveSimpleNameEndingWith("Controller");

    }

    @Test
    void classes_named_controller_should_be_in_a_controller_package (){

        classes()
                .that().haveSimpleNameContaining("Controller")
                .should().resideInAPackage("..controller..");
    }




}
