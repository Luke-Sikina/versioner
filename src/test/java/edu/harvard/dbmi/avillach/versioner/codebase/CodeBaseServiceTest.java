package edu.harvard.dbmi.avillach.versioner.codebase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CodeBaseServiceTest {

    @Mock
    CodeBaseRepository repository;

    @InjectMocks
    CodeBaseService subject;

    @Test
    void shouldCreateCodeBases() {
        List<CodeBase> before = List.of(new CodeBase(-1, "a", "a", "a"));
        List<CodeBase> after = List.of(new CodeBase(1, "a", "a", "a"));
        Mockito.when(repository.createCodeBases(before)).thenReturn(after);

        List<CodeBase> actual = subject.createCodeBases(before);

        Assertions.assertEquals(after, actual);
    }
}