package fr.lofo.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SystemInstruction")
class SystemInstructionTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorCases {
        @Test
        @DisplayName("should_throw_when_content_is_null_or_blank")
        void should_throw_when_content_is_null_or_blank() {
            assertThrows(IllegalArgumentException.class, () -> new SystemInstruction(null));
            assertThrows(IllegalArgumentException.class, () -> new SystemInstruction("   "));
        }
    }

    @Nested
    @DisplayName("default")
    class DefaultCases {
        @Test
        @DisplayName("should_load_default_when_called")
        void should_load_default_when_called() {
            SystemInstruction si = SystemInstruction.createDefault();
            assertNotNull(si);
            assertNotNull(si.content());
            assertFalse(si.content().isBlank());
        }
    }
}
