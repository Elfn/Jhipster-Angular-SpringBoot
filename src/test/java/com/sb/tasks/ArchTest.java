package com.sb.tasks;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.sb.tasks");

        noClasses()
            .that()
                .resideInAnyPackage("com.sb.tasks.service..")
            .or()
                .resideInAnyPackage("com.sb.tasks.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.sb.tasks.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
