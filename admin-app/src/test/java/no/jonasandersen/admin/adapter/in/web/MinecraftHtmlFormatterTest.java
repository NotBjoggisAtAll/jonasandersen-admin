package no.jonasandersen.admin.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import no.jonasandersen.admin.core.minecraft.domain.MinecraftInstance;
import org.junit.jupiter.api.Test;

class MinecraftHtmlFormatterTest {

  @Test
  void formattedHtmlContainsNameAndIp() {
    MinecraftHtmlFormatter formatter = new MinecraftHtmlFormatter();
    String formatted = formatter.format(new MinecraftInstance("Test", "127.0.0.1"));

    assertThat(formatted).isEqualTo(
        """
            <p> Name: Test </p>
            <p> IP: 127.0.0.1 </p>
                """
    );
  }
}